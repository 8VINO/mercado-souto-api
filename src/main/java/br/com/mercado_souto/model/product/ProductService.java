package br.com.mercado_souto.model.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.mercado_souto.util.exception.EntityNotFoundException;
import br.com.mercado_souto.util.upload.UploadImage;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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

}
