package br.com.mercado_souto.model.acess;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "Role")
@SQLRestriction("active = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Role extends BaseEntity implements GrantedAuthority {
    public static final String ROLE_CLIENT = "ROLE_CLIENT";
    public static final String ROLE_SELLER = "ROLE_SELLER";
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.name;
    }

}
