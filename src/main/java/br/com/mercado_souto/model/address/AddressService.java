package br.com.mercado_souto.model.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Address update(Long id, Address modifiedAddress) {
        Address address = findById(id);

        address.setCep(modifiedAddress.getCep());
        address.setStreet(modifiedAddress.getStreet());
        address.setNumber(modifiedAddress.getNumber());
        address.setComplement(modifiedAddress.getComplement());
        address.setAdditionalInfo(modifiedAddress.getAdditionalInfo());
        address.setHome(modifiedAddress.getHome());
        address.setContactName(modifiedAddress.getContactName());
        address.setContactPhone(modifiedAddress.getContactPhone());

        return addressRepository.save(address);
    }

    @Transactional
    public void delete(Long id) {
        Address address = findById(id);
        address.setActive(Boolean.FALSE);

        addressRepository.save(address);
    }
}
