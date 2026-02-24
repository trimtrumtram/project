package com.crudapi.crud.controller;

import com.crudapi.crud.dto.product.CreateProductDTO;
import com.crudapi.crud.dto.product.ProductFilterDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.dto.product.UpdateProductDTO;
import com.crudapi.crud.enums.sort.ProductSortField;
import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.mapper.filterMapper.ProductFilterMapper;
import com.crudapi.crud.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductFilterMapper productFilterMapper;
    private ProductResponseDTO productDto;

    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Creating a new product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully"),
                    @ApiResponse(responseCode = "400", description = "Product is not created")
            }
    )
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid CreateProductDTO dto) {
        productDto = productService.createProduct(dto);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing product",
            description = "Updating an existing product",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Product is not updated")
            }
    )
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductDTO dto) {
        productDto = productService.updateProduct(id, dto);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an existing product",
            description = "Deleting an existing product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Product is not deleted")
            }
    )
    public boolean deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return true;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a product by id",
            description = "Getting a product by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product found successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        productDto = productService.findById(id);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Getting all products",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Products found successfully"),
                    @ApiResponse(responseCode = "404", description = "Products not found")
            }
    )
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) BigDecimal startPriceFilter,
            @RequestParam(required = false) BigDecimal endPriceFilter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ID") ProductSortField sortBy,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection
    ) {
        ProductFilterDTO filter = productFilterMapper.toDTO(
                nameFilter,
                startPriceFilter,
                endPriceFilter,
                page,
                size,
                sortBy,
                sortDirection
        );
        Page<ProductResponseDTO> products = productService.getAllProducts(filter);
        return ResponseEntity.ok(products);
    }
}
