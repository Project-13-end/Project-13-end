package com.ll.project_13_backend.post.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.request.CreatePostRequest;
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


}

