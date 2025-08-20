package com.access.productInventoryTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.access.productInventoryTracker.model.Product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom methods here if needed, for example: 
    // List<Product> findByCategory(String category);

    // AI Generated
    @Query(value = "SELECT * FROM product p " +
					"WHERE p.category = :category " +
					"AND p.available = true " +
					"ORDER BY p.price DESC",
           nativeQuery = true)
    List<Product> findProductsByCategory(@Param("category") @NotNull String category);

	@Query(value = "SELECT * FROM product p " +
					"WHERE p.price BETWEEN :minPrice AND :maxPrice",
			nativeQuery = true)
	List<Product> findProductsByPriceRange(@Param("minPrice") @NotNull @PositiveOrZero Double minPrice, @Param("maxPrice") @NotNull @PositiveOrZero Double maxPrice);
    
	@Query(value = "SELECT * FROM product p " +
					"WHERE p.available = :available", 
			nativeQuery = true)
	List<Product> findProductsByAvailability(@Param("available") boolean available);

}
