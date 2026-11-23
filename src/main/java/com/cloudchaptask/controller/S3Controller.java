package com.cloudchaptask.controller;

import com.cloudchaptask.dto.response.FileDownloadUrlResponse;
import com.cloudchaptask.dto.response.FileUploadResponse;
import com.cloudchaptask.service.S3Service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class S3Controller {

    // S3 기능을 담당하는 Service
    private final S3Service s3Service;


    /**
     * 1. 프로필 이미지 업로드
     */
    @PostMapping(
            value = "/{id}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<FileUploadResponse> uploadProfileImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file
    ) {

        // S3 업로드 및 DB 저장 요청
        String imageUrl = s3Service.uploadProfileImage(id, file);

        // 이미지 주소 응답
        return ResponseEntity.ok(
                new FileUploadResponse(imageUrl)
        );
    }


    /**
     * 2. 프로필 이미지 조회
     */
    @GetMapping("/{id}/profile-image")
    public ResponseEntity<FileDownloadUrlResponse> getProfileImage(
            @PathVariable("id") Long id
    ) {

        // Presigned URL 발급 요청
        String url = s3Service.getProfileImage(id);

        // 발급받은 URL 응답
        return ResponseEntity.ok(
                new FileDownloadUrlResponse(url)
        );
    }
}