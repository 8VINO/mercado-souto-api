package br.com.mercado_souto.api.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercado_souto.model.address.Address;
import br.com.mercado_souto.model.address.AddressService;
import br.com.mercado_souto.model.cart.Cart;
import br.com.mercado_souto.model.cart.CartService;
import br.com.mercado_souto.model.order.Order;
import br.com.mercado_souto.model.order.OrderService;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
@RequestMapping("/api/order")
@Tag(
    name = "Order API ",
    description = "API responsible for managing orders in the system"
)
public class OrderController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Operation(
    summary = "Endpoint responsible for placing orders using the items in the cart.",
    description = "Receives the cart id and the selected client address id, and returns the order."
    )
    @PostMapping("/cart/{cartId}/address/{clientAddressId}")
    public Order placeOrderFromCart(@PathVariable Long cartId, @PathVariable Long clientAddressId) {
        
        Cart cart = cartService.findById(cartId);
        Address address = addressService.findById(clientAddressId);

        return orderService.placeOrderFromCart(cart, address);
        
    }

    @Operation(
    summary = "Endpoint responsible for placing direct orders from product details.",
    description = "Receives the product id and quantity in the request body, along with the selected client address id, and returns the order."
    )
    @PostMapping("/product/{productId}/address/{clientAddressId}")
    public Order placeDirectOrder(@PathVariable Long productId, @PathVariable Long clientAddressId, @RequestBody OrderRequest request) {
        
        Product product = productService.findById(productId);
        Address clientAddress = addressService.findById(clientAddressId);

        return orderService.placeDirectOrder(product, clientAddress, request.getQuantity());
        
    }
    
}
