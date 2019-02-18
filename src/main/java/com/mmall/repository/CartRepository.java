package com.mmall.repository;

import com.mmall.pojo.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: Mirai.Yang
 * @create: 2019-02-15 11:07
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {
    /**
     * 查找某个用户的购物车中的所有产品
     *
     * @param userId 用户Id
     * @return 产品列表
     */
    @EntityGraph(value = "cart.all",type = EntityGraph.EntityGraphType.FETCH)
    List<Cart> findAllByUserId(Integer userId);

    Optional<Cart> findByUserIdAndProductId(Integer userId, Integer productId);

    void deleteAllByUserIdAndProductIdIn(Integer userId,List<Integer> productId);

}
