package com.mmall.pojo.vo;

import com.mmall.common.BigDecimalUtil;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;

/**
 * @description:
 * @author: Mirai.Yang
 * @create: 2019-02-18 11:57
 * <pre>
 *       ██████╗   █████╗  ██╗  ██╗ ██╗  ██╗ ██╗
 *      ██╔════╝  ██╔══██╗ ██║ ██╔╝ ██║ ██╔╝ ██║
 *      ██║  ███╗ ███████║ █████╔╝  █████╔╝  ██║
 *      ██║   ██║ ██╔══██║ ██╔═██╗  ██╔═██╗  ██║
 *      ╚██████╔╝ ██║  ██║ ██║  ██╗ ██║  ██╗ ██║
 *       ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝  ╚═╝ ╚═╝
 *  </pre>
 */
public class Convertor {
    public static CartProductVo converte(Cart cart){
        CartProductVo vo = new CartProductVo();
        vo.setCartId(cart.getCartId());
        vo.setUserId(cart.getUserId());
        vo.setProductId(cart.getProductId());
        vo.setQuantity(cart.getQuantity());
        vo.setChecked(cart.getChecked());

        Product product = cart.getProduct();
        vo.setProductName(product.getProductName());
        vo.setProductSubtitle(product.getProductSubtitle());
        vo.setProductMainImage(product.getProductMainImage());
        vo.setProductPrice(product.getProductPrice());
        vo.setProductStatus(product.getProductStatus());
        vo.setProductStock(product.getProductStock());

        vo.setProductTotalPrice(BigDecimalUtil.mul(cart.getQuantity().doubleValue(), product.getProductPrice().doubleValue()));
        return vo;
    }
}
