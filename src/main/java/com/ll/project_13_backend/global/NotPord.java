package com.ll.project_13_backend.global;


import com.ll.project_13_backend.cart.repository.CartRepository;
import com.ll.project_13_backend.comment.repository.CommentRepository;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.orders.repository.OrdersRepository;
import com.ll.project_13_backend.ordersdetail.repository.OrdersDetailRepository;
import com.ll.project_13_backend.payment.repository.PaymentRepository;
import com.ll.project_13_backend.post.repository.PostRepository;
import com.ll.project_13_backend.refund.repository.RefundRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotPord {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CartRepository cartRepository;
    private final OrdersRepository ordersRepository;
    private final OrdersDetailRepository ordersDetailRepository;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    public void init() {

    }

}
