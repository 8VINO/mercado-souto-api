package br.com.mercado_souto.api.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercado_souto.model.acess.Role;
import br.com.mercado_souto.model.acess.User;
import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.client.ClientService;
import br.com.mercado_souto.model.order.Order;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;
import br.com.mercado_souto.model.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/client")
@CrossOrigin
@Tag(
    name = "Client API ",
    description = "API responsible for managing clients in the system"
)
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    @Autowired
    private  JwtService jwtService;

     @Operation(
       summary = "Endpoint responsible for registering a client",
       description = "Receives the client data in the request body, creates the client, and returns the client information."
   )

    @PostMapping
    ResponseEntity<Map<Object, Object>> create(@RequestBody @Valid ClientRequest request ){

        Client clientCreated = clientService.create(request.build());
       
        User authenticatedUser = clientCreated.getUser();
        
        String jwtToken = jwtService.generateToken(authenticatedUser);
        
        Map<Object, Object> registerResponse = new HashMap<>();
        registerResponse.put("clientId", clientCreated.getId());
        registerResponse.put("token", jwtToken);
        registerResponse.put("tokenExpiresIn", jwtService.getExpirationTime());
        registerResponse.put("roles", authenticatedUser.getRoles()
                .stream()
                .map(Role::getName)
                .toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);

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
    ResponseEntity<Client> update(@PathVariable Long id, @RequestBody @Valid ClientUpdateRequest request){

        Client client = clientService.update(id, request);

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

    @Operation(
        summary = "Endpoint responsible for adding a product to favorites",
        description = "Receives the client id and product id, and returns status 200 if the product was added, or 409 if the product is already in favorites"
    )
    @PostMapping("{clientId}/favorite-product/{productId}")
    public ResponseEntity<String> addFavoriteProduct (@PathVariable Long clientId, @PathVariable Long productId){
        Client client=clientService.findById(clientId);
        Product product=productService.findById(productId);

        Boolean added = clientService.addFavoriteProduct(client, product);

        if(!added){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product is already in favorites");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product added to favorites");
    }

    @Operation(
        summary = "Endpoint responsible for removing a product from favorites",
        description = "Receives the client id and product id, and returns status 200 if the product was removed, or 404 if the product was not found in favorites"
    )
    @DeleteMapping("{clientId}/favorite-product/{productId}")
    public ResponseEntity<String> removeFavoriteProduct (@PathVariable Long clientId, @PathVariable Long productId){
        Client client=clientService.findById(clientId);
        Product product=productService.findById(productId);

        Boolean removed = clientService.removeFavoriteProduct(client, product);

        if(!removed){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product is not in favorites");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product removed from favorites");
    }

    @Operation(
       summary = "Endpoint responsible for getting favorite products with pagination",
       description = "Receive client id, offset, and limit as request parameters and returns a paginated list of products."
   )
    @GetMapping("{clientId}/favorite-products")
    public ResponseEntity<List<Product>> getFavoriteProducts (@PathVariable Long clientId,
        @RequestParam(defaultValue = "0") Integer offset,
        @RequestParam(defaultValue = "10") Integer limit){
        Client client=clientService.findById(clientId);

        List<Product> list = clientService.getFavoriteProducts(client,offset,limit);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Operation(
       summary = "Endpoint responsible for getting all orders with pagination",
       description = "Receive client id, offset, and limit as request parameters and returns a paginated list of orders."
   )
    @GetMapping("{clientId}/orders")
    public ResponseEntity<List<Order>> getOrders (@PathVariable Long clientId,  
        @RequestParam(defaultValue = "0") Integer offset,
        @RequestParam(defaultValue = "10") Integer limit){
        Client client=clientService.findById(clientId);

        List<Order> list = clientService.getOrders(client,offset,limit);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
