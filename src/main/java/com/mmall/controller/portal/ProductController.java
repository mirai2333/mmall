package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.service.CategoryService;
import com.mmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 前台产品控制器
 * @author: Mirai.Yang
 * @create: 2019-02-14 18:03
 * <pre>
 *       ██████╗   █████╗  ██╗  ██╗ ██╗  ██╗ ██╗
 *      ██╔════╝  ██╔══██╗ ██║ ██╔╝ ██║ ██╔╝ ██║
 *      ██║  ███╗ ███████║ █████╔╝  █████╔╝  ██║
 *      ██║   ██║ ██╔══██║ ██╔═██╗  ██╔═██╗  ██║
 *      ╚██████╔╝ ██║  ██║ ██║  ██╗ ██║  ██╗ ██║
 *       ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝
 *  </pre>
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{productId}")
    public ServerResponse getProductDetail(@PathVariable Integer productId) {
        ServerResponse<Product> detail = productService.getProductDetail(productId);
        if (detail.isSuccess() && detail.getData().getProductStatus() == Const.ProductStatus.ON_SELL) {
            return detail;
        }
        return ServerResponse.createByErrorMsg("找不到产品");
    }

    @GetMapping("/list")
    public ServerResponse getProductList(Integer categoryID, String keywords,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        List<Integer> categoryIdList = new ArrayList<>();
        int status = Const.PortalListStatus.NONE;
        if (categoryID != null){
            categoryIdList.addAll(categoryService.getAllChildren(categoryID).getData());
            status = Const.PortalListStatus.CATEGORY;
        }
        if (!StringUtils.isEmpty(keywords)){
            keywords = "%" + keywords + "%";
            status = Const.PortalListStatus.KEYWORDS;
        }
        if (categoryID != null && !StringUtils.isEmpty(keywords)){
            status = Const.PortalListStatus.ALL;
        }
        return productService.portalProductList(status,categoryIdList,keywords, PageRequest.of(page,size));
    }

}
