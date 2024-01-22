package com.ll.project_13_backend.member.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MemberService {

    // 회원가입
    void join(final String loginId, String password, String name, String email, String phone);

    // 입출금
    void addCash(Member member, int cash, String event);

    void findByLoginId(String loginId);
}