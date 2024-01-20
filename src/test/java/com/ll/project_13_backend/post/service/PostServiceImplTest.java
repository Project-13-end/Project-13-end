package com.ll.project_13_backend.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ll.project_13_backend.global.exception.AuthException;
import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.dto.service.FindPostDto;
import com.ll.project_13_backend.post.dto.service.UpdatePostDto;
import com.ll.project_13_backend.post.entity.Category;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//todo 통합 테스트 시 id가 보장받지 못하는 상황을 어떻게 처리해야 하는가 생각해보자
@SpringBootTest
@Transactional
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("게시글을 만든다.")
    @Test
    public void createPostTest() {

        //given
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

        Member member1 = Member.builder()
                .loginId("member1")
                .build();
        Member member2 = Member.builder()
                .loginId("member2")
                .build();
        Member member3 = Member.builder()
                .loginId("member3")
                .build();

        memberRepository.saveAll(List.of(member1, member2, member3));

        //when
        postService.createPost(createPostDto1, member1);
        postService.createPost(createPostDto2, member2);
        postService.createPost(createPostDto3, member3);

        //then
        List<Post> post = postRepository.findAll();

        assertThat(post.size())
                .isEqualTo(3);
    }

    @DisplayName("로그인을 하지 않고 게시글을 작성하면 예외가 발생한다.")
    @Test
    public void createPostExceptionTest() {
        //given
        CreatePostDto createPostDto1 = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        Member member1 = Member.builder()
                .loginId("member1")
                .build();
        postService.createPost(createPostDto1, member1);
        //when & then
        assertThatThrownBy(() -> postService.createPost(createPostDto1, null))
                .isInstanceOf(AuthException.class)
                .hasMessage("로그인을 하지 않은 사용자입니다.");
    }

    @DisplayName("존재하지 않는 게시글을 조회하면 예외가 발생한다.")
    @Test
    public void findPostExceptionTest() {
        //given
        CreatePostDto createPostDto1 = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();

        Member member1 = Member.builder()
                .loginId("member1")
                .build();
        Long id = postService.createPost(createPostDto1, member1);
        //when & then

        assertThatThrownBy(() -> postService.findPost(999999999999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("지정한 Entity를 찾을 수 없습니다.");
    }

    @DisplayName("게시글을 조회합니다.")
    @Test
    public void findPostTest() {

        Member member1 = Member.builder()
                .loginId("member1")
                .name("testName1")
                .build();

        CreatePostDto createPostDto1 = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();
        Member member = memberRepository.save(member1);
        Long id = postService.createPost(createPostDto1, member);

        //when
        FindPostDto findPostDto = postService.findPost(id);

        //then
        assertAll(
                () -> assertThat(findPostDto.name()).isEqualTo("testName1"),

                () -> assertThat(findPostDto)
                        .extracting("title", "content", "category", "price")
                        .contains("testTitle1", "testContent1", Category.KOR, 10000L)//시간확인은 궂이 안해도?
        );
    }

    @DisplayName("권한이 없는 사용자가 게시글을 수정하려하면 예외가 발생한다.")
    @Test
    public void updatePostUnauthorizedUserExceptionTest() {
        //given
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();
        Member member = Member.builder().build();
        Long postId = postService.createPost(createPostDto, member);

        UpdatePostDto updatePostDto = UpdatePostDto.builder()
                .build();

        //when & then
        assertThatThrownBy(() -> postService.updatePost(postId, updatePostDto, null))
                .isInstanceOf(AuthException.class)
                .hasMessage("권한이 없는 사용자입니다.");
    }

    @DisplayName("존재하지 않는 게시글을 수정하려하면 예외가 발생한다.")
    @Test
    public void updatePostNotLoginException() {
        //given
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();
        Member member = Member.builder().build();
        Long postId = postService.createPost(createPostDto, member);

        UpdatePostDto updatePostDto = UpdatePostDto.builder()
                .build();

        //when & then
        assertThatThrownBy(() -> postService.updatePost(99999999L, updatePostDto, member))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("지정한 Entity를 찾을 수 없습니다.");
    }

    @DisplayName("게시글을 수정한다.")
    @Test
    public void updatePostTest() {
        //todo 테스트 코드를 작성 시 testTitle1 포스트 하나만 만들어서 테스르를 하는것인가 아니면 여러개를 만들어야 하는것인가 생각해보자
        //given
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();
        Member member = Member.builder().build();
        Long postId = postService.createPost(createPostDto, member);

        UpdatePostDto updatePostDto = UpdatePostDto.builder()
                .title("updateTitle1")
                .content("updateContent1")
                .category(Category.ENG)
                .price(20000L)
                .build();

        //when
        postService.updatePost(postId, updatePostDto, member);

        //then
        Post post = postRepository.findById(postId).get();
        assertThat(post)
                .extracting("title", "content", "category", "price")
                .contains("updateTitle1", "updateContent1", Category.ENG, 20000L);
    }

    @DisplayName("권한이 없는 사용자가 게시글을 삭제하려하면 예외가 발생한다.")
    @Test
    public void deletePostUnauthorizedUserExceptionTest() {
        //given
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("testTitle1")
                .content("testContent1")
                .category(Category.KOR)
                .price(10000L)
                .build();
        Member member = Member.builder().build();
        Long postId = postService.createPost(createPostDto, member);

        //when & then
        assertThatThrownBy(() -> postService.deletePost(postId, null))
                .isInstanceOf(AuthException.class)
                .hasMessage("권한이 없는 사용자입니다.");
    }
}