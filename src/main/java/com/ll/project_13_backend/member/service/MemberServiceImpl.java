package com.ll.project_13_backend.member.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    @Override
    public void join(String loginId, String password, String name, String email, String phone) {

        Member member = Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .build();
    }

    @Override
    public void login(String loginId, String password) {
        return;
    }

    @Override
    public void addCash(Member member, int cash, String event) {
        return;
    }

    @Override
    public void findByLoginId(String loginId) {
        return;
    }
}
