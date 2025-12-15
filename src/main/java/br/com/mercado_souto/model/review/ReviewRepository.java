package br.com.mercado_souto.model.review;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.product.Product;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product = :product AND r.active = TRUE")
    Optional<Double> findAverageRatingByProduct(Product product);
    
    long countByProduct(Product product);

    boolean existsByClientAndProduct(Client client, Product product);

    List<Review> findByProductId(Long productId);
    
}
