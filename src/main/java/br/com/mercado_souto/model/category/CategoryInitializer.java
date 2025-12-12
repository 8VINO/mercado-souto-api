package br.com.mercado_souto.model.category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor

public class CategoryInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

   @Override
    public void run(String... args) {
        String[] basicCategories = {
            "Eletrônicos, Áudio e Vídeo",
            "Moda",
            "Casa, Móveis e Decoração",
            "Esportes e Fitness",
            "Informática",
            "Ferramentas e Construção",
            "Beleza e Cuidados Pessoais",
            "Automotivo",
            "Brinquedos e Hobbies",
            "Livros, Filmes e Música"
        };

        for (String categoryName : basicCategories) {
            createCategoryIfNotExists(categoryName);
        }
    }

    private void createCategoryIfNotExists(String categoryName) {
        try {
            if (categoryRepository.existsByName(categoryName)) {
                return;
            }

            Category category = Category.builder()
                    .name(categoryName)
                    .build();

            category.setActive(Boolean.TRUE);

            categoryRepository.save(category);

        } catch (Exception e) {
            System.err.println("Error initializing category " + categoryName + ": " + e.getMessage());
        }
    }
}
