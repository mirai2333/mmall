package com.mmall.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductListVo {
    private Integer productID;
    private Integer categoryID;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
}
