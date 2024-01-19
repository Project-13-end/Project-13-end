package com.ll.project_13_backend.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ll.project_13_backend.global.exception.AuthException;
import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.service.CreatePostDto;
import com.ll.project_13_backend.post.entity.Category;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


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
        postService.createPost(createPostDto1, member1);
        //when & then

        assertThatThrownBy(() -> postService.findPost(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("지정한 Entity를 찾을 수 없습니다.");
    }
}