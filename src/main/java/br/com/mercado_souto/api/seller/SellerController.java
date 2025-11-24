package br.com.mercado_souto.api.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.mercado_souto.model.seller.Seller;
import br.com.mercado_souto.model.seller.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/api/seller")
@Tag(
    name = "Seller API",
    description = "API responsible for managing sellers in the system"
)
public class SellerController {
    @Autowired
    private SellerService sellerService;

      @Operation(
       summary = "Endpoint responsible for registering a seller",
       description = "Receives the client id who wants to become a seller, creates the seller, and returns the seller."
   )
    @PostMapping("/{idClient}")
    public ResponseEntity<Seller> create(@PathVariable Long idClient, @RequestBody SellerRequest request){
        Seller seller=sellerService.create(idClient, request.build());

        return ResponseEntity.status(HttpStatus.CREATED).body(seller);
    }

      @Operation(
       summary = "Endpoint responsible for getting all sellers",
       description = "Returns a list of sellers."
   )
    @GetMapping
    ResponseEntity<List<Seller>> findAll(){

        List<Seller> sellers = sellerService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(sellers);
    }

       @Operation(
       summary = "Endpoint responsible for getting a specific seller",
       description = "Receives the seller id and returns the specific seller."
   )
    @GetMapping("/{id}")
    ResponseEntity<Seller> findById(@PathVariable Long id){

        Seller seller = sellerService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(seller);
    }

      @Operation(
       summary = "Endpoint responsible for updating the seller",
       description = "Receives the seller id and returns the updated seller."
   )
    @PutMapping("/{id}")
    ResponseEntity<Seller> update(@PathVariable Long id, @RequestBody SellerRequest request){

        Seller seller = sellerService.update(id, request.build());

        return ResponseEntity.status(HttpStatus.OK).body(seller);
    }

      @Operation(
       summary = "Endpoint responsible for deleting the seller",
       description = "Receives the seller id, deletes the seller and returns status 204."
   )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id){

        sellerService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
