package com.mmall.controller.backend;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.ProductService;
import com.mmall.service.UploadService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/manage/product")
@Validated
public class ProductManageController {
    private final ProductService productService;
    private final UploadService uploadService;

    @Autowired
    public ProductManageController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    /**
     * 创建或者修改产品
     *
     * @param product 产品属性接收者
     * @return 结果
     */
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

    /**
     * 获取产品详情
     *
     * @param productID 必传产品ID
     * @return 产品详情
     */
    @GetMapping("/{productID}")
    public ServerResponse manageGetProductDetail(@PathVariable Integer productID) {
        return productService.getProductDetail(productID);
    }

    /**
     * 查询产品列表
     *
     * @param page         页码，从0开始
     * @param size         每页个数
     * @param sortProperty 要排序的属性
     * @param strDirection 排序的方向
     * @return 列表
     */
    @GetMapping("/list")
    public ServerResponse selectProductList(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(defaultValue = "createTime") String sortProperty,
                                            @RequestParam(defaultValue = "DESC") String strDirection) {
        Sort.Direction direction = Sort.Direction.DESC;
        if ("ASC".equals(strDirection)) {
            direction = Sort.Direction.ASC;
        }
        return productService.selectProductList(PageRequest.of(page, size, direction, sortProperty));
    }

    @GetMapping("/search")
    public ServerResponse searchProductList(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(defaultValue = "小米") String name) {
        return productService.searchProductList(name, PageRequest.of(page, size));
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file) {
        return uploadService.saveFile(file);
    }
}
