package br.com.mercado_souto.model.address;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByClientId(Long idClient);
}
