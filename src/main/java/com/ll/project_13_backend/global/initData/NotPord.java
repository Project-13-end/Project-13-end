package com.ll.project_13_backend.global.initData;


import com.ll.project_13_backend.comment.entity.Comment;
import com.ll.project_13_backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;

    private final MemberService memberService;
//    private final CartService cartService;
//    private final ProductService productService;
//    private final CartService cartService;
//    private final OrderService orderService;
//    private final WithdrawService withdrawService;

    @Bean
    @org.springframework.core.annotation.Order(3)
    ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.findByUsername("admin").isPresent()) return;

        Member memberAdmin = memberService.join("admin", "1234", "관리자").getData();
        Member memberUser1 = memberService.join("user1", "1234", "유저1").getData();
        Member memberUser2 = memberService.join("user2", "1234", "유저2").getData();
        Member memberUser3 = memberService.join("user3", "1234", "유저3").getData();
        Member memberUser4 = memberService.join("user4", "1234", "유저4").getData();
        Member memberUser5 = memberService.join("user5", "1234", "유저5").getData();

        Comment comment1 = postService.createPost(memberUser1, "게시글 제목 1", "게시글 내용 1", 10_000, true);
        Comment comment2 = postService.createPost(memberUser2, "게시글 제목 2", "게시글 내용 2", 20_000, true);
        Comment comment3 = postService.createPost(memberUser2, "게시글 제목 3", "게시글 내용 3", 30_000, true);
        Comment comment4 = postService.createPost(memberUser3, "게시글 제목 4", "게시글 내용 4", 40_000, true);
        Comment comment5 = postService.createPost(memberUser3, "게시글 제목 5", "게시글 내용 5", 15_000, true);
        Comment comment6 = postService.createPost(memberUser3, "게시글 제목 6", "게시글 내용 6", 20_000, true);

        Product product1 = postService.createProduct(comment3, true);
        Product product2 = postService.createProduct(comment4, true);
        Product product3 = postService.createProduct(comment5, true);
        Product product4 = postService.createProduct(comment6, true);

        postService.addItem(memberUser1, product1);
        postService.addItem(memberUser1, product2);
        postService.addItem(memberUser1, product3);

        postService.addItem(memberUser2, product1);
        postService.addItem(memberUser2, product2);
        postService.addItem(memberUser2, product3);

        postService.addItem(memberUser3, product1);
        postService.addItem(memberUser3, product2);
        postService.addItem(memberUser3, product3);

        memberService.addCash(memberUser1, 150_000, CashLog.EvenType.충전__무통장입금, memberUser1);
        memberService.addCash(memberUser1, -20_000, CashLog.EvenType.출금__통장입금, memberUser1);

        Order order1 = orderService.createFromCart(memberUser1);

        long order1PayPrice = order1.calcPayPrice();

        orderService.payByCashOnly(order1);

        memberService.addCash(memberUser3, 150_000, CashLog.EvenType.충전__무통장입금, memberUser3);

        Order order2 = orderService.createFromCart(memberUser3);
        orderService.payByCashOnly(order2);
        orderService.cancel(order2);

        memberService.addCash(memberUser2, 150_000, CashLog.EvenType.충전__무통장입금, memberUser2);

        Order order3 = orderService.createFromCart(memberUser2);
        orderService.checkCanPay(order3, 55_000);
        orderService.payByTossPayments(order3, 55_000);

        memberService.addCash(memberUser4, 150_000, CashLog.EvenType.충전__무통장입금, memberUser4);

        postService.addItem(memberUser4, product1);
        postService.addItem(memberUser4, product2);
        postService.addItem(memberUser4, product3);

        Order order4 = orderService.createFromCart(memberUser4);

        memberService.addCash(memberUser5, 150_000, CashLog.EvenType.충전__무통장입금, memberUser5);

        withdrawService.apply(memberUser5, 150_000, "국민은행", "1234");

        cartService.addItem(memberUser5, product1);

        Order order5 = orderService.createFromCart(memberUser5);

        orderService.payByCashOnly(order5);

        cartService.addItem(memberUser5, product2);

        Order order6 = orderService.createFromCart(memberUser5);

        orderService.cancel(order6);

        cartService.addItem(memberUser5, product3);

        Order order7 = orderService.createFromCart(memberUser5);
        orderService.payByCashOnly(order7);
        orderService.cancel(order7);

        cartService.addItem(memberUser5, product4);
        Order order8 = orderService.createFromCart(memberUser5);
    }

    @Transactional
    public void work2() {
//        Member memberUser1 = memberService.findByUsername("user1").get();
//        Product product1 = productService.findById(1L).get();
//
//        cartService.addItem(memberUser1, product1);
    }
}