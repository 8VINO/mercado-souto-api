package br.com.mercado_souto.model.seller;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "Seller")
@SQLRestriction("active = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends BaseEntity{
   
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Client client;
   
    @JsonIgnore
    @OneToMany(mappedBy="seller")
    private List<Product> products; 
   
    @Column(unique = true, nullable = false, length = 18)
    private String cnpj;

    @Column(nullable = false)
    @Builder.Default
    private Integer sales=0;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
    
}
