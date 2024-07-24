package com.ex.mobilestore.controller;

import com.ex.mobilestore.constant.AppConstant;
import com.ex.mobilestore.dto.ProductDetailDto;
import com.ex.mobilestore.dto.ProductDto;
import com.ex.mobilestore.dto.common.BaseResponse;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.service.ProductService;
import com.ex.mobilestore.utils.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService ) {
        this.productService = productService;
    }

    @GetMapping("/listAll")
    public ResponseEntity<BaseResponse<List<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ){
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        Page<ProductDto> productsPage = productService.getAllProducts(pageable);
        return ResponseFactory.ok(productsPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductDetailDto>> getProductById(@PathVariable Integer id){
        try {
            ProductDetailDto getProductById = productService.getProductById(id);
            return ResponseFactory.ok(getProductById);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<ProductDto>> addProduct(@Valid @ModelAttribute ProductDto productDto){
        try {
            ProductDto addProduct = productService.addProduct(productDto);
            return ResponseFactory.ok(addProduct);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductDto>> updateProduct(@PathVariable Integer id, @Valid @ModelAttribute ProductDto productDto){
        try {
            ProductDto updateProduct = productService.updateProduct(id, productDto);
            return ResponseFactory.ok(updateProduct);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteProduct(@PathVariable Integer id){
        try {
            productService.deleteProduct(id);
            return ResponseFactory.ok(null);
        }catch (NotFoundException e){
            return ResponseFactory.error(null, AppConstant.ERROR_CODE,e.getMessage());
        }
    }

    @GetMapping("/by-price")
    public ResponseEntity<BaseResponse<List<ProductDto>>> findAllByPrice(
            @RequestParam BigDecimal price,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "price,asc") String[] sort
    )
    {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        Page<ProductDto> productsPage = productService.findAllByPrice(price, pageable);
        return ResponseFactory.ok(productsPage.getContent());
    }

    @GetMapping("/by-category")
    public ResponseEntity<BaseResponse<List<ProductDto>>> findAllByCategory(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "price,asc") String[] sort
    ){
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        Page<ProductDto> productsPage = productService.findAllByCategoryName(categoryName, pageable);
        return ResponseFactory.ok(productsPage.getContent());
    }
}
