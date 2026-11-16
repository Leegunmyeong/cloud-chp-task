package com.cloudchaptask.dto.response;

import lombok.Getter;

@Getter
public class SaveMemberInfoResponse {

    private final Long memberId;
    private final String memberName;
    private final Integer memberAge;
    private final String memberMBTI;

    public SaveMemberInfoResponse(Long memberId, String memberName, Integer memberAge, String memberMBTI) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.memberMBTI = memberMBTI;
    }
}
