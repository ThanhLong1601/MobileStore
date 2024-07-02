package com.ex.mobilestore.service.impl;

import com.ex.mobilestore.dto.ProductDetailDto;
import com.ex.mobilestore.dto.ProductDto;
import com.ex.mobilestore.entity.CategoryEntity;
import com.ex.mobilestore.entity.ManufactureEntity;
import com.ex.mobilestore.entity.ProductEntity;
import com.ex.mobilestore.exception.NotFoundException;
import com.ex.mobilestore.mapper.ProductMapper;
import com.ex.mobilestore.repository.CategoryRepository;
import com.ex.mobilestore.repository.ManufactureRepository;
import com.ex.mobilestore.repository.ProductRepository;
import com.ex.mobilestore.service.CloudinaryService;
import com.ex.mobilestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;
    private final ManufactureRepository manufactureRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CloudinaryService cloudinaryService, ManufactureRepository manufactureRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.cloudinaryService = cloudinaryService;
        this.manufactureRepository = manufactureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductDTO)
                .toList();
    }
    @Override
    public ProductDetailDto getProductById(Integer id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        ProductDto productDto = productMapper.toProductDTO(productEntity);
        return new ProductDetailDto(productEntity.getId(),productDto);
    }


    @Override
    public ProductDto addProduct(ProductDto product){

        ManufactureEntity manufacture = manufactureRepository.findById(product.getManufacture().getId())
                .orElseThrow(() -> new NotFoundException(product.getManufacture().getId()));
        CategoryEntity category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new NotFoundException(product.getCategory().getId()));

        if (!manufacture.getId().equals(category.getManufacture().getId())){
            throw new IllegalArgumentException("Category does not belong to selected manufacture");
        }

        if (product.getImageFile() != null & !product.getImageFile().isEmpty()){
            String imageUrl = null;
            try {
                imageUrl = cloudinaryService.uploadFile(product.getImageFile());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            product.setProductImage(imageUrl);
        }
        ProductEntity productEntity = productMapper.toProductEntity(product);
        ProductEntity createdProduct = productRepository.save(productEntity);
        return productMapper.toProductDTO(createdProduct);
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto product){
        return productRepository.findById(id)
                .map(existingProduct -> {
                    if (product.getImageFile() != null & !product.getImageFile().isEmpty()){
                        String imageUrl = null;
                        try {
                            imageUrl = cloudinaryService.uploadFile(product.getImageFile());
                        } catch (IOException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                        product.setProductImage(imageUrl);
                    }
                    ProductEntity productEntity = productMapper.toProductEntity(product);
                    productEntity.setId(id);
                    ProductEntity updateProduct = productRepository.save(productEntity);
                    return productMapper.toProductDTO(updateProduct);
                }).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> findAllByPrice(BigDecimal price, Pageable pageable) {
        Page<ProductEntity> productpage = productRepository.findAllByPrice(price, pageable);
        return productpage.map(productMapper::toProductDTO);
    }

    @Override
    public Page<ProductDto> findAllByCategoryName(String categoryName, Pageable pageable) {
        Page<ProductEntity> productpage = productRepository.findAllByCategoryName(categoryName, pageable);
        return productpage.map(productMapper::toProductDTO);
    }
}
