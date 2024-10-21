package com.example.store.service.impl;

import com.example.store.dto.request.Product_VariantRequestDTO;
import com.example.store.dto.response.Product_VariantsResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.ImportTicket_Product;
import com.example.store.entity.Order_Product;
import com.example.store.entity.Product_Variants;
import com.example.store.entity.Products;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.Product_VariantRepository;
import com.example.store.service.Price_HistoryService;
import com.example.store.service.Product_VariantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Product_VariantServiceImpl implements Product_VariantService {
    private final Product_VariantRepository product_VariantRepository;
    private final Price_HistoryService price_HistoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public Product_VariantServiceImpl(Product_VariantRepository productVariantRepository, Price_HistoryService priceHistoryService,  ModelMapper modelMapper) {
        this.product_VariantRepository = productVariantRepository;
        price_HistoryService = priceHistoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Product_VariantsResponseDTO> createAll(Products products, List<Product_VariantRequestDTO> product_VariantRequestDTOs) {
        try {
            List<Product_Variants> product_Variants = product_VariantRequestDTOs.stream().map(variant -> modelMapper.map(variant, Product_Variants.class)).toList();
            for (Product_Variants variant : product_Variants) {
                variant.setCreateDate(LocalDateTime.now());
                variant.setProduct(products);
            }
            List<Product_Variants> savedProduct_Variants = product_VariantRepository.saveAll(product_Variants);
            List<Product_VariantsResponseDTO> result = new ArrayList<>();
            for (Product_Variants variant : savedProduct_Variants) {
                variant.setProduct(null);
                Product_VariantsResponseDTO dto = new Product_VariantsResponseDTO(variant);
                result.add(dto);
            }
            return result;
        }catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ServiceResponseDTO<Product_VariantsResponseDTO> update(Product_VariantRequestDTO product_VariantRequestDTO) {
        Product_Variants product_Variant = product_VariantRepository.findById(product_VariantRequestDTO.getId())
                .orElseThrow(() -> new ApiRequestException("Product variant not found"));
        if(checkDuplicateSizeAndColor(product_Variant, product_VariantRequestDTO))
            throw new ApiRequestException("Product variant already exists");
        Double oldPrice = null;
        if(!product_Variant.getPrice().equals(product_VariantRequestDTO.getPrice())){
            oldPrice = product_Variant.getPrice();
        }
        product_Variant = modelMapper.map(product_VariantRequestDTO, Product_Variants.class);
        product_VariantRepository.save(product_Variant);
        if(oldPrice != null)
            price_HistoryService.create(product_Variant, oldPrice, product_Variant.getPrice());
        Product_VariantsResponseDTO result = new Product_VariantsResponseDTO(product_Variant);
        return ServiceResponseDTO.success(HttpStatus.OK, result);
    }

    @Override
    public Boolean delete(List<UUID> product_VariantIds) {
        try {
            List<Product_Variants> productVariants = product_VariantRepository.findAllById(product_VariantIds);
            product_VariantRepository.deleteAll(productVariants);
            return true;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Override
    public ServiceResponseDTO<Product_VariantsResponseDTO> findById(UUID productID) {
        Product_Variants productVariant = product_VariantRepository.findById(productID).orElseThrow(() -> new ApiRequestException("Product variant not found"));
        Product_VariantsResponseDTO result = new Product_VariantsResponseDTO(productVariant);
        return ServiceResponseDTO.success(HttpStatus.OK, result);
    }

    @Override
    public ServiceResponseDTO<List<Product_VariantsResponseDTO>> findAll() {
        List<Product_Variants> product_Variants = product_VariantRepository.findAll();
        return ServiceResponseDTO.success(HttpStatus.OK, convertProductVariantsToProductVariantsResponseDTO(product_Variants));
    }

    @Override
    public ServiceResponseDTO<List<Product_VariantsResponseDTO>> findAllByProduct_Id(UUID productID) {
        List<Product_Variants> product_Variants = product_VariantRepository.findByProduct_Id(productID);
        return ServiceResponseDTO.success(HttpStatus.OK, convertProductVariantsToProductVariantsResponseDTO(product_Variants));
    }

    @Override
    @Transactional
    public List<Product_VariantsResponseDTO> updatePriceProductBase(UUID productID, double changePrice) {
        try {
            List<Product_Variants> productVariants = product_VariantRepository.findByProduct_Id(productID);
            List<Double> oldPrices = new ArrayList<>();
            for(Product_Variants productVariant : productVariants){
                // get oldPrice
                oldPrices.add(productVariant.getPrice());
                // get newPrice (price variant + price change ( newPrice base product - oldPrice base product))
                Double completePrice = productVariant.getPrice() + changePrice;
                productVariant.setPrice(completePrice);
                productVariant.setUpdateDate(LocalDateTime.now());
            }
            product_VariantRepository.saveAll(productVariants);
            for (int i = 0; i < productVariants.size(); i++) {
                // create price history in variant by product
                price_HistoryService.create(productVariants.get(i), oldPrices.get(i), productVariants.get(i).getPrice());
            }
            List<Product_VariantsResponseDTO> result = new ArrayList<>();
            productVariants.forEach(v -> {
                v.setProduct(null);
                result.add(new Product_VariantsResponseDTO(v));
            });
            return result;
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> addQuantity(List<ImportTicket_Product> importTicketProducts, Set<Order_Product> orderProducts) {
        try {
            Map<UUID, Integer> variantQuantityMap = new HashMap<>();
            if(importTicketProducts != null){
                importTicketProducts.forEach(p -> variantQuantityMap.put(p.getId().getVariantID(), p.getQuantity()));
            }else {
                orderProducts.forEach(p -> variantQuantityMap.put(p.getId().getVariantID(), p.getQuantity()));
            }
            Map<String, Object> map = updateQuantity(variantQuantityMap, true);
            @SuppressWarnings("unchecked") List<Product_Variants> productVariants = (List<Product_Variants>) map.get("productVariants");
            Integer totalQuantity = (Integer) map.get("totalQuantity");
            product_VariantRepository.saveAll(productVariants);
            return returnTotalQuantity(productVariants.get(0).getProduct().getId(), totalQuantity);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> sellQuantity(Set<Order_Product> orderProducts) {
        Map<UUID, Integer> productQuantityMap = new HashMap<>();
        orderProducts.forEach(o -> productQuantityMap.put(o.getId().getVariantID(), o.getQuantity()));
        Map<String, Object> map = updateQuantity(productQuantityMap, false);
        @SuppressWarnings("unchecked") List<Product_Variants> productVariants = (List<Product_Variants>) map.get("productVariants");
        Integer totalQuantity = (Integer) map.get("totalQuantity");
        product_VariantRepository.saveAll(productVariants);
        return returnTotalQuantity(productVariants.get(0).getProduct().getId(), totalQuantity);
    }

    private List<Product_VariantsResponseDTO> convertProductVariantsToProductVariantsResponseDTO(List<Product_Variants> productVariants) {
        List<Product_VariantsResponseDTO> productVariantsResponseDTOS = new ArrayList<>();
        for(Product_Variants productVariant : productVariants){
            productVariantsResponseDTOS.add(new Product_VariantsResponseDTO(productVariant));
        }
        return productVariantsResponseDTOS;
    }


    private  Map<String, Object> updateQuantity(Map<UUID, Integer>variantQuantity, Boolean addQuantity){
        Map<String, Object> result = new HashMap<>();
        List<Product_Variants> productVariants = new ArrayList<>();
        AtomicInteger totalQuantity = new AtomicInteger(0);
        variantQuantity.forEach((id, quantity) -> {
            Product_Variants variants = product_VariantRepository.findById(id).orElseThrow(() -> new ApiRequestException("Variant not found"));
            if (!addQuantity && variants.getQuantity() < quantity) {
                throw new ApiRequestException("Quantity cannot be less than zero.");
            }
            if(addQuantity) {
                variants.setQuantity(variants.getQuantity()!=null? variants.getQuantity() + quantity : quantity);
            }else {
                int calculateQuantity = variants.getQuantity() - quantity;
                if(calculateQuantity < 0)
                    throw new ApiRequestException("Out of stock.");
                variants.setQuantity(calculateQuantity);
            }
            totalQuantity.addAndGet(quantity);
            productVariants.add(variants);
        });
        Integer intTotalQuantity = totalQuantity.get();
        result.put("totalQuantity", intTotalQuantity);
        result.put("productVariants", productVariants);
        return result;
    }

    private Boolean checkDuplicateSizeAndColor(Product_Variants productVariant, Product_VariantRequestDTO productVariantRequestDTO){
        return productVariant.getSize().equalsIgnoreCase(productVariantRequestDTO.getSize())
                && productVariant.getColor().equalsIgnoreCase(productVariantRequestDTO.getColor());
    }

    private Map<String, Object> returnTotalQuantity(UUID productID, Integer totalQuantity){
        Map<String, Object> result = new HashMap<>();
        result.put("totalQuantity", totalQuantity);
        result.put("productID", productID);
        return result;
    }
}
