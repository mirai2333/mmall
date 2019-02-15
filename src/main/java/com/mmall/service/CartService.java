package com.mmall.service;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.repository.CartRepository;
import com.mmall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @description:
 * @author: Mirai.Yang
 * @create: 2019-02-15 11:01
 * <pre>
 *       ██████╗   █████╗  ██╗  ██╗ ██╗  ██╗ ██╗
 *      ██╔════╝  ██╔══██╗ ██║ ██╔╝ ██║ ██╔╝ ██║
 *      ██║  ███╗ ███████║ █████╔╝  █████╔╝  ██║
 *      ██║   ██║ ██╔══██║ ██╔═██╗  ██╔═██╗  ██║
 *      ╚██████╔╝ ██║  ██║ ██║  ██╗ ██║  ██╗ ██║
 *       ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝
 *  </pre>
 */
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public ServerResponse<Integer> add(Integer userId, Integer productId, Integer num) {
        Optional<Cart> optionalCart = cartRepository.findByUserIdAndProductId(userId, productId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            int quantity = cart.getQuantity() + this.subtractStock(num, productId);
            cart.setQuantity(quantity);
            cart.setUpdateTime(new Date());
            cartRepository.save(cart);
            return ServerResponse.createBySuccess(quantity);
        }else {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(this.subtractStock(num,productId));
            cart.setChecked(Const.CartStatus.CHECKED);
            cart.setCreateTime(new Date());
            cartRepository.save(cart);
            return ServerResponse.createBySuccess(cart.getQuantity());
        }
    }

    private synchronized int subtractStock(int num, int productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int productStock = product.getProductStock();
            num = productStock < num ? productStock : num;
            product.setProductStock(productStock - num);
            productRepository.save(product);
        } else {
            num = 0;
        }
        return num;
    }
}
