package com.mmall.repository;

import com.mmall.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT createTime FROM Product WHERE productID = :id")
    Optional<Timestamp> findCreateTimeByProductID(@Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE Product SET productStatus = :status WHERE productID = :id")
    void updateProductStatus(@Param("status") Integer status, @Param("id") Integer id);

    @Query(value = "SELECT " +
            "new Product(productID,categoryID,productName,productSubtitle,productMainImage,productPrice,productStatus) " +
            "FROM Product")
    Page<Product> findProductList(Pageable pageable);

    @Query(value = "SELECT " +
            "new Product(p.productID,p.categoryID,p.productName,p.productSubtitle,p.productMainImage,p.productPrice,p.productStatus) " +
            "FROM Product p WHERE p.productName LIKE :name")
    Page<Product> searchProductList(@Param("name") String name, Pageable pageable);
}
