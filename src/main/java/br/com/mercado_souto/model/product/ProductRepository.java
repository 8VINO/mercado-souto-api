package br.com.mercado_souto.model.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySellerId(Long idSeller);

    List<Product> findByCategoryId(Long categoryId);
}
