package com.mmall.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:
 * @author: Mirai.Yang
 * @create: 2019-02-15 11:02
 * <pre>
 *       ██████╗   █████╗  ██╗  ██╗ ██╗  ██╗ ██╗
 *      ██╔════╝  ██╔══██╗ ██║ ██╔╝ ██║ ██╔╝ ██║
 *      ██║  ███╗ ███████║ █████╔╝  █████╔╝  ██║
 *      ██║   ██║ ██╔══██║ ██╔═██╗  ██╔═██╗  ██║
 *      ╚██████╔╝ ██║  ██║ ██║  ██╗ ██║  ██╗ ██║
 *       ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝
 *  </pre>
 */
@Entity
@Table(name = "mmall_cart")
@NamedEntityGraph(name = "cart.all", attributeNodes = @NamedAttributeNode(value = "product", subgraph = "product.slim"),
        subgraphs = @NamedSubgraph(name = "product.slim", attributeNodes = {@NamedAttributeNode("productName"),
                @NamedAttributeNode("productSubtitle"), @NamedAttributeNode("productMainImage"),
                @NamedAttributeNode("productPrice"), @NamedAttributeNode("productStatus"),
                @NamedAttributeNode("productStock")}))
@Data
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "product_id", insertable = false, updatable = false)
    private Integer productId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "checked")
    private Integer checked;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
