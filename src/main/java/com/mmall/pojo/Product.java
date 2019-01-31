package com.mmall.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "mmall_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer productID;

    @Column(name = "category_id")
    @NotBlank
    private Integer categoryID;

    @Column(name = "name")
    @NotBlank
    private String productName;

    @Column(name = "subtitle")
    private String productSubtitle;
    @Column(name = "main_image")
    private String productMainImage;
    @Column(name = "sub_images")
    private String productSubImages;
    @Column(name = "detail")
    private String productDetail;

    @Column(name = "price")
    @NotNull
    private BigDecimal productPrice;

    @Column(name = "stock")
    @NotBlank
    private Integer productStock;

    @Column(name = "status")
    @Range(min = 1, max = 3, message = "产品状态值错误")
    private Integer productStatus;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
