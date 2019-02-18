package com.mmall.service;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.pojo.vo.CartProductVo;
import com.mmall.pojo.vo.CartVo;
import com.mmall.pojo.vo.Convertor;
import com.mmall.repository.CartRepository;
import com.mmall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ServerResponse<Integer> update(Integer userId, Integer productId, Integer num) {
        Optional<Cart> optionalCart = cartRepository.findByUserIdAndProductId(userId, productId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            int quantity = cart.getQuantity() + num;
            cart.setQuantity(quantity > 0 ? quantity : 1);
            cart.setUpdateTime(new Date());
            cartRepository.save(cart);
            return ServerResponse.createBySuccess(quantity);
        } else {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setQuantity(num > 0 ? num : 1);
            cart.setProduct(new Product(productId));
            cart.setChecked(Const.CartStatus.CHECKED);
            cart.setCreateTime(new Date());
            cartRepository.save(cart);
            return ServerResponse.createBySuccess(cart.getQuantity());
        }
    }
    public void delete(Integer userId,Integer[] productId){
        cartRepository.deleteAllByUserIdAndProductIdIn(userId, Arrays.asList(productId));
    }

    public ServerResponse<CartVo> list(Integer userId) {
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = cartRepository.findAllByUserId(userId).stream()
                .map(Convertor::converte)
                .peek(vo -> {
                    cartVo.setCartTotalPrice(cartVo.getCartTotalPrice().add(vo.getProductTotalPrice()));
                    if (vo.getChecked()==Const.CartStatus.UN_CHECKED){ cartVo.setAllChecked(false); }
                })
                .collect(Collectors.toList());
        cartVo.setCartProductVoList(cartProductVoList);
        return ServerResponse.createBySuccess(cartVo);
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
