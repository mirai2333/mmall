package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Cart;
import com.mmall.repository.CartRepository;
import com.mmall.service.CartService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @description:
 * @author: Mirai.Yang
 * @create: 2019-02-15 14:53
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
@RequestMapping("/cart")
@Validated
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/update")
    public ServerResponse update(@RequestParam Integer userId, @RequestParam Integer productId,
                              @RequestParam @Range(min = -20, max = 20) Integer num) {
        return cartService.update(userId, productId, num);
    }

    @DeleteMapping
    public ServerResponse delete(@RequestParam Integer userId, @RequestParam Integer[] productId) {
        cartService.delete(userId, productId);
        return ServerResponse.createBySuccess();
    }

    @GetMapping("/test")
    public String test(){
        Cart cart = cartRepository.findById(121).get();
        return String.valueOf(cart.getUserId());
    }
}

