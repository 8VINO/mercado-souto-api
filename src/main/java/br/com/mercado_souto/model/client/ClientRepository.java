package br.com.mercado_souto.model.client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mercado_souto.model.acess.User;


public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);
}
