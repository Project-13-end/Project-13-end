package com.ll.project_13_backend.global.initData;


import com.ll.project_13_backend.cart.service.CartService;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.member.service.MemberService;
import com.ll.project_13_backend.orders.entity.Orders;
import com.ll.project_13_backend.orders.service.OrdersService;
import com.ll.project_13_backend.post.service.PostService;
import com.ll.project_13_backend.refund.entity.Refund;
import com.ll.project_13_backend.refund.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;

    private final MemberService memberService;
    private final CartService cartService;
    private final PostService postService;
    private final OrdersService ordersService;
    private final RefundService refundService;
    private final MemberRepository memberRepository;

    @Bean
    @org.springframework.core.annotation.Order(3)
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.findByLoginId("admin").isPresent()) return;

//        //예시
//        Member member1 = Member.builder().id(1L).loginId("user1").password("1234").name("유저1").email("123@123").phone("010-0000-0000").build();
//        Member member2 = Member.builder().id(2L).loginId("user2").password("1234").name("유저2").email("124@124").phone("010-1111-1111").build();
//        Member member3 = Member.builder().id(3L).loginId("user3").password("1234").name("유저3").email("125@125").phone("010-2222-2222").build();
//        memberRepository.saveAll(List.of(member1, member2, member3));
        //member




        //post



        //comment
        //cart??
        //order
        //ordersDetail
        //Payment
        //Refund

        // 예시 유저 생성
        Member memberAdmin = memberService.join("admin", "1234", "관리자", "a@naver.com", "010-0001-0000").getData();
        Member memberUser1 = memberService.join("user1", "1234", "유저1", "u1@naver.com", "010-0011-0000").getData();
        Member memberUser2 = memberService.join("user2", "1234", "유저2", "u2@naver.com", "010-0012-0000").getData();
        Member memberUser3 = memberService.join("user3", "1234", "유저3", "u3@naver.com", "010-0013-0000").getData();
        Member memberUser4 = memberService.join("user4", "1234", "유저4", "u4@naver.com", "010-0014-0000").getData();
        Member memberUser5 = memberService.join("user5", "1234", "유저5", "u5@naver.com", "010-0015-0000").getData();

        //예시 수업 생성
        Class class1 = postService.createPost(memberUser1, "게시글 제목 1", "게시글 내용 1", 10_000, true);
        Class class2 = postService.createPost(memberUser2, "게시글 제목 2", "게시글 내용 2", 20_000, true);
        Class class3 = postService.createPost(memberUser2, "게시글 제목 3", "게시글 내용 3", 30_000, true);
        Class class4 = postService.createPost(memberUser3, "게시글 제목 4", "게시글 내용 4", 40_000, true);
        Class class5 = postService.createPost(memberUser3, "게시글 제목 5", "게시글 내용 5", 15_000, true);
        Class class6 = postService.createPost(memberUser3, "게시글 제목 6", "게시글 내용 6", 20_000, true);


        // Payment Testing
        // 결제 상품 생성
        Product product1 = postService.createProduct(class3, true);
        Product product2 = postService.createProduct(class4, true);
        Product product3 = postService.createProduct(class5, true);
        Product product4 = postService.createProduct(class6, true);

        // member 상품 중복 결제
        postService.addItem(memberUser1, product1);
        postService.addItem(memberUser1, product2);
        postService.addItem(memberUser1, product3);

        postService.addItem(memberUser2, product1);
        postService.addItem(memberUser2, product2);
        postService.addItem(memberUser2, product3);

        postService.addItem(memberUser3, product1);
        postService.addItem(memberUser3, product2);
        postService.addItem(memberUser3, product3);

        //member 입출금 테스트
        memberService.addCash(memberUser1, 150_000, CashLog.EvenType.충전__무통장입금, memberUser1);
        memberService.addCash(memberUser1, -20_000, CashLog.EvenType.출금__통장입금, memberUser1);

        Orders orders1 = ordersService.createFromCart(memberUser1);

        long order1PayPrice = orders1.calcPayPrice();

        ordersService.payByCashOnly(orders1);

        memberService.addCash(memberUser3, 150_000, CashLog.EvenType.충전__무통장입금, memberUser3);

        Orders order2 = ordersService.createFromCart(memberUser3);
        ordersService.payByCashOnly(order2);
        ordersService.cancel(order2);

        memberService.addCash(memberUser2, 150_000, CashLog.EvenType.충전__무통장입금, memberUser2);

        Orders order3 = orderService.createFromCart(memberUser2);
        ordersService.checkCanPay(order3, 55_000);
        ordersService.payByTossPayments(order3, 55_000);

        memberService.addCash(memberUser4, 150_000, CashLog.EvenType.충전__무통장입금, memberUser4);

        postService.addItem(memberUser4, product1);
        postService.addItem(memberUser4, product2);
        postService.addItem(memberUser4, product3);

        Orders order4 = ordersService.createFromCart(memberUser4);

        memberService.addCash(memberUser5, 150_000, CashLog.EvenType.충전__무통장입금, memberUser5);

        refundService.apply(memberUser5, 150_000, "국민은행", "1234");

        cartService.addItem(memberUser5, product1);

        Orders orders5 = ordersService.createFromCart(memberUser5);

        ordersService.payByCashOnly(orders5);

        cartService.addItem(memberUser5, product2);

        Orders orders6 = ordersService.createFromCart(memberUser5);

        ordersService.cancel(orders6);

        cartService.addItem(memberUser5, product3);

        Orders order7 = ordersService.createFromCart(memberUser5);
        ordersService.payByCashOnly(order7);
        ordersService.cancel(order7);

        cartService.addItem(memberUser5, product4);
        Orders orders8 = ordersService.createFromCart(memberUser5);
    }


    //MemberService testing
    @Transactional
    public void work2() {
//        Member memberUser1 = memberService.findByUsername("user1").get();
//        Product product1 = productService.findById(1L).get();
//
//        cartService.addItem(memberUser1, product1);
        @Test
        public void testJoinAndLogin() {
            // 회원 가입 테스트
            RsData<Member> joinResult = memberService.join("testUser", "password123", "Test User", "test@example.com", "010-1234-5678");
            assertTrue(joinResult.isSuccess());

            // 로그인 테스트
            RsData<Member> loginResult = memberService.login("testUser", "password123");
            assertTrue(loginResult.isSuccess());
            assertNotNull(loginResult.getData());
        }


    }
}


