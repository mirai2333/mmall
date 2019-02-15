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
@Data
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "checked")
    private Integer checked;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
