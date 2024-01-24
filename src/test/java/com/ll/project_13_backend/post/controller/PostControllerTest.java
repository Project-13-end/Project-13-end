package com.ll.project_13_backend.post.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.request.CreatePostRequest;
import com.ll.project_13_backend.post.dto.request.UpdatePostRequest;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.entity.Category;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import com.ll.project_13_backend.post.service.PostService;
import com.ll.project_13_backend.test_security.prinipal.MemberPrincipal;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        em.createNativeQuery("ALTER TABLE post AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE member AUTO_INCREMENT 1").executeUpdate();
    }

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
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("title"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("제목을 반드시 입력해주세요.")
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
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("content"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("내용을 반드시 입력해주세요.")
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
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("category"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("카테고리를 반드시 선택해주세요.")
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
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("price"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("가격을 반드시 입력해주세요.")
                );
    }

    @DisplayName("존재하지 않는 게시글을 조회하면 예외가 발생한다.")
    @Test
    public void findPostNotExist() throws Exception {
        mockMvc.perform(get("/post/{articleId}", 99999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.code").value("C_001"),
                        jsonPath("$.message").value("지정한 Entity를 찾을 수 없습니다.")
                );
    }

    @DisplayName("게시글을 조회한다.")
    @Test
    public void findPostTest() throws Exception {

        //given
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("titleTest1")
                .content("contentTest1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        Member member = Member.builder().name("testName").build();
        Long postId = postService.createPost(createPostDto, member);

        //when & then
        mockMvc.perform(get("/post/{articleId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.name").value("testName"),
                        jsonPath("$.title").value("titleTest1"),
                        jsonPath("$.content").value("contentTest1"),
                        jsonPath("$.category").value("kor"),
                        jsonPath("$.price").value(10000),
                        jsonPath("$.createdDate").isNotEmpty()
                );
    }

    //todo 메소드이름 다시 생각하기
    @DisplayName("권한이 없는 사용자는 게시글을 수정하지 못한다.")
    @Test
    public void updatePostUnauthorizedTest() throws Exception {
        //given
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("titleTest1")
                .content("contentTest1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        Member member = Member.builder().name("testName").build();
        Long postId = postService.createPost(createPostDto, member);

        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("updateTitle1")
                .content("updateContent1")
                .category("eng")
                .price(20000L).build();
        //when & then
        mockMvc.perform(put("/post/{postId}", 1L)
                .content(objectMapper.writeValueAsString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("AU_002"),
                        jsonPath("$.message").value("권한이 없는 사용자입니다.")
                );
    }

    @DisplayName("게시글 수정 시 제목을 반드시 입력해야한다.")
    @Test
    public void updatePostNotInputTitleTest() throws Exception {
        //given
        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .content("contentTest1")
                .category("kor")
                .price(10000L)
                .build();
        //when & then
        mockMvc.perform(put("/post/{postId}", 1L)
                .content(objectMapper.writeValueAsString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("title"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("제목을 반드시 입력해주세요.")
                );
    }

    @DisplayName("게시글 수정 시 내용을 반드시 입력해야한다.")
    @Test
    public void updatePostNotInputContentTest() throws Exception {
        //given
        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("titleTest1")
                .category("kor")
                .price(10000L)
                .build();

        //when & then
        mockMvc.perform(put("/post/{postId}", 1L)
                .content(objectMapper.writeValueAsString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("content"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("내용을 반드시 입력해주세요.")
                );
    }

    @DisplayName("게시글 수정 시 카테고리를 반드시 선택해야한다.")
    @Test
    public void updatePostNotSelectCategoryTest() throws Exception {
        //given
        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("titleTest1")
                .content("contentTest1")
                .price(10000L)
                .build();

        //when & then
        mockMvc.perform(put("/post/{postId}", 1L)
                        .content(objectMapper.writeValueAsString(updatePostRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("category"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("카테고리를 반드시 선택해주세요.")
                );
    }

    @DisplayName("게시글 수정 시 가격을 반드시 입력해야한다.")
    @Test
    public void updatePostNotInputPriceTest() throws Exception {
        //given
        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("titleTest1")
                .content("contentTest1")
                .category("kor")
                .build();

        //when & then
        mockMvc.perform(put("/post/{postId}", 1L)
                        .content(objectMapper.writeValueAsString(updatePostRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("price"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("가격을 반드시 입력해주세요.")
                );
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    public void updatePostTest() throws Exception {//todo 허..... 리팩토링 필요해 보임
        //given
        Member member = Member.builder().id(1L).loginId("user").password("password").build();
        memberRepository.save(member);

        Post post = Post.builder()
                .member(member)
                .title("titleTest1")
                .content("contentTest1")
                .category(Category.KOR)
                .price(20000L)
                .build();

        postRepository.save(post);

        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("updateTitle1")
                .content("updateContent1")
                .category("eng")
                .price(20000L)
                .build();

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when
        mockMvc.perform(put("/post/{postId}", 1L)
                .content(objectMapper.writeValueAsString(updatePostRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isOk());

        //then
        Post resultPost = postRepository.findById(1L).get();
        Assertions.assertThat(resultPost)
                .extracting("title", "content", "category", "price")
                .contains("updateTitle1", "updateContent1", Category.ENG, 20000L);
    }

    @DisplayName("권한이 없는 회원은 게시글을 수정하지 못한다.")
    @Test
    public void updatePostUnauthorizedTest2() throws Exception {//todo 허..... 리팩토링 필요해 보임
        //given
        Member member = Member.builder().id(1L).loginId("user").password("password").build();
        Member member2 = Member.builder().id(2L).loginId("user2").password("password").build();
        memberRepository.saveAll(List.of(member, member2));



        Post post = Post.builder()
                .member(memberRepository.findById(2L).get())
                .title("titleTest1")
                .content("contentTest1")
                .category(Category.KOR)
                .price(20000L)
                .build();

        postRepository.save(post);

        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("updateTitle1")
                .content("updateContent1")
                .category("eng")
                .price(20000L)
                .build();

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when
        mockMvc.perform(put("/post/{postId}", 1L)
                        .content(objectMapper.writeValueAsString(updatePostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("AU_002"),
                        jsonPath("$.message").value("권한이 없는 사용자입니다.")
                );
    }

    @DisplayName("비회원은 게시글을 삭제하지 못한다.")
    @Test
    public void deletePostAnonymousUnauthorizedTest() throws Exception {
        mockMvc.perform(delete("/post/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("AU_002"),
                        jsonPath("$.message").value("권한이 없는 사용자입니다.")
                );
    }

    @DisplayName("권한이 없는 회원은 게시글을 삭제하지 못한다.")
    @Test
    public void deletePostMemberUnauthorizedTest() throws Exception {
        //given
        Member member = Member.builder().id(1L).loginId("user").password("password").build();
        Member member2 = Member.builder().id(2L).loginId("user2").password("password").build();
        memberRepository.saveAll(List.of(member, member2));

        Post post = Post.builder()
                .member(memberRepository.findById(2L).get())
                .title("titleTest1")
                .content("contentTest1")
                .category(Category.KOR)
                .price(20000L)
                .build();

        postRepository.save(post);
        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(delete("/post/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("AU_002"),
                        jsonPath("$.message").value("권한이 없는 사용자입니다.")
                );
    }

    @DisplayName("존재하지 않는 게시글은 삭제하지 못한다.")
    @Test
    public void deletePostNotExistTest() throws Exception {
        //given
        Member member = Member.builder().id(1L).loginId("user").password("password").build();
        memberRepository.save(member);

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(delete("/post/{postId}", 999999999L)
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("C_001"),
                        jsonPath("$.message").value("지정한 Entity를 찾을 수 없습니다.")
                );
    }

    @DisplayName("게시글을 삭제한다.")
    @Test
    public void deletePostTest() throws Exception {
        //given
        Member member = Member.builder().id(1L).loginId("user").password("password").build();
        memberRepository.save(member);

        Post post = Post.builder()
                .member(memberRepository.findById(1L).get())
                .title("titleTest1")
                .content("contentTest1")
                .category(Category.KOR)
                .price(20000L)
                .build();

        postRepository.save(post);

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when
        mockMvc.perform(delete("/post/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/post/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.code").value("C_001"),
                        jsonPath("$.message").value("지정한 Entity를 찾을 수 없습니다.")
                );
    }

    @DisplayName("모든 게시글을 출력한다.")
    @Test
    public void findAllTest() throws Exception {
        CreatePostDto createPostDto1 = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        CreatePostDto createPostDto2 = CreatePostDto.builder()
                .title("testTitle2")
                .content("testContent2")
                .category(Category.ENG)
                .price(20000L)
                .build();

        CreatePostDto createPostDto3 = CreatePostDto.builder()
                .title("testTitle3")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        CreatePostDto createPostDto4 = CreatePostDto.builder()
                .title("3321")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        Member member1 = Member.builder()
                .name("name1")
                .build();
        Member member2 = Member.builder()
                .name("name2")
                .build();
        Member member3 = Member.builder()
                .name("name3")
                .build();

        memberRepository.saveAll(List.of(member1, member2, member3));

        for (int i = 0; i < 21; i++) {
            postService.createPost(createPostDto1, member1);
            postService.createPost(createPostDto2, member2);
            postService.createPost(createPostDto3, member3);
            postService.createPost(createPostDto4, member3);
        }
//todo hasNext가 검증이 안된다. 이유를 물어보자
        mockMvc.perform(get("/post")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.content[*].title", everyItem(matchesPattern(".*Title.*|.*3321.*"))),
                        jsonPath("$.content.size()").value(20),
                        jsonPath("$.last").value(false)
                );
    }


    @DisplayName("마지막 게시글을 출력하면 last는 true가 된다.")
    @Test
    public void findAllHasNextTest() throws Exception {
        CreatePostDto createPostDto1 = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        CreatePostDto createPostDto2 = CreatePostDto.builder()
                .title("testTitle2")
                .content("testContent2")
                .category(Category.ENG)
                .price(20000L)
                .build();

        CreatePostDto createPostDto3 = CreatePostDto.builder()
                .title("testTitle3")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        CreatePostDto createPostDto4 = CreatePostDto.builder()
                .title("3321")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        Member member1 = Member.builder()
                .name("name1")
                .build();
        Member member2 = Member.builder()
                .name("name2")
                .build();
        Member member3 = Member.builder()
                .name("name3")
                .build();

        memberRepository.saveAll(List.of(member1, member2, member3));

        for (int i = 0; i < 21; i++) {
            postService.createPost(createPostDto1, member1);
            postService.createPost(createPostDto2, member2);
            postService.createPost(createPostDto3, member3);
            postService.createPost(createPostDto4, member3);
        }
//todo hasNext가 검증이 안된다. 이유를 물어보자
        mockMvc.perform(get("/post?page=4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.content[*].title",containsInAnyOrder("testTitle1", "testTitle2", "testTitle3","3321")),
                        jsonPath("$.content.size()").value(4),
                        jsonPath("$.last").value(true)
                );
    }

    @DisplayName("키워드 기준으로 검색하여 출력하고 마지막까지 출력이 끝나면 last는 true가 된다.")
    @Test
    public void findSearchAllHasNextTest() throws Exception {
        CreatePostDto createPostDto1 = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        CreatePostDto createPostDto2 = CreatePostDto.builder()
                .title("testTitle2")
                .content("testContent2")
                .category(Category.ENG)
                .price(20000L)
                .build();

        CreatePostDto createPostDto3 = CreatePostDto.builder()
                .title("testTitle3")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        CreatePostDto createPostDto4 = CreatePostDto.builder()
                .title("123")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        Member member1 = Member.builder()
                .name("name1")
                .build();
        Member member2 = Member.builder()
                .name("name2")
                .build();
        Member member3 = Member.builder()
                .name("name3")
                .build();

        memberRepository.saveAll(List.of(member1, member2, member3));

        for (int i = 0; i < 21; i++) {
            postService.createPost(createPostDto1, member1);
            postService.createPost(createPostDto2, member2);
            postService.createPost(createPostDto3, member3);
            postService.createPost(createPostDto4, member3);
        }
//todo hasNext필드가 있는데 없다고 나온다 이유를 모르겠다.
        mockMvc.perform(get("/post/search?page=3&keyword=Title")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.content[*].title", containsInAnyOrder("testTitle1", "testTitle2", "testTitle3")),
                        jsonPath("$.content.size()").value(3),
                        jsonPath("$.last").value(true)
                );
    }

    @DisplayName("키워드를 기준으로 OR검색을 하여 모든 게시글을 출력한다.")
    @Test
    public void findSearchAllTest() throws Exception {
        CreatePostDto createPostDto1 = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        CreatePostDto createPostDto2 = CreatePostDto.builder()
                .title("testTitle2")
                .content("testContent2")
                .category(Category.ENG)
                .price(20000L)
                .build();

        CreatePostDto createPostDto3 = CreatePostDto.builder()
                .title("testTitle3")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        CreatePostDto createPostDto4 = CreatePostDto.builder()
                .title("123")
                .content("testContent3")
                .category(Category.MATH)
                .price(30000L)
                .build();

        Member member1 = Member.builder()
                .name("name1")
                .build();
        Member member2 = Member.builder()
                .name("name2")
                .build();
        Member member3 = Member.builder()
                .name("name3")
                .build();

        memberRepository.saveAll(List.of(member1, member2, member3));

        for (int i = 0; i < 21; i++) {
            postService.createPost(createPostDto1, member1);
            postService.createPost(createPostDto2, member2);
            postService.createPost(createPostDto3, member3);
            postService.createPost(createPostDto4, member3);
        }
//todo hasNext가 검증이 안된다. 이유를 물어보자
        mockMvc.perform(get("/post/search?keyword=Title")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.content[*].title", everyItem(matchesPattern(".*Title.*"))),
                        jsonPath("$.content.size()").value(20),
                        jsonPath("$.last").value(false)
                );
    }
}