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
   
    @OneToOne
    @JsonIgnore
    private Client client;
   
    @Column
    private String cnpj;

    @Column
    private Integer sales;

    @Column
    private BigDecimal balance;
    
    @JsonIgnore
    @OneToMany(mappedBy="seller")
    private List<Product> products; 
}
