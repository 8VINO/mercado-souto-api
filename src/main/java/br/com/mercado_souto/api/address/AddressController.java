package br.com.mercado_souto.api.address;

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

import br.com.mercado_souto.model.address.Address;
import br.com.mercado_souto.model.address.AddressService;
import br.com.mercado_souto.model.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/address")
@CrossOrigin
@Tag(
    name = "Address API",
    description = "API responsible for managing addresses in the system"
)
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ClientService clientService;

     @Operation(
       summary = "Endpoint responsible for creating an address",
       description = "Receives the client id and address data in the request body."
   )
    @PostMapping("/{idClient}")
    public ResponseEntity<Address> create (@PathVariable Long idClient, @RequestBody @Valid AddressRequest request){
        Address newAddress = request.build();
        newAddress.setClient(clientService.findById(idClient));
        Address address = addressService.create(newAddress);
        return  ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
      @Operation(
       summary = "Endpoint responsible for getting all addresses from a specific client",
       description = "Receives the client id and returns a list of address this client."
   )
    @GetMapping("/by-client/{idClient}")
    public ResponseEntity<List<Address>> findByClient(@PathVariable Long idClient){
        clientService.findById(idClient);
        List<Address> list = addressService.findByClient(idClient);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    
       @Operation(
       summary = "Endpoint responsible for getting a specific address",
       description = "Receives the address id and returns the specific address."
   )
    @GetMapping("/{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id){
        Address address = addressService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(address);
        
    }

     @Operation(
       summary = "Endpoint responsible for updating the address",
       description = "Receives the address id and returns the updated address."
   )
    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody @Valid AddressRequest request){
        Address address = addressService.update(id,request.build());

        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

      @Operation(
       summary = "Endpoint responsible for deleting the address",
       description = "Receives the address id, deletes the address and returns status 204."
   )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        addressService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
