package com.mmall.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 购物车产品视图类
 * @author: Mirai.Yang
 * @create: 2019-02-15 10:40
 * <pre>
 *       ██████╗   █████╗  ██╗  ██╗ ██╗  ██╗ ██╗
 *      ██╔════╝  ██╔══██╗ ██║ ██╔╝ ██║ ██╔╝ ██║
 *      ██║  ███╗ ███████║ █████╔╝  █████╔╝  ██║
 *      ██║   ██║ ██╔══██║ ██╔═██╗  ██╔═██╗  ██║
 *      ╚██████╔╝ ██║  ██║ ██║  ██╗ ██║  ██╗ ██║
 *       ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝
 *  </pre>
 */
@Data
public class CartProductVo {
    //购物车单条记录的字段
    private Integer cartId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer checked;
    //产品字段
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private Integer productStock;
    //购物车字段
    private BigDecimal productTotalPrice;
    private String limitQuantity;

}
