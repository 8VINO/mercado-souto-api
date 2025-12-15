package br.com.mercado_souto.model.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.order.OrderRepository;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;
import br.com.mercado_souto.util.exception.BusinessRuleException;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductService productService; 

    @Transactional
    public Review create(Client client, Product product, Integer rating, String comment) {
  
        boolean hasPurchased = orderRepository.existsByClientAndProduct(client.getId(), product.getId());

        if (!hasPurchased) {
            throw new BusinessRuleException("Product must be purchased before review.");
        }

        if (reviewRepository.existsByClientAndProduct(client, product)) {
            throw new BusinessRuleException("You have already reviewed this product.");
        }

        Review newReview = Review.builder()
            .client(client)
            .product(product)
            .rating(rating)
            .comment(comment)
            .build();
        newReview.setActive(Boolean.TRUE); 
        Review savedReview = reviewRepository.save(newReview);
        
        productService.updateProductRating(product); 
        return savedReview;
    }

    public List<Review> findReviewsByProductId(Long productId) {
       
        return reviewRepository.findByProductId(productId);
    }
}
