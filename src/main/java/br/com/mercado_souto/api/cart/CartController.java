package br.com.mercado_souto.api.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercado_souto.model.cart.Cart;
import br.com.mercado_souto.model.cart.CartService;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{cartId}")
    public Cart findById (@PathVariable Long cartId) {
        return cartService.findById(cartId);
    }
    
    @PostMapping("/{cartId}/product/{productId}")
    public Cart addItem(@PathVariable Long cartId, @PathVariable Long productId, @RequestBody CartRequest request) {

        Cart cart = cartService.findById(cartId);
        Product product = productService.findById(productId);
        return cartService.addItem(cart, product, request.getQuantity());
    }
    
    @DeleteMapping("/{cartId}/product/{productId}")
    public Cart removeItem(@PathVariable Long cartId, @PathVariable Long productId) {

        Cart cart = cartService.findById(cartId);
        Product product = productService.findById(productId);
        return cartService.removeItem(cart, product);
    }

    @PutMapping("/{cartId}/product/{productId}")
    public Cart selectItem(@PathVariable Long cartId, @PathVariable Long productId) {

        Cart cart = cartService.findById(cartId);
        Product product = productService.findById(productId);
        return cartService.selectItem(cart, product);
    }

}
