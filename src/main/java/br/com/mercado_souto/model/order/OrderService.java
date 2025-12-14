package br.com.mercado_souto.model.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.model.address.Address;
import br.com.mercado_souto.model.cart.Cart;
import br.com.mercado_souto.model.cart.CartItem;
import br.com.mercado_souto.model.cart.CartService;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;
import br.com.mercado_souto.model.seller.Seller;
import br.com.mercado_souto.model.seller.SellerService;
import br.com.mercado_souto.util.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private SellerService sellerService;

        @Autowired
        private ProductService productService;
       
        @Autowired
        private CartService cartService;

        @Transactional
        public Order placeOrderFromCart(Cart cart, Address clientAddress) {
                Order newOrder = Order.builder()
                                .client(cart.getClient())
                                .clientAddress(clientAddress)
                                .status(OrderStatus.PENDING)
                                .build();

                List<OrderItem> orderItems = cart.getItems().stream()
                                .filter(CartItem::getIsSelected)
                                .map(cartItem -> createOrderItem(cartItem, newOrder))
                                .collect(Collectors.toList());

                BigDecimal finalPrice = orderItems.stream()
                                .map(OrderItem::getSubTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                newOrder.setOrderItems(orderItems);
                newOrder.setTotalPrice(finalPrice);

                Order savedOrder = orderRepository.save(newOrder);
                paymentOrder(savedOrder);
                cartService.removeSelectedItems(cart);

                return savedOrder;

        }

        @Transactional
        public Order placeDirectOrder(Product product, Address clientAddress, Integer quantity) {

                BigDecimal unitPriceAtOrderTime = product.getPrice();
                BigDecimal subtotalAtOrderTime = product.getPrice().multiply(BigDecimal.valueOf(quantity));

                Order newOrder = Order.builder()
                                .client(clientAddress.getClient())
                                .clientAddress(clientAddress)
                                .status(OrderStatus.PENDING)
                                .build();

                OrderItem newOrderItem = OrderItem.builder()
                                .order(newOrder)
                                .product(product)
                                .unitPrice(unitPriceAtOrderTime)
                                .quantity(quantity)
                                .subTotal(subtotalAtOrderTime)
                                .build();

                List<OrderItem> orderItems = new java.util.ArrayList<>(List.of(newOrderItem));
                newOrder.setOrderItems(orderItems);
                newOrder.setTotalPrice(subtotalAtOrderTime);
                Order savedOrder = orderRepository.save(newOrder);
                paymentOrder(savedOrder);

                return savedOrder;
        }

        @Transactional
        private OrderItem createOrderItem(CartItem cartItem, Order order) {

                BigDecimal unitPriceAtOrderTime = cartItem.getUnitPrice();

                BigDecimal subtotalAtOrderTime = cartItem.getSubtotal();

                return OrderItem.builder()
                                .order(order)
                                .product(cartItem.getProduct())
                                .unitPrice(unitPriceAtOrderTime)
                                .quantity(cartItem.getQuantity())
                                .subTotal(subtotalAtOrderTime)
                                .build();
        }

        @Transactional
        public Order paymentOrder(Order order) {

                processOrderItems(order);

                order.setStatus(OrderStatus.PAID);

                Order savedOrder = orderRepository.save(order);

                return savedOrder;
        }

        @Transactional
        public Order findById(Long orderId) {
                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new EntityNotFoundException("Order", orderId));
                return order;
        }

        private void processOrderItems(Order order) {

                for (OrderItem item : order.getOrderItems()) {

                        Product product = item.getProduct();
                        Seller seller = product.getSeller(); 

                        int quantitySold = item.getQuantity();
                        BigDecimal amountSold = item.getSubTotal();

                        productService.decrementStock(product.getId(), quantitySold);

                        BigDecimal amountToCredit = amountSold;

                        sellerService.increaseBalance(seller.getId(), amountToCredit);
                }
        }

}
