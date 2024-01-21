package com.ll.project_13_backend.post.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.request.CreatePostRequest;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.test_security.prinipal.MemberPrincipal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberPrincipal memberPrincipal;

    @DisplayName("게시글을 등록한다.")
    @Test
    void createPostTest() throws Exception {
        //given
        CreatePostRequest createPostRequest = CreatePostRequest.builder()
                .title("testTitle1")
                .content("testContent1")
                .category("kor")
                .price(10000L)
                .build();

        memberRepository.save(Member.builder().loginId("user").password("pass").build());
        UserDetails user = memberPrincipal.loadUserByUsername("user");

//when & then
        mockMvc.perform(post("/post/create")
                        .content(objectMapper.writeValueAsString(createPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/post/1"));

    }

    @DisplayName("게시글 제목은 반드시 입력해야 한다.")
    @Test
    void createPostNotInputTitle() throws Exception {
        CreatePostRequest createPostRequest = CreatePostRequest.builder()
                .content("contentTest1")
                .category("kor")
                .price(10000L)
                .build();

        memberRepository.save(Member.builder().loginId("user").password("password").build());
        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(post("/post/create")
                        .content(objectMapper.writeValueAsString(createPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다.")
                );
    }

    @DisplayName("게시글 내용은 반드시 입력해야 한다.")
    @Test
    void createPostNotInputContent() throws Exception {
        CreatePostRequest createPostRequest = CreatePostRequest.builder()
                .title("titleTest1")
                .category("kor")
                .price(10000L)
                .build();

        memberRepository.save(Member.builder().loginId("user").password("password").build());
        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(post("/post/create")
                        .content(objectMapper.writeValueAsString(createPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다.")
                );
    }

    @DisplayName("게시글의 카테고리는 반드시 정해야합니다.")
    @Test
    void createPostNotSelectCategory() throws Exception {
        CreatePostRequest createPostRequest = CreatePostRequest.builder()
                .title("titleTest1")
                .content("contentTest1")
                .price(10000L)
                .build();

        memberRepository.save(Member.builder().loginId("user").password("password").build());
        UserDetails user = memberPrincipal.loadUserByUsername("user");

        mockMvc.perform(post("/post/create")
                .content(objectMapper.writeValueAsString(createPostRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다.")
                );
    }

    @DisplayName("게시글의 가격은 반드시 입력해야합니다.")
    @Test
    public void createPostNotInputPrice() throws Exception {
        CreatePostRequest createPostRequest = CreatePostRequest.builder()
                .title("titleTest1")
                .content("contentTest1")
                .category("categoryTest1")
                .build();

        memberRepository.save(Member.builder().loginId("user").password("password").build());
        UserDetails user = memberPrincipal.loadUserByUsername("user");

        mockMvc.perform(post("/post/create")
                .content(objectMapper.writeValueAsString(createPostRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다.")
                );
    }
}

