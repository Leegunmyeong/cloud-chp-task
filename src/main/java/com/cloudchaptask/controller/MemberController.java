package com.cloudchaptask.controller;


import com.cloudchaptask.dto.request.SaveMemberInfoRequest;
import com.cloudchaptask.dto.response.GetMemberInfoResponse;
import com.cloudchaptask.dto.response.SaveMemberInfoResponse;
import com.cloudchaptask.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;


    // 팀원 정보 등록
    @PostMapping
    public ResponseEntity<SaveMemberInfoResponse> savedMemberInfo(@Valid @RequestBody SaveMemberInfoRequest request) {
        SaveMemberInfoResponse responseDto = memberService.savedMemberInfo(request);
        ResponseEntity<SaveMemberInfoResponse> response = new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        return response;
    }

    // 팀원 정보 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<GetMemberInfoResponse> getMemberInfo(@PathVariable Long memberId) {
        GetMemberInfoResponse responseDto = memberService.getMemberInfo(memberId);
        ResponseEntity<GetMemberInfoResponse> response = new ResponseEntity<>(responseDto, HttpStatus.OK);
        return response;
    }

}
