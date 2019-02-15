package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.service.CartService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/add")
    public ServerResponse add(@RequestParam @Min(0) Integer userId, @RequestParam @Min(0) Integer productId,
                              @RequestParam @Range(min = 1, max = 20) Integer num) {
        return cartService.add(userId, productId, num);
    }
}
