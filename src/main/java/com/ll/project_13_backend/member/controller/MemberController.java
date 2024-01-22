package com.ll.project_13_backend.member.controller;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.service.MemberService;
import io.jsonwebtoken.security.Jwks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
}