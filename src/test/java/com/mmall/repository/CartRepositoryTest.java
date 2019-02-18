package com.mmall.repository;

import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
//    @Transactional
    public void test(){
//        List<Cart> list = cartRepository.findAllByUserId(21);
//        System.out.println(list);
        Product product = productRepository.findById(29).get();
        System.out.println(product);
    }

}