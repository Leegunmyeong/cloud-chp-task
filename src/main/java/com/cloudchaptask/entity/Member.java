package com.cloudchaptask.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    // 팀원 이름
    @Column(name = "member_name", nullable = false, length = 20)
    private String memberName;
    // 팀원 나이
    @Column(name = "member_age", nullable = false)
    private Integer memberAge;
    // 팀원 MBTI
    @Column(name = "member_mbti", nullable = false, length = 4)
    private String memberMBTI;
    // S3에 저장된 프로필 이미지의 주소
    @Column(name = "profile_image_url", length = 2048)
    private String profileImageUrl;



    public Member(String memberName, Integer memberAge, String memberMBTI) {
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.memberMBTI = memberMBTI;
    }

    // 프로필 이미지 업로드 성공 후 주소 변경
    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
