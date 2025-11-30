package br.com.mercado_souto.model.address;

import org.hibernate.annotations.SQLRestriction;

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

    @ManyToOne
    private Client client;

    @Column
    private String cep;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String complement;

    @Column
    private String additionalInfo;

    @Column
    private Boolean home;

    @Column
    private String contactName;

    @Column
    private String contactPhone;
}
