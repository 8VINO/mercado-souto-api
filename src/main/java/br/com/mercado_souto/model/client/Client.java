package br.com.mercado_souto.model.client;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.address.Address;
import br.com.mercado_souto.model.cart.Cart;
import br.com.mercado_souto.model.order.Order;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.seller.Seller;
import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @JsonIgnoreProperties({ "client" })
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "client")
    private Cart cart;

    @OneToMany(mappedBy = "client")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();;

    @ManyToMany
    @JoinTable(name = "client_favorite_products", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @Builder.Default
    private List<Product> favoriteProducts = new ArrayList<>();

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false, length = 70)
    private String name;

    @Column(nullable = false, length = 50)
    @JsonIgnore
    private String password;

    @Column(length = 16)
    private String phone;

}
