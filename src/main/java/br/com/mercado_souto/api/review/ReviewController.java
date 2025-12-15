package br.com.mercado_souto.api.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercado_souto.model.client.Client;
import br.com.mercado_souto.model.client.ClientService;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;
import br.com.mercado_souto.model.review.Review;
import br.com.mercado_souto.model.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;
    
    @Operation(
       summary = "Endpoint responsible for creating a review for a product by a client.",
       description = "Receives the client id and product id in the path variables, and the review data in the request body, creates and returns the review.")
    @PostMapping("/client/{clientId}/product/{productId}")
    public ResponseEntity<Review> create(@PathVariable Long clientId, @PathVariable Long productId, @RequestBody @Valid ReviewRequest request) {
        
        Client client = clientService.findById(clientId);
        Product product = productService.findById(productId);
        Review review = reviewService.create(client, product, request.getRating(), request.getComment());

        return ResponseEntity.status(HttpStatus.CREATED).body((review));
    }

    @Operation(
       summary = "Endpoint responsible for retrieving all reviews for a specific product.",
       description = "Receives the product id in the path variable and returns a list of reviews for that product.")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable Long productId) {
      
        List<Review> reviews = reviewService.findReviewsByProductId(productId);
        
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }


}
