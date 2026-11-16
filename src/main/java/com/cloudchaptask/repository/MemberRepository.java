package com.cloudchaptask.repository;

import com.cloudchaptask.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
