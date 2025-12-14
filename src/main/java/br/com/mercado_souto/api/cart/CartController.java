package br.com.mercado_souto.api.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/cart")
@CrossOrigin
@Tag(
    name = "Cart API ",
    description = "API responsible for managing the cart in the system"
)
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Operation(
       summary = "Endpoint responsible for retrieving the cart by id.",
       description = "Receives the cart id and returns the cart data."
   )
    @GetMapping("/{cartId}")
    public Cart findById (@PathVariable Long cartId) {
        return cartService.findById(cartId);
    }

    @Operation(
       summary = "Endpoint responsible for adding a product to the cart or updating its quantity.",
       description = "Receives the cart id and product id in the path variables, and the quantity in the request body, and returns the updated cart."
   )
    @PostMapping("/{cartId}/product/{productId}")
    public Cart addItem(@PathVariable Long cartId, @PathVariable Long productId, @RequestBody @Valid CartRequest request) {

        Cart cart = cartService.findById(cartId);
        Product product = productService.findById(productId);
        return cartService.addItem(cart, product, request.getQuantity());
    }

    @Operation(
       summary = "Endpoint responsible for deleting a product from the cart",
       description = "Receives the cart id and product id in the path variables and returns the updated cart."
   )
    @DeleteMapping("/{cartId}/product/{productId}")
    public Cart removeItem(@PathVariable Long cartId, @PathVariable Long productId) {

        Cart cart = cartService.findById(cartId);
        Product product = productService.findById(productId);
        return cartService.removeItem(cart, product);
    }

      @Operation(
       summary = "Endpoint responsible for marking a product in the cart as selected.",
       description = "Receives the cart id and product id in the path variables and returns the updated cart."
   )
    @PutMapping("/{cartId}/product/{productId}")
    public Cart selectItem(@PathVariable Long cartId, @PathVariable Long productId) {

        Cart cart = cartService.findById(cartId);
        Product product = productService.findById(productId);
        return cartService.selectItem(cart, product);
    }

}
