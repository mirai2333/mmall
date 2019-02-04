package com.mmall.repository;

import com.mmall.pojo.Product;
import com.mmall.pojo.vo.ProductListVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void test() {
        Page<Product> page = productRepository.
                findProductList(PageRequest.of(0, 10, Sort.Direction.DESC, "createTime"));
        List<Product> list = page.getContent();
        System.out.println(list);

    }
}