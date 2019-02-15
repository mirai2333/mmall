package com.mmall.service;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ServerResponse<String> saveOrUpdateProduct(Product product) {
        //这个product不可能为空，因为我要在controller的参数里面使用依赖注入注入这个类，SpringMVC应该会创建这个类的实例，然后填充这个实例的属性值。
        product.setCreateTime(new Date());
        productRepository.findCreateTimeByProductID(product.getProductID())
                .ifPresent(product::setCreateTime);
        product.setUpdateTime(new Date());
        productRepository.save(product);
        return ServerResponse.createBySuccess();
    }

    @Transactional(rollbackFor = Exception.class)
    public ServerResponse<String> updateProductStatus(int status, Integer productID) {
        productRepository.updateProductStatus(status, productID);
        return ServerResponse.createBySuccess();
    }

    public ServerResponse<Product> getProductDetail(int productID) {
        return productRepository.findById(productID)
                .map(ServerResponse::createBySuccess)
                .orElse(ServerResponse.createByErrorMsg("产品不存在！"));
    }

    public ServerResponse<Page<Product>> selectProductList(Pageable pageable) {
        Page<Product> productPage = productRepository.findProductList(pageable);
        return ServerResponse.createBySuccess(productPage);
    }

    public ServerResponse<Page<Product>> searchProductList(String productName, Pageable pageable) {
        Page<Product> productPage = productRepository.searchProductList(productName, pageable);
        return ServerResponse.createBySuccess(productPage);
    }

    public ServerResponse<Page<Product>> portalProductList(int status, List<Integer> categoryIdList, String keywords, Pageable pageable) {
        switch (status) {
            case Const.PortalListStatus.ALL:
                return ServerResponse.createBySuccess(productRepository.findAllByCategoryIDInAndProductNameLike(categoryIdList, keywords, pageable));
            case Const.PortalListStatus.CATEGORY:
                return ServerResponse.createBySuccess(productRepository.findAllByCategoryIDIn(categoryIdList, pageable));
            case Const.PortalListStatus.KEYWORDS:
                return ServerResponse.createBySuccess(productRepository.findAllByProductNameLike(keywords, pageable));
            default:
                return ServerResponse.createBySuccess(productRepository.findAll(pageable));
        }
    }

}
