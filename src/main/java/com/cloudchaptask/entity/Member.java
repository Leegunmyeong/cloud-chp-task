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


    public Member(String memberName, Integer memberAge, String memberMBTI) {
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.memberMBTI = memberMBTI;
    }
}
