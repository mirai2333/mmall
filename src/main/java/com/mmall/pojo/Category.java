package com.mmall.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "mmall_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer categoryID;
    @Column(name = "parent_id")
    private Integer parentID;
    @Column(name = "name")
    private String categoryName;
    @Column(name = "status")
    private Boolean categoryStatus;
    @Column(name = "sort_Order")
    private Integer sortOrder;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
