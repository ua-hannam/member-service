package com.uahannam.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends SysTimeCols {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "item_id", nullable = false)
    private Integer itemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}

