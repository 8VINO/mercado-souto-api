package br.com.mercado_souto.model.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.mercado_souto.model.review.ReviewRepository;
import br.com.mercado_souto.util.exception.EntityNotFoundException;
import br.com.mercado_souto.util.upload.UploadImage;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public Product create(Product product) {
        product.setActive(Boolean.TRUE);
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();

    }

    public Product findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));

        return product;
    }

    public List<Product> findBySeller(Long idSeller) {

        return productRepository.findBySellerId(idSeller);
    }

    @Transactional
    public Product update(Long id, Product modifiedProduct) {
        Product product = findById(id);

        product.setTitle(modifiedProduct.getTitle());
        product.setSpecification(modifiedProduct.getSpecification());
        product.setDescription(modifiedProduct.getDescription());
        product.setPrice(modifiedProduct.getPrice());
        product.setStock(modifiedProduct.getStock());
        product.setCategory(modifiedProduct.getCategory());
        product.setImageURL(modifiedProduct.getImageURL());

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = findById(id);
        product.setActive(Boolean.FALSE);

        productRepository.save(product);
    }

    @Transactional
    public Product saveImage(Long id, MultipartFile image) {

        Product product = findById(id);

        String savedImage = UploadImage.upload(image);

        if (savedImage != null) {
            product.getImageURL().add(savedImage);
        }

        return productRepository.save(product);
    }

    public List<Product> findProductByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> search(String title, Sort sort) {
        return productRepository.findByTitleContainingIgnoreCase(title, sort);
    }

    @Transactional
    public void decrementStock(Long productId, Integer quantity) {
        Product product = findById(productId);
        int currentStock = product.getStock();
        
        if (currentStock < quantity) {
            throw new RuntimeException("Insufficient stock for product " + product.getTitle() + ". Current stock: " + currentStock );
        }
        
        product.setStock(currentStock - quantity);
        
        productRepository.save(product); 
    }

    @Transactional
    public void updateProductRating(Product product) {
        
    Product productToUpdate = findById(product.getId()); 
    
    Optional<Double> avgRatingOptional = reviewRepository.findAverageRatingByProduct(product);
    
    long totalReviews = reviewRepository.countByProduct(product);
    if (avgRatingOptional.isPresent()) {
        Double avg = avgRatingOptional.get();
        BigDecimal averageRating = BigDecimal.valueOf(avg)
                                            .setScale(2, RoundingMode.HALF_UP);
        
        productToUpdate.setAverageRating(averageRating);
    } else {
        productToUpdate.setAverageRating(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)); 
    }

    productToUpdate.setTotalReviews((int) totalReviews); 
    productRepository.save(productToUpdate);
    }
}
