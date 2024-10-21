package com.example.store.service.impl;

import com.example.store.dto.request.ProductsRequestDTO;
import com.example.store.dto.response.Product_VariantsResponseDTO;
import com.example.store.dto.response.ProductsResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.Products;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.ProductsRepository;
import com.example.store.service.ProductService;
import com.example.store.service.Product_VariantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;
    private final Product_VariantService product_VariantService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductsRepository productsRepository, Product_VariantService productVariantService, ModelMapper modelMapper) {
        this.productsRepository = productsRepository;
        product_VariantService = productVariantService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceResponseDTO<List<ProductsResponseDTO>> getAll() {
        try {
            List<Products> products = productsRepository.findAll();
            List<ProductsResponseDTO> productsDTOList = new ArrayList<>();
            for (Products product: products) {
                ProductsResponseDTO productsDTO = new ProductsResponseDTO(product);
                productsDTOList.add(productsDTO);
            }
            return ServiceResponseDTO.success(HttpStatus.OK, productsDTOList);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Override
    public ServiceResponseDTO<PageImpl<ProductsResponseDTO>> getPage(Pageable pageable) {
        Page<Products> page = productsRepository.findAll(pageable);
        List<ProductsResponseDTO> productsDTOList = new ArrayList<>();
        for (Products product: page.getContent()) {
            ProductsResponseDTO productsDTO = new ProductsResponseDTO(product);
            productsDTOList.add(productsDTO);
        }
        return ServiceResponseDTO.success(HttpStatus.OK, new PageImpl<>(productsDTOList, pageable, page.getTotalElements()));
    }

    @Override
    @Transactional
    public ServiceResponseDTO<ProductsResponseDTO> create(ProductsRequestDTO productsRequestDTO) {
        try {
            Products product = modelMapper.map(productsRequestDTO, Products.class);
            Products savedProduct = productsRepository.save(product);
            List<Product_VariantsResponseDTO> savedVariants = product_VariantService.createAll(savedProduct, productsRequestDTO.getProductVariants());
            ProductsResponseDTO productsResponseDTO = new ProductsResponseDTO(savedProduct);
            Set<Product_VariantsResponseDTO> setProduct_VariantsResponseDTO = new LinkedHashSet<>(savedVariants);
            productsResponseDTO.setProductVariantsResponseDTOs(setProduct_VariantsResponseDTO);
            return ServiceResponseDTO.success(HttpStatus.OK, productsResponseDTO);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ServiceResponseDTO<ProductsResponseDTO> update(ProductsRequestDTO productsRequestDTO) {
        try {
            Products oldProducts = productsRepository.findById(productsRequestDTO.getId()).orElseThrow(() -> new ApiRequestException("product not found"));
            Products newProduct = modelMapper.map(productsRequestDTO, Products.class);
            boolean updatePriceVariant = false;
            double changePrice = 0;
            if(!oldProducts.getPrice().equals(newProduct.getPrice())){
                updatePriceVariant = true;
                changePrice = productsRequestDTO.getPrice() - oldProducts.getPrice();
            }
            Products savedProduct = productsRepository.save(newProduct);
            ProductsResponseDTO productsResponseDTO = new ProductsResponseDTO(savedProduct);
            if(updatePriceVariant){
                Set<Product_VariantsResponseDTO> setVariants = new LinkedHashSet<>(product_VariantService.updatePriceProductBase(oldProducts.getId(), changePrice));
                productsResponseDTO.setProductVariantsResponseDTOs(setVariants);
            }
            return ServiceResponseDTO.success(HttpStatus.OK, productsResponseDTO);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Override
    public ServiceResponseDTO<ProductsResponseDTO> findByID(UUID productID) {
        Products products = productsRepository.findById(productID).orElseThrow(() -> new ApiRequestException("Product not found"));
        ProductsResponseDTO productsResponseDTO = new ProductsResponseDTO(products);
        return ServiceResponseDTO.success(HttpStatus.OK, productsResponseDTO);
    }

    @Override
    public ServiceResponseDTO<ProductsResponseDTO> deletes(List<UUID> ids) {
        List<Products> products = productsRepository.findAllById(ids);
        productsRepository.deleteAll(products);
        return ServiceResponseDTO.success(HttpStatus.OK, null);
    }

    @Override
    public Boolean updateQuantity(UUID productID, Integer quantity, Boolean isAdd) {
        Products product = productsRepository.findById(productID).orElseThrow(() -> new ApiRequestException("Product not found"));
        product.setQuantity(product.getQuantity() != null? product.getQuantity() + (isAdd? quantity : (quantity * -1)) : quantity);
        productsRepository.save(product);
        return true;
    }

}
