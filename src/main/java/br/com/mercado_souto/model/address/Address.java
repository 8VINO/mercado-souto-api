package br.com.mercado_souto.model.address;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.util.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "Address")
@SQLRestriction("active = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {

    @ManyToOne(optional = false)
    @JsonIgnore
    private Client client;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 150)
    private String street;

    @Column(nullable = false, length = 10)
    private String number;

    @Column(length = 50)
    private String complement;

    @Column(length = 100)
    private String additionalInfo;

    @Column(nullable = false)
    private Boolean home;

    @Column(nullable = false, length = 70)
    private String contactName;

    @Column(nullable = false, length = 16)
    private String contactPhone;
}
