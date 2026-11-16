package com.cloudchaptask.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;


@Getter
public class SaveMemberInfoRequest {

    // null, 빈 문자열, 공백만 입력하는 경우 검증 실패
    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 20, message = "이름은 20자 이하여야 합니다.")
    private String memberName;

    // null 또는 1 미만의 나이 입력 시 검증 실패
    @NotNull(message = "나이는 필수 입력값입니다.")
    @Min(value = 1, message = "나이는 1세 이상이어야 합니다.")
    private Integer memberAge;

    // 미입력 또는 올바르지 않는 MBTI 입력 시 검증 실패
    @NotBlank(message = "MBTI는 필수 입력값입니다.")
    @Pattern(regexp = "^[EI][SN][TF][JP]$", message = "올바른 MBTI를 입력해주세요 또한 MBTI는 대문자만 가능합니다.")
    private String memberMBTI;

}
