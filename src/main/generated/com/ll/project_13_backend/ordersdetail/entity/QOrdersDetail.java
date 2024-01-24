package com.ll.project_13_backend.ordersdetail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrdersDetail is a Querydsl query type for OrdersDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersDetail extends EntityPathBase<OrdersDetail> {

    private static final long serialVersionUID = -1358538631L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrdersDetail ordersDetail = new QOrdersDetail("ordersDetail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ll.project_13_backend.orders.entity.QOrders orders;

    public final com.ll.project_13_backend.post.entity.QPost post;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public QOrdersDetail(String variable) {
        this(OrdersDetail.class, forVariable(variable), INITS);
    }

    public QOrdersDetail(Path<? extends OrdersDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrdersDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrdersDetail(PathMetadata metadata, PathInits inits) {
        this(OrdersDetail.class, metadata, inits);
    }

    public QOrdersDetail(Class<? extends OrdersDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new com.ll.project_13_backend.orders.entity.QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.post = inits.isInitialized("post") ? new com.ll.project_13_backend.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

