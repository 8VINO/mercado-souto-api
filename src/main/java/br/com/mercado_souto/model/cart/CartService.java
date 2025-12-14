package br.com.mercado_souto.model.cart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.util.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    private Optional<CartItem> findCartItemByProductId(Cart cart, Product product) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
    }

    @Transactional
    public Cart create(Client client) {

        Cart newCart = Cart.builder()
                .client(client)
                .build();

        newCart.setActive(Boolean.TRUE);
        return cartRepository.save(newCart);
    }

    @Transactional
    public Cart addItem(Cart cart, Product product, Integer quantity) {
        Optional<CartItem> existingItem = findCartItemByProductId(cart, product);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(quantity);
        } else {
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .isSelected(Boolean.TRUE)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(newCartItem);
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItem(Cart cart, Product product) {
        Optional<CartItem> existingItem = findCartItemByProductId(cart, product);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            new EntityNotFoundException(product.getId());
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart selectItem(Cart cart, Product product) {
        Optional<CartItem> existingItem = findCartItemByProductId(cart, product);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setIsSelected(!item.getIsSelected());
        } else {
            throw new EntityNotFoundException(product.getId());
        }
        return cartRepository.save(cart);
    }

    public Cart findById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart", cartId));
    }

    @Transactional
    public Cart removeSelectedItems(Cart cart) {
        
    List<CartItem> itemsToRemove = cart.getItems().stream()
        .filter(CartItem::getIsSelected) 
        .collect(Collectors.toList());
        
    cart.getItems().removeAll(itemsToRemove);
    
   
    return cartRepository.save(cart);
    }
}