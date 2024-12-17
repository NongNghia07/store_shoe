package com.example.store.service.impl;

import com.example.store.dto.request.CategoriesRequestDTO;
import com.example.store.dto.request.MtTranslationsRequestDTO;
import com.example.store.dto.request.Product_VariantRequestDTO;
import com.example.store.dto.request.ProductsRequestDTO;
import com.example.store.dto.response.Product_VariantsResponseDTO;
import com.example.store.dto.response.ProductsResponseDTO;
import com.example.store.dto.response.util.ServiceResponseDTO;
import com.example.store.entity.MetadataTranslations;
import com.example.store.entity.Product_Variants;
import com.example.store.entity.Products;
import com.example.store.enums.ErrorStatus;
import com.example.store.exception.ApiRequestException;
import com.example.store.repository.ProductsRepository;
import com.example.store.service.MtTranslationsService;
import com.example.store.service.ProductService;
import com.example.store.service.Product_VariantService;
import com.example.store.util.ExcelReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;
    private final Product_VariantService product_VariantService;
    private final MtTranslationsService translationsService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductsRepository productsRepository, Product_VariantService productVariantService, MtTranslationsService translationsService, ModelMapper modelMapper) {
        this.productsRepository = productsRepository;
        product_VariantService = productVariantService;
        this.translationsService = translationsService;
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
            return ServiceResponseDTO.success(HttpStatus.OK,"", productsDTOList);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), ErrorStatus.BAD_REQUEST_400);
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
        return ServiceResponseDTO.success(HttpStatus.OK, "null", new PageImpl<>(productsDTOList, pageable, page.getTotalElements()));
    }

    @Override
    @Transactional
    public ServiceResponseDTO<List<ProductsResponseDTO>> createToExcel(InputStream is) {
        try {
            // Đọc dữ liệu từ file Excel
            Object[][] excelData = ExcelReader.readExcel(is, 0);

            // Khởi tạo các map để lưu trữ dữ liệu sản phẩm và biến thể
            Map<String, ProductsRequestDTO> productMap = new HashMap<>();
            Map<String, Map<String, Product_VariantRequestDTO>> variantMap = new HashMap<>();
            Map<String, List<MtTranslationsRequestDTO>> metadataTranslationProduct = new HashMap<>();
            Map<String, List<MtTranslationsRequestDTO>> metadataTranslationVariant = new HashMap<>();

            // Duyệt qua các dòng dữ liệu trong excel
            for (int i = 1; i < excelData.length; i++) {
                // Lấy dữ liệu từng dòng từ file Excel
                String productId = getStringValue(excelData[i][0]);
                Integer quantity = getIntegerValue(excelData[i][1]);
                Double price = getDoubleValue(excelData[i][2]);
                UUID categoryId = getUUIDValue(excelData[i][3]);
                String variantProductId = getStringValue(excelData[i][4]);
                Integer variantQuantity = getIntegerValue(excelData[i][5]);
                Double variantPrice = getDoubleValue(excelData[i][6]);
                String variantSize = getStringValue(excelData[i][7]);
                String tableName = getStringValue(excelData[i][8]);
                String columnName = getStringValue(excelData[i][9]);
                String languageCode = getStringValue(excelData[i][10]);
                String translation = getStringValue(excelData[i][11]);

                // Xử lý dữ liệu cho sản phẩm hoặc variant dựa trên bảng
                if ("product".equals(tableName)) {
                    processProductData(productId, quantity, price, categoryId, metadataTranslationProduct, tableName, columnName, languageCode, translation, productMap);
                } else if ("variant_product".equals(tableName)) {
                    processVariantData(productId, variantProductId, variantQuantity, variantPrice, variantSize, metadataTranslationVariant, tableName, columnName, languageCode, translation, variantMap);
                }
            }

            // Lưu sản phẩm vào database và lấy danh sách các sản phẩm đã lưu
            List<Products> savedProducts = saveProducts(productMap, metadataTranslationProduct);

            // Lưu variants và cập nhật ID sản phẩm trong variants
            List<Product_Variants> savedVariants = saveVariants(productMap, variantMap, metadataTranslationVariant, savedProducts);

            // Lưu translations cho cả sản phẩm và variant
            saveTranslations(variantMap, metadataTranslationProduct, metadataTranslationVariant, savedProducts, savedVariants);

            // Trả về kết quả thành công với danh sách sản phẩm đã xử lý
            List<ProductsResponseDTO> result = savedProducts.stream()
                    .map(ProductsResponseDTO::new)
                    .collect(Collectors.toList());

            return ServiceResponseDTO.success(HttpStatus.OK, "message.product.create.success", result);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Error processing Excel data", ErrorStatus.BAD_REQUEST_400);
        }
    }

    private List<Products> saveProducts(Map<String, ProductsRequestDTO> productMap, Map<String, List<MtTranslationsRequestDTO>> metadataTranslationProduct) {
        // Lọc và chỉ lưu các sản phẩm có tên
        List<Products> productsToSave = productMap.entrySet().stream()
                .filter(entry -> metadataTranslationProduct.containsKey(entry.getKey()) &&
                        metadataTranslationProduct.get(entry.getKey()).stream()
                                .anyMatch(trans -> "name".equals(trans.getColumnName())))
                .map(entry -> modelMapper.map(entry.getValue(), Products.class))
                .collect(Collectors.toList());

        return productsRepository.saveAll(productsToSave);
    }

    private List<Product_Variants> saveVariants(Map<String, ProductsRequestDTO> productMap,
                                                Map<String, Map<String, Product_VariantRequestDTO>> variantMap,
                                                Map<String, List<MtTranslationsRequestDTO>> metadataTranslationVariant,
                                                List<Products> savedProducts) {
        List<Product_Variants> variants = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, ProductsRequestDTO> entry : productMap.entrySet()) {
            Products savedProduct = savedProducts.get(i); // Lấy product đã được lưu
            ProductsRequestDTO dto = entry.getValue();
            dto.setId(savedProduct.getId());
            entry.setValue(dto);

            Map<String, Product_VariantRequestDTO> variantDTOMap = variantMap.get(entry.getKey());

            if (variantDTOMap != null) {
                for (Map.Entry<String, Product_VariantRequestDTO> entryVariant : variantDTOMap.entrySet()) {
                    List<MtTranslationsRequestDTO> translationList = metadataTranslationVariant.get(entry.getKey()+ "-" + entryVariant.getKey());
                    // Kiểm tra trùng `color`
                    if (translationList.stream().anyMatch(trans -> "color".equals(trans.getColumnName()))) {
                        Product_Variants variant = modelMapper.map(entryVariant.getValue(), Product_Variants.class);
                        variant.setProduct(savedProduct);
                        variants.add(variant);
                    }
                }
            }
            i++;
        }

        product_VariantService.createAllByExcel(variants);
        return variants;
    }

    private void saveTranslations(Map<String, Map<String, Product_VariantRequestDTO>> variantMap,
                                  Map<String, List<MtTranslationsRequestDTO>> metadataTranslationProduct,
                                  Map<String, List<MtTranslationsRequestDTO>> metadataTranslationVariant,
                                  List<Products> savedProducts, List<Product_Variants> savedVariants) {
        List<MetadataTranslations> translations = new ArrayList<>();

        // Lưu translations cho sản phẩm
        int i = 0;
        for (Map.Entry<String, List<MtTranslationsRequestDTO>> translationProduct : metadataTranslationProduct.entrySet()) {
            List<MtTranslationsRequestDTO> translationList = metadataTranslationProduct.get(translationProduct.getKey());
            if (translationList != null) {
                for (MtTranslationsRequestDTO translation :  translationList) {
                    translation.setRowId(savedProducts.get(i).getId());
                    translations.add(modelMapper.map(translation, MetadataTranslations.class));
                }

            }
        }

        // Lưu translations cho variant
        int j = 0;
        for (Map.Entry<String, Map<String, Product_VariantRequestDTO>> entry : variantMap.entrySet()) {
            for (Map.Entry<String, Product_VariantRequestDTO> variantMapDTO : entry.getValue().entrySet()) {
                List<MtTranslationsRequestDTO> translationList = metadataTranslationVariant.get(entry.getKey()+"-"+variantMapDTO.getKey());
                for (MtTranslationsRequestDTO translation : translationList) {
                    translation.setRowId(savedVariants.get(j).getId());
                    translations.add(modelMapper.map(translation, MetadataTranslations.class));
                }
                i++;
            }
        }

        translationsService.createAll(translations);
    }

    // Hàm hỗ trợ lấy giá trị từ excel
    private String getStringValue(Object obj) {
        return obj instanceof Double ? String.valueOf(((Double) obj).intValue()) : obj != null ? obj.toString() : null;
    }

    private Integer getIntegerValue(Object obj) {
        return obj instanceof Double ? ((Double) obj).intValue() : null;
    }

    private Double getDoubleValue(Object obj) {
        return obj instanceof Double ? (Double) obj : null;
    }

    private UUID getUUIDValue(Object obj) {
        return obj instanceof String ? UUID.fromString((String) obj) : null;
    }

    private void processProductData(String productId, Integer quantity, Double price, UUID categoryId,
                                    Map<String, List<MtTranslationsRequestDTO>> metadataTranslationProduct,
                                   String tableName, String columnName, String languageCode, String translation,
                                    Map<String, ProductsRequestDTO> productMap) {
        ProductsRequestDTO productDTO = productMap.computeIfAbsent(productId, k -> new ProductsRequestDTO());

        if (quantity != null) productDTO.setQuantity(quantity);
        if (price != null) productDTO.setPrice(price);

        if (productDTO.getCategory() == null && categoryId != null) {
            CategoriesRequestDTO category = new CategoriesRequestDTO();
            category.setId(categoryId);
            productDTO.setCategory(category);
        }

        // Kiểm tra trùng tên (giả sử tên sản phẩm nằm trong cột "name" trong translations) và trùng languageCode
        for (Map.Entry<String, List<MtTranslationsRequestDTO>> translations : metadataTranslationProduct.entrySet()) {
            List<MtTranslationsRequestDTO> translationList = metadataTranslationProduct.get(translations.getKey());
            if (translationList != null) {
                for (MtTranslationsRequestDTO trans : translationList) {
                    // Kiểm tra nếu cột là "name" (tên sản phẩm) và khớp languageCode
                    if ("name".equals(trans.getColumnName()) && languageCode.equals(trans.getLanguageCode())) {
                        // So sánh giá trị của translation (tên sản phẩm)
                        if (translation.equals(trans.getTranslation())) {
                            return;
                        }
                    }
                }
            }
        }

        // Thêm bản dịch nếu chưa có
        addTranslation(metadataTranslationProduct, productId, tableName, columnName, languageCode, translation);
    }

    private void processVariantData(String productId, String variantProductId, Integer variantQuantity, Double variantPrice,
                                    String variantSize, Map<String, List<MtTranslationsRequestDTO>> metadataTranslationVariant,
                                    String tableName, String columnName, String languageCode, String translation,
                                    Map<String, Map<String, Product_VariantRequestDTO>> variantMap) {
        // Trả về nếu variantProductId là null
        if (variantProductId == null) return;

        // Khởi tạo variantMap cho sản phẩm nếu chưa có
        variantMap.computeIfAbsent(productId, k -> new HashMap<>());
        Map<String, Product_VariantRequestDTO> variantList = variantMap.get(productId);

        // Khởi tạo variantProductDTO nếu chưa có và cập nhật thông tin
        Product_VariantRequestDTO variantDTO = variantList.computeIfAbsent(variantProductId, k -> new Product_VariantRequestDTO());
        if (variantQuantity != null) variantDTO.setQuantity(variantQuantity);
        if (variantPrice != null) variantDTO.setPrice(variantPrice);
        if (variantSize != null) variantDTO.setSize(variantSize);

        // Kiểm tra trùng thông tin variant và bản dịch
        for (Map.Entry<String, List<MtTranslationsRequestDTO>> translations : metadataTranslationVariant.entrySet()) {
            String[] id = translations.getKey().split("-");
            if (productId.equals(id[0])) { // Kiểm tra sản phẩm
                List<MtTranslationsRequestDTO> listTranslation = translations.getValue();
                for (MtTranslationsRequestDTO trans : listTranslation) {
                    // Kiểm tra trùng thông tin size và color
                    if ("color".equals(trans.getColumnName()) && variantSize != null) {
                        for (Product_VariantRequestDTO variant : variantList.values()) {
                            if (variantSize.equals(variant.getSize()) && trans.getTranslation().equals(translation)) {
                                return; // Nếu trùng, không cần thêm vào nữa
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, List<MtTranslationsRequestDTO>> translations : metadataTranslationVariant.entrySet()) {
            String[] id = translations.getKey().split("-");
            if(productId.equals(id[0])) {
                List<MtTranslationsRequestDTO> listTranslation = translations.getValue();
                for (MtTranslationsRequestDTO trans : listTranslation) {
                    if(variantSize != null) {
                        for (Product_VariantRequestDTO variant : variantList.values()) {
                            if(trans.getColumnName().equals("color")) {
                                if (variantSize.equals(variant.getSize()) && trans.getTranslation().equals(translation)) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        // Thêm bản dịch nếu chưa có
        addTranslation(metadataTranslationVariant, productId + "-" + variantProductId, tableName, columnName, languageCode, translation);
    }

    private void addTranslation(Map<String, List<MtTranslationsRequestDTO>> metadataTranslationMap,
                                String key, String tableName, String columnName, String languageCode, String translation) {
        List<MtTranslationsRequestDTO> translations = metadataTranslationMap.getOrDefault(key, new ArrayList<>());
        MtTranslationsRequestDTO translationDTO = new MtTranslationsRequestDTO();
        translationDTO.setTableName(tableName);
        translationDTO.setColumnName(columnName);
        translationDTO.setLanguageCode(languageCode);
        translationDTO.setTranslation(translation);
        translations.add(translationDTO);
        metadataTranslationMap.put(key, translations);
    }


//    private void addTranslation(Map<String, List<MtTranslationsRequestDTO>> metadataTranslationMap, String key,
//                                String tableName, String columnName, String languageCode, String translation) {
//        metadataTranslationMap.computeIfAbsent(key, k -> new ArrayList<>())
//                .add(new MtTranslationsRequestDTO(tableName, columnName, null, languageCode, translation));
//    }

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
            return ServiceResponseDTO.success(HttpStatus.OK, "", productsResponseDTO);
        }catch (Exception e){
            throw new ApiRequestException("api.error.generic", ErrorStatus.BAD_REQUEST_400, e);
        }
    }

    @Override
    @Transactional
    public ServiceResponseDTO<ProductsResponseDTO> update(ProductsRequestDTO productsRequestDTO) {
        try {
            Products oldProducts = productsRepository.findById(productsRequestDTO.getId()).orElseThrow(() -> new ApiRequestException("product not found", ErrorStatus.BAD_REQUEST_400));
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
            return ServiceResponseDTO.success(HttpStatus.OK,"", productsResponseDTO);
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage(), ErrorStatus.BAD_REQUEST_400);
        }
    }

    @Override
    public ServiceResponseDTO<ProductsResponseDTO> findByID(UUID productID) {
        Products products = productsRepository.findById(productID).orElseThrow(() -> new ApiRequestException("Product not found", ErrorStatus.BAD_REQUEST_400));
        ProductsResponseDTO productsResponseDTO = new ProductsResponseDTO(products);
        return ServiceResponseDTO.success(HttpStatus.OK,",", productsResponseDTO);
    }

    @Override
    public ServiceResponseDTO<ProductsResponseDTO> deletes(List<UUID> ids) {
        List<Products> products = productsRepository.findAllById(ids);
        productsRepository.deleteAll(products);
        return ServiceResponseDTO.success(HttpStatus.OK,"", null);
    }

    @Override
    public Boolean updateQuantity(UUID productID, Integer quantity, Boolean isAdd) {
        Products product = productsRepository.findById(productID).orElseThrow(() -> new ApiRequestException("Product not found", ErrorStatus.BAD_REQUEST_400));
        product.setQuantity(product.getQuantity() != null? product.getQuantity() + (isAdd? quantity : (quantity * -1)) : quantity);
        productsRepository.save(product);
        return true;
    }

}
