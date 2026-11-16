package com.cloudchaptask.service;

import com.cloudchaptask.dto.request.SaveMemberInfoRequest;
import com.cloudchaptask.dto.response.GetMemberInfoResponse;
import com.cloudchaptask.dto.response.SaveMemberInfoResponse;
import com.cloudchaptask.entity.Member;
import com.cloudchaptask.exception.ServerException;
import com.cloudchaptask.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 팀원 정보 저장
    @Transactional
    public SaveMemberInfoResponse savedMemberInfo(SaveMemberInfoRequest request) {

        // 1. Controller에서 전달받은 Request에서 저장할 정보 꺼내기
        String memberName = request.getMemberName();
        Integer memberAge = request.getMemberAge();
        String memberMBTI = request.getMemberMBTI();

        // 2. 가져온 정보를 이용하여 Member 엔티티 객체 생성
        // 아직 DB에 저장되지 않았기 때문에 memberId는 null인 상태
        Member member = new Member(memberName, memberAge, memberMBTI);

        // 3. 생성한 Member 객체 DB에 저장
        Member savedMember = memberRepository.save(member);

        // 4. 저장된 Member 객체에서 클라이언트에게 응답할 정보 꺼낸 후
        // ResponseDTO 객체 생성
        SaveMemberInfoResponse response = new SaveMemberInfoResponse(
                savedMember.getMemberId(),
                savedMember.getMemberName(),
                savedMember.getMemberAge(),
                savedMember.getMemberMBTI()
        );

        // Controller에게 ResponseDTO 반환
        return response;
    }

    @Transactional(readOnly = true)
    public GetMemberInfoResponse getMemberInfo(Long memberId) {
        // 1. 전달받은 memberId로 DB에서 팀원 조회
        // 예외: 존재하지 않는 팀원일 시 NOT_FOUND 예외 발생
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new ServerException(HttpStatus.NOT_FOUND, "존재하지 않는 팀원입니다.")
        );

        // 2. 조회한 팀원 정보를 ResponseDTO로 변환
        GetMemberInfoResponse response = new GetMemberInfoResponse(
                member.getMemberId(),
                member.getMemberName(),
                member.getMemberAge(),
                member.getMemberMBTI()
        );

        // 3. Controller에게 응답 데이터 반환
        return response;

    }



}
