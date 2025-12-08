package br.com.mercado_souto.model.client;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.address.Address;
import br.com.mercado_souto.model.cart.Cart;
import br.com.mercado_souto.model.seller.Seller;
import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
public class Client extends BaseEntity {

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToOne(mappedBy = "client")
    private Seller seller;

    @OneToMany(mappedBy = "client")
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties({"client"})
    private List<Address> addresses;

    @OneToOne(mappedBy = "client")
    private Cart cart;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String cpf;

    @Column
    private String phone;

}
