package br.com.mercado_souto.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import br.com.mercado_souto.model.category.Category;
import br.com.mercado_souto.model.seller.Seller;
import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@SQLRestriction("active = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @ManyToOne
    private Seller seller;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String specification;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private Integer stock;

    @ManyToOne
    private Category category;

    @ElementCollection
    @Builder.Default
    private List<String> imageURL = new ArrayList<>();

}
