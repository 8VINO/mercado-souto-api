package br.com.mercado_souto.api.category;

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

import br.com.mercado_souto.model.category.Category;
import br.com.mercado_souto.model.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
@Tag(
    name = "Category API",
    description = "API responsible for managing categories in the system"
)
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    
     
     @Operation(
       summary = "Endpoint responsible for creating a category",
       description = "Receives the category data in the request body, creates and return the category."
   )
    @PostMapping
    public ResponseEntity<Category> create (@RequestBody  CategoryRequest request){
        Category category = categoryService.create (request.build());
        return  ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
      @Operation(
       summary = "Endpoint responsible for getting all categories",
       description = "Returns a list of categories."
   )
    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> listacategory = categoryService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(listacategory);
    }
    
       @Operation(
       summary = "Endpoint responsible for getting a specific category",
       description = "Receives the category id and returns the specific category."
   )
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Category category = categoryService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(category);
        
    }

     @Operation(
       summary = "Endpoint responsible for updating the category",
       description = "Receives the category id and returns the updated category."
   )
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody CategoryRequest request){
        Category category = categoryService.update(id,request.build());

        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

      @Operation(
       summary = "Endpoint responsible for deleting the category",
       description = "Receives the category id, deletes the category and returns status 204."
   )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        categoryService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
