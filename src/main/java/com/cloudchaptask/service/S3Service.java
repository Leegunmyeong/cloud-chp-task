package com.cloudchaptask.service;

import com.cloudchaptask.entity.Member;
import com.cloudchaptask.repository.MemberRepository;
import com.cloudchaptask.exception.ServerException;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    // 1. 팀원 정보 조회 및 저장
    private final MemberRepository memberRepository;

    // 2. AWS S3 연동
    private final S3Template s3Template;

    // 3. YAML에서 S3 버킷 이름 가져오기
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    // 4. YAML에서 AWS 리전 가져오기
    @Value("${spring.cloud.aws.region.static}")
    private String region;

    // 5. Presigned URL 유효기간 (7일)
    private static final Duration EXPIRATION = Duration.ofDays(7);


    /**
     * 프로필 이미지 업로드
     */
    @Transactional
    public String uploadProfileImage(Long memberId, MultipartFile file) {

        // 1. 요청받은 팀원 ID로 팀원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ServerException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 팀원입니다."
                )
        );

        // 2. 업로드한 파일이 비어 있는지 검사
        if (file == null || file.isEmpty()) {
            throw new ServerException(
                    HttpStatus.BAD_REQUEST,
                    "이미지 파일이 없습니다."
            );
        }

        // 3. 이미지 형식 검사
        String contentType = file.getContentType();
        String extension;

        if ("image/jpeg".equals(contentType)) {
            extension = ".jpg";
        } else if ("image/png".equals(contentType)) {
            extension = ".png";
        } else if ("image/webp".equals(contentType)) {
            extension = ".webp";
        } else {
            throw new ServerException(
                    HttpStatus.BAD_REQUEST,
                    "지원하지 않는 이미지 형식입니다."
            );
        }

        // 4. 중복되지 않는 S3 객체 Key 생성
        String key = "uploads/" + UUID.randomUUID() + extension;

        // 5. S3에 이미지 업로드
        try (InputStream inputStream = file.getInputStream()) {

            s3Template.upload(
                    bucket,
                    key,
                    inputStream,
                    ObjectMetadata.builder()
                            .contentType(contentType)
                            .build()
            );

        } catch (IOException e) {
            throw new ServerException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "파일을 읽는 중 오류가 발생했습니다."
            );
        }

        // 6. S3 이미지 주소 생성
        String imageUrl = "https://" + bucket
                + ".s3." + region
                + ".amazonaws.com/" + key;

        // 7. Member Entity에 이미지 주소 저장
        member.updateProfileImage(imageUrl);

        // @Transactional로 JPA 변경 감지 적용
        // 트랜잭션 커밋 시 DB에 반영됨

        // 8. 업로드한 이미지 주소 반환
        return imageUrl;
    }


    /**
     * 프로필 이미지 Presigned URL 조회
     */
    @Transactional(readOnly = true)
    public String getProfileImage(Long memberId) {

        // 1. 팀원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ServerException(
                        HttpStatus.NOT_FOUND,
                        "존재하지 않는 팀원입니다."
                )
        );

        // 2. DB에 저장된 이미지 주소 가져오기
        String imageUrl = member.getProfileImageUrl();

        // 3. 이미지 등록 여부 확인
        if (imageUrl == null) {
            throw new ServerException(
                    HttpStatus.NOT_FOUND,
                    "등록된 프로필 이미지가 없습니다."
            );
        }

        // 4. 이미지 주소에서 S3 객체 Key 추출
        String prefix = "https://" + bucket
                + ".s3." + region
                + ".amazonaws.com/";

        if (!imageUrl.startsWith(prefix)) {
            throw new ServerException(
                    HttpStatus.BAD_REQUEST,
                    "잘못된 이미지 주소입니다."
            );
        }

        String key = imageUrl.substring(prefix.length());

        // 5. 유효기간 7일의 Presigned URL 발급
        return s3Template.createSignedGetURL(
                bucket,
                key,
                EXPIRATION
        ).toString();
    }
}