package com.access.productInventoryTracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.access.productInventoryTracker.dto.ProductDTO;
import com.access.productInventoryTracker.model.Product;
import com.access.productInventoryTracker.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    
    private int repositorySize = 0; // Useful for test cases where knowing the repository size is helpful.
    private Product highestPriceProduct = null; // Helps make the actual hard-coded values less important.
    private long numberOfProductsHavingAvailableTrue = 0; // Helper variable.
    
    // Note: we might want to consider using "@BeforeEach" since almost all test cases use this.
    public void setupMockProducts() {
        List<Product> mockProducts = Arrays.asList(
            new Product(1L, "Laptop", 1500.0, "Electronics", true),
            new Product(2L, "Smartphone", 800.0, "Electronics", false),
            new Product(3L, "Coffee Maker", 100.0, "Home Appliances", true),
            new Product(4L, "Blender", 150.0, "Home Appliances", true),
            new Product(5L, "T-Shirt", 30.0, "Apparel", true),
            new Product(6L, "Jeans", 45.0, "Apparel", true),
            new Product(7L, "Desk Lamp", 89.99, "Home Appliances", false),
            new Product(8L, "Wall Art", 120.0, "Home Decor", true),
            new Product(9L, "Sneakers", 75.0, "Apparel", true),
            new Product(10L, "Wristwatch", 250.0, "Accessories", false),
            new Product(11L, "Backpack", 60.0, "Accessories", true),
            new Product(12L, "Microwave Oven", 99.0, "Home Appliances", false),
            new Product(13L, "Floor Rug", 150.0, "Home Decor", true),
            new Product(14L, "Speaker", 300.0, "Electronics", true),
            new Product(15L, "E-reader", 200.0, "Electronics", false),
            new Product(16L, "Gaming Console", 499.99, "Electronics", true),
            new Product(17L, "Office Chair", 220.0, "Office Supplies", true),
            new Product(18L, "Pen Set", 29.99, "Office Supplies", true),
            new Product(19L, "Mountain Bike", 489.0, "Outdoor", true),
            new Product(20L, "Camping Tent", 270.0, "Outdoor", false)
        );
        
        // Below are helper variables that make testing easier and less reliant upon know which random, hard-coded values a developer mocked.
        repositorySize = mockProducts.size();
        
        // Since we control the mockProducts, its unlikely the list will be empty, but just for completeness sake.
        Optional<Product> highestPricedOption = mockProducts.stream()
				 .max(Comparator.comparingDouble(Product::getPrice));
        highestPricedOption.ifPresent(p -> highestPriceProduct = p);
        
        numberOfProductsHavingAvailableTrue = mockProducts.stream()
			.filter(Product::isAvailable)
			.count();

        when(productRepository.findAll()).thenReturn(mockProducts);
    }

    // Your tests here...
    
    // Developer notes:
    // 1. If every test-case is going to use "setupMockProducts()" then it might be a good idea to use "@BeforeEach".

	@Test
	void getProductsByPriceRange_GivenKnownMaxRange() {
		// By using the known max range, we are essential returning ALL.
		setupMockProducts();
		Optional<List<ProductDTO>> outcome = productService.getProductsByPriceRange(0d, highestPriceProduct.getPrice());
		
		assertTrue(outcome.isPresent());
		assertEquals(repositorySize, outcome.get().size());
	}
	
	@Test
	void getProductsByPriceRange_GivenMinPriceGreaterThanKnownMax() {
		// E.g.: If known max-price is 10, then we send a range of: 11 - 11+.
		setupMockProducts();
		double guaranteedAboveKnownMax = highestPriceProduct.getPrice() + 1d;
		Optional<List<ProductDTO>> outcome = productService.getProductsByPriceRange(guaranteedAboveKnownMax, guaranteedAboveKnownMax + 1);

		assertTrue(outcome.isEmpty());
	}
	
	@Test
	void getProductsByPriceRange_GivenKnownValidRange() {
		setupMockProducts();
		double min = 30;
		double max = 60;
		Optional<List<ProductDTO>> outcome = productService.getProductsByPriceRange(min, max);

		assertTrue(outcome.isPresent());
		List<ProductDTO> products = outcome.get();
		assertThat(products).hasSize(3); // We know the answer is 3 based upon the "setup" function.
        assertTrue(products.stream().allMatch(p -> p.getPrice() >= min && p.getPrice() <= max));
	}
	
	@Test
	void getProductsByPriceRange_GivenNullRange() {
		setupMockProducts();
		Double min = null;
		Double max = null;
		
		assertThrows(NullPointerException.class, () -> {
			productService.getProductsByPriceRange(min, max);
		});
	}
    
	@Test
	void testGetProductsByCateory_GivenValidCategory() {
		setupMockProducts();
		List<ProductDTO> products = productService.getProductsByCategory("Apparel");

		assertThat(products).hasSize(3);
		
		for(ProductDTO product : products) {
			assertTrue(product.isAvailable(), product.getName() + " is available.");
		}
	}
	
	@Test
	void testGetProductsByCateory_GivenInvalidCategory() {
		setupMockProducts();
		List<ProductDTO> products = productService.getProductsByCategory("invalid_category");

		assertTrue(products.isEmpty());
	}
	
	@Test
	void testGetProductsByCateory_GivenValidCategoryWithMixedUpperLowerCase() {
		setupMockProducts();
		List<ProductDTO> products = productService.getProductsByCategory("apPaReL");

		assertThat(products).hasSize(3);
	}
	
	@Test
	void testGetProductsByCateory_GivenValidCategoryWithMatchingPrefix() {
		setupMockProducts();
		List<ProductDTO> products = productService.getProductsByCategory("Appare");

		assertThat(products).hasSize(0);
	}
    
	@Test
	void testGetProductsByCategory_thenOrderedByPrice() {
		setupMockProducts();
		List<ProductDTO> products = productService.getProductsByCategory("Apparel");

		// No-use comparing unless there are more than one entry.
		assumeTrue(products.size() > 1, "Precondition of at least two products not met.");
		
		// The above preconditions protects this loop from meaningful evaluation.
		for(int i = 0; i < products.size() - 1; i++) {
			double next = products.get(i + 1).getPrice();
			assertTrue(next <= products.get(i).getPrice(), "Out-of-order after: " + i);
		}
	}

	@Test
    public void testGetProductsByAvailability_GivenAvailabilityOfTrue() {
		boolean availability = true;
        List<ProductDTO> products = productService.getProductsByAvailability(availability);

        assertEquals(numberOfProductsHavingAvailableTrue, products.size());
        assertTrue(products.stream().allMatch(ProductDTO::isAvailable));
    }

	@Test
    public void testGetProductsByAvailability_GivenAvailabilityOfFalse() {
		boolean availability = false;
        List<ProductDTO> products = productService.getProductsByAvailability(availability);

        assertEquals(numberOfProductsHavingAvailableTrue, products.size());
        assertTrue(products.stream().noneMatch(ProductDTO::isAvailable));
    }
    
	@Test
	void testGetAllProducts_GivenProductsExist() {
		setupMockProducts(); // This should ensure at least one product exists.
		List<ProductDTO> products = productService.getAllProducts();

		assertThat(products).hasSizeGreaterThan(0);
		assertThat(products).hasSize(repositorySize);
	}
    
	@Test
	void testGetAllProducts_GivenProductsDontExist() {
		// Don't call the "setup" method to ensure no products exist.
		List<ProductDTO> products = productService.getAllProducts();

		assertThat(products).isEmpty();
	}
}