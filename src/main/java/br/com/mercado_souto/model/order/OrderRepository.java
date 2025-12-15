package br.com.mercado_souto.model.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.orderItems item " +
           "WHERE o.client.id = :clientId " +
           "AND item.product.id = :productId " +
           "AND (o.status = 'PAID')")
    boolean existsByClientAndProduct(@Param("clientId") Long clientId, @Param("productId") Long productId);
}
