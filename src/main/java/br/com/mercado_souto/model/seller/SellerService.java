package br.com.mercado_souto.model.seller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercado_souto.api.seller.SellerUpdateRequest;
import br.com.mercado_souto.model.acess.Role;
import br.com.mercado_souto.model.acess.RoleRepository;
import br.com.mercado_souto.util.exception.DataAlreadyExistsException;
import br.com.mercado_souto.util.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Seller create(Seller seller){
        if (sellerRepository.existsByCnpj(seller.getCnpj())) {
            throw new DataAlreadyExistsException("CNPJ");
        }
        Role sellerRole = roleRepository.findByName(Role.ROLE_SELLER);
        seller.getClient().getUser().getRoles().add(sellerRole);
        seller.setActive(Boolean.TRUE);
        
        return sellerRepository.save(seller);


    }

    public List<Seller> findAll(){

        return sellerRepository.findAll();
    }

    
    public Seller findById(Long id){
        Seller seller = sellerRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Seller",id));
        
        return seller;
    }


    @Transactional
    public Seller update (Long id, SellerUpdateRequest request){

        Seller seller = findById(id);
        
       Optional.ofNullable(request.getCnpj()).ifPresent(seller::setCnpj);
       
       
        return sellerRepository.save(seller);
    }

    @Transactional
    public void delete(Long id){
        Seller seller = findById(id);
        seller.setActive(Boolean.FALSE);

        sellerRepository.save(seller);
    }

    @Transactional
    public Seller increaseBalance(Long sellerId, BigDecimal amount) {
        
        Seller seller = findById(sellerId);
      
        BigDecimal currentBalance = seller.getBalance() != null ? seller.getBalance() : BigDecimal.ZERO;
       
        seller.setBalance(currentBalance.add(amount));
     
        Integer currentSales = seller.getSales() != null ? seller.getSales() : 0;
        
     
        seller.setSales(currentSales + 1);
        
     
        return sellerRepository.save(seller);
    }
}
