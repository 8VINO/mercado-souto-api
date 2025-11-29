package br.com.mercado_souto.model.category;

import org.hibernate.annotations.SQLRestriction;

import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "Category")
@SQLRestriction("active = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
    
    @Column
    private String name; 
    
}
