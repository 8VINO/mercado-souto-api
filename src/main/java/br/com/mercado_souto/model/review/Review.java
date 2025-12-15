package br.com.mercado_souto.model.review;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Review", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"client_id", "product_id"}) 
})
@SQLRestriction("active = true")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity{
 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties({
    "user",
    "addresses",
    "cart",
    "orders",
    "favoriteProducts",
    "email",
    "cpf",
    "phone"
})
    private Client client;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer rating; 

    @Column(columnDefinition = "TEXT")
    private String comment;
}
