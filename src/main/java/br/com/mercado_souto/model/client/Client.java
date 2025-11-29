package br.com.mercado_souto.model.client;

import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import br.com.mercado_souto.model.address.Address;
import br.com.mercado_souto.model.seller.Seller;
import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Client")
@SQLRestriction("active = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends BaseEntity  {
    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;
    
    @Column
    private String cpf;
    
    @Column
    private String phone;

    @Column
    private List<Address> addresses;
    
    @OneToOne(mappedBy="client")
    private Seller seller;
    
    
    

}
