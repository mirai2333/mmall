package com.mmall.repository;

import com.mmall.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT createTime FROM Product WHERE productID = :id")
    Optional<Timestamp> findCreateTimeByProductID(@Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE Product SET productStatus = :status WHERE productID = :id")
    void updateProductStatus(@Param("status") Integer status, @Param("id") Integer id);
}
