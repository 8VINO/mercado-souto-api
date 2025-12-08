package br.com.mercado_souto.model.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.product.Product;
import jakarta.transaction.Transactional;

@Service
public class CartService {
    @Autowired

    private CartRepository cartRepository;
    

    @Transactional

    public Cart create(Client client) {

        Cart newCart = Cart.builder()
                .client(client)
                .build();
        
        newCart.setActive(Boolean.TRUE);
        return cartRepository.save(newCart);
    }
    @Transactional
    public Cart addItem(Cart cart, Product product, Integer quantity){
        CartItem newCartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
        cart.getItems().add(newCartItem);
        return cartRepository.save(cart);
    }

    public Cart findByClientId(Long clientId){
        return cartRepository.findByClientId(clientId).get();
    }
}