package br.com.mercado_souto.model.client;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.order.Order;
import br.com.mercado_souto.model.product.Product;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);

    boolean existsByCpf(String cpf);

    boolean existsByUser(User user);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    @Query("SELECT p FROM Client c JOIN c.favoriteProducts p WHERE c = :client ORDER BY p.id DESC")
    Page<Product> findFavoriteProducts(
            @Param("client") Client client,
            Pageable pageable);

    @Query("SELECT o FROM Client c JOIN c.orders o WHERE c = :client ORDER BY o.id DESC")
    Page<Order> findOrdersByClient(
            @Param("client") Client client,
            Pageable pageable);
}
