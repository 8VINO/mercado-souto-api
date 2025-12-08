package br.com.mercado_souto.api.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercado_souto.model.cart.Cart;
import br.com.mercado_souto.model.cart.CartService;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @Autowired
    private ProductService productService;

    @PostMapping("/client/{clientId}/product/{productId}")
    public Cart addItem (@PathVariable Long clientId, @PathVariable Long productId,@RequestBody CartRequest request) {


        Cart cart = cartService.findByClientId(clientId);
        Product product = productService.findById(productId);
        return cartService.addItem(cart, product, request.getQuantity());
    }

}
