package com.ll.project_13_backend.member.repository;

import com.ll.project_13_backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
