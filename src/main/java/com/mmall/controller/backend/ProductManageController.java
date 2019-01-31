package com.mmall.controller.backend;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.ProductService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/manage/product")
@Validated
public class ProductManageController {
    private final ProductService productService;

    @Autowired
    public ProductManageController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ServerResponse saveOrUpdateProduct(Product product) {
        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 更新产品状态
     *
     * @param status    产品状态
     * @param productID 产品ID
     */
    @PutMapping("/update/status")
    public ServerResponse updateProductStatus(@Range(min = 1, max = 3, message = "产品状态值错误") Integer status,
                                              @RequestParam Integer productID) {
        return productService.updateProductStatus(status, productID);
    }

}
