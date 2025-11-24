package br.com.mercado_souto.api.client;

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

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/client")
@CrossOrigin
@Tag(
    name = "Client API ",
    description = "API responsible for managing clients in the system"
)
public class ClientController {
    @Autowired
    ClientService clientService;
    
     @Operation(
       summary = "Endpoint responsible for registering a client",
       description = "Receives the client data in the request body, creates and return the client."
   )

    @PostMapping
    ResponseEntity<Client> create(@RequestBody ClientRequest request ){

        Client client = clientService.create(request.build());

        return ResponseEntity.status(HttpStatus.CREATED).body(client);

    }
     @Operation(
       summary = "Endpoint responsible for getting all clients",
       description = "Returns a list of clients."
   )
    @GetMapping
    ResponseEntity<List<Client>> findAll(){

        List<Client> clients = clientService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }
      @Operation(
       summary = "Endpoint responsible for getting a specific client",
       description = "Receives the client id and returns the specific client."
   )
    @GetMapping("/{id}")
    ResponseEntity<Client> findById(@PathVariable Long id){

        Client client = clientService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

       @Operation(
       summary = "Endpoint responsible for updating the client",
       description = "Receives the client id and returns the updated client."
   )
    @PutMapping("/{id}")
    ResponseEntity<Client> update(@PathVariable Long id, @RequestBody ClientRequest request){

        Client client = clientService.update(id, request.build());

        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

      @Operation(
       summary = "Endpoint responsible for deleting the client",
       description = "Receives the client id, deletes the client and returns status 204."
   )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id){

        clientService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
