package br.com.mercado_souto.model.address;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.api.address.AddressUpdateRequest;
import br.com.mercado_souto.util.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Address create(Address address) {
        address.setActive(Boolean.TRUE);
        return addressRepository.save(address);
    }

    public List<Address> findByClient(Long idClient) {
        return addressRepository.findByClientId(idClient);

    }

    public Address findById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address", id));

        return address;
    }

    @Transactional
    public Address update(Long id, AddressUpdateRequest request) {
        
        Address address = findById(id);

        Optional.ofNullable(request.getCep()).ifPresent(address::setCep);

        Optional.ofNullable(request.getStreet()).ifPresent(address::setStreet);

        Optional.ofNullable(request.getNumber()).ifPresent(address::setNumber);

        Optional.ofNullable(request.getComplement()).ifPresent(address::setComplement);

        Optional.ofNullable(request.getAdditionalInfo()).ifPresent(address::setAdditionalInfo);

        Optional.ofNullable(request.getHome()).ifPresent(address::setHome);

        Optional.ofNullable(request.getContactName()).ifPresent(address::setContactName);

        Optional.ofNullable(request.getContactPhone()).ifPresent(address::setContactPhone);

        return addressRepository.save(address);
    }

    @Transactional
    public void delete(Long id) {
        Address address = findById(id);
        address.setActive(Boolean.FALSE);

        addressRepository.save(address);
    }
}
