package br.com.mercado_souto.api.product;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.mercado_souto.model.category.CategoryService;
import br.com.mercado_souto.model.product.Product;
import br.com.mercado_souto.model.product.ProductService;
import br.com.mercado_souto.model.seller.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/product")
@CrossOrigin

@Tag(
    name = "Product API ",
    description = "API responsible for managing products in the system"
)

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SellerService sellerService;
    
   @Operation(
       summary = "Endpoint responsible for creating a product",
       description = "Receives the seller id of the seller who wants to register the product, along with the product data in the request body."
   )

    @PostMapping("/{idSeller}")
    public ResponseEntity<Product> create(@PathVariable Long idSeller,@RequestBody ProductRequest request) {

        Product newProduct = request.build();
        newProduct.setSeller(sellerService.findById(idSeller));
        newProduct.setCategory(categoryService.findById(request.getIdCategory()));
        Product product = productService.create(newProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(
       summary = "Endpoint responsible for getting all products",
       description = "Returns a list of products from ALL sellers."
   )
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> list = productService.findAll();
        return ResponseEntity.status( HttpStatus.OK).body(list);
    }

     @Operation(
       summary = "Endpoint responsible for getting all products from a SINGLE seller",
       description = "Receives the seller id and returns a list of products."
   )
   

    @GetMapping("/by-seller/{idSeller}")
    public ResponseEntity<List<Product>> findBySeller(@PathVariable Long idSeller){

        sellerService.findById(idSeller);//garantir q o seller existe
        List<Product> list = productService.findBySeller(idSeller);

        return ResponseEntity.status( HttpStatus.OK).body(list);
    }
     @Operation(
       summary = "Endpoint responsible for getting a specific product",
       description = "Receives the product id and returns the specific product."
   )
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.status( HttpStatus.OK).body(product);
    }

      @Operation(
       summary = "Endpoint responsible for updating the product",
       description = "Receives the product id and returns the updated product."
   )
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductRequest request) {

        Product modifiedProduct = request.build();
        modifiedProduct.setCategory(categoryService.findById(request.getIdCategory()));
        Product product = productService.update(id, modifiedProduct);

        return ResponseEntity.status( HttpStatus.OK).body(product);
    }
    
       @Operation(
       summary = "Endpoint responsible for deleting the product",
       description = "Receives the product id, deletes the product and returns status 204."
   )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status( HttpStatus.NO_CONTENT).build();
    }

   @Operation(
    summary = "Add an image to a product",
    description = "Receives the product id in the URL path and an image in the request body (multipart/form-data) under the field name 'image'. "
    + "The endpoint saves the image for the specified product and returns the updated product with HTTP status 201."
)
    @PostMapping("/image/{id}")
    public ResponseEntity<Product> saveImage(@PathVariable Long id, @RequestParam(value = "image", required = true) MultipartFile image) {

        Product product = productService.saveImage(id, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}
