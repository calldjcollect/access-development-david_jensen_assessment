package com.access.productInventoryTracker.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.access.productInventoryTracker.dto.ProductDTO;
import com.access.productInventoryTracker.model.Product;
import com.access.productInventoryTracker.repository.ProductRepository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    // Helper method to convert Product to ProductDTO
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getCategory().toLowerCase(),
            product.isAvailable()
        );
    }
    
    // Get all products as DTOs
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Your filtering methods here...

    public List<ProductDTO> getProductsByCategory(@NotNull String category) {
        return productRepository.findAll().stream()
            .flatMap(product -> {
                if (product.getCategory().equalsIgnoreCase(category)) {
                    return Stream.of(convertToDTO(product));
                }
                return Stream.empty();
            })
            .sorted(Comparator.comparing(ProductDTO::getPrice).reversed())
            .collect(Collectors.toList());
    }
    
	public Optional<List<ProductDTO>> getProductsByPriceRange(@NotNull @PositiveOrZero Double minPrice,	@NotNull @PositiveOrZero Double maxPrice) {
		// Departure from the category method above where flatMap was used.
		List<ProductDTO> products = productRepository.findAll().stream()
			.filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
			.map(this::convertToDTO)
			.collect(Collectors.toList());
		
		return products.isEmpty() ? Optional.empty() : Optional.of(products);
	}

	public List<ProductDTO> getProductsByAvailability(boolean isAvailable) {
		return productRepository.findAll().stream()
			.filter(product -> product.isAvailable() == isAvailable)
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}
}
