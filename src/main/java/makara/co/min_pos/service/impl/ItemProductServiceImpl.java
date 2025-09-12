package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.ImportProductHistoriesMapper;
import makara.co.min_pos.mapper.ProductMapper;
import makara.co.min_pos.models.entities.ImportProductHistory;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.request.ProductImportHistoriesRequest;
import makara.co.min_pos.models.response.ItemProductResponse;
import makara.co.min_pos.repository.ProductImportHistoriesRepository;
import makara.co.min_pos.repository.ProductRepository;
import makara.co.min_pos.service.GeneralFileService;
import makara.co.min_pos.service.ItemProductService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemProductServiceImpl implements ItemProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final GeneralFileService generalFileService;

    private final ProductImportHistoriesRepository productImportHistoriesRepository;
    private final ImportProductHistoriesMapper importProductHistoriesMapper;

    @Value("${file.server-path}")
    private String fileServerPath;

    @Override
    public ItemProduct create(ItemProduct itemProduct) {
        ItemProduct pro = new ItemProduct();
        pro.setProductCode(itemProduct.getProductCode());
        pro.setProductName(itemProduct.getProductName());
        pro.setProductType(itemProduct.getProductType());
        pro.setCost(itemProduct.getCost());
        pro.setPrice(itemProduct.getPrice());
        pro.setQty(itemProduct.getQty());
        pro.setAlertQty(itemProduct.getAlertQty());
        pro.setBrand(itemProduct.getBrand());
        pro.setItemCategory(itemProduct.getItemCategory());
        pro.setItemUnit(itemProduct.getItemUnit());
        pro.setPurchaseItemUnit(itemProduct.getPurchaseItemUnit());
        pro.setSaleItemUnit(itemProduct.getSaleItemUnit());

        if(itemProduct.getBrand() ==null || itemProduct.getBrand().getId() ==null){
            return null;
        }
        return productRepository.save(pro);
    }

    @Override
    public ItemProduct getById(Long id) {
        return productRepository.findByIdAndIsDeletedFalse(id).
                orElseThrow(()->new ResourceNotFoundException("Product",id));
    }

    @Override
    public ItemProduct deleteBydId(Long id) {
        ItemProduct pro = getById(id);
        pro.setIsDeleted(true);
        return productRepository.save(pro);
    }

    @Override
    public ItemProduct update(Long id, ItemProduct newProduct) {
        ItemProduct itemPro = getById(id);
        itemPro.setProductName(newProduct.getProductName());
        itemPro.setProductCode(newProduct.getProductCode());
        itemPro.setProductType(newProduct.getProductType());
        itemPro.setCost(newProduct.getCost());
        itemPro.setPrice(newProduct.getPrice());
        return productRepository.save(itemPro);
    }

    @Override
    public List<ItemProductResponse> listProduct() {
        return productRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ItemProduct uploadImage(Long id, MultipartFile file) throws Exception {
        String fileName = generalFileService.generalFile(file.getOriginalFilename());

        String destinationPath = fileServerPath + fileName;
        file.transferTo(new File(destinationPath));

        ItemProduct saveImageProduct = getById(id);
        saveImageProduct.setImage(fileName);
        return productRepository.save(saveImageProduct);

    }

    @Override
    public Page<ItemProductResponse> getWithPagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<ItemProductResponse> page = productRepository.findByIsDeletedIsFalseOrderByIdDesc(pageable)
                .map(productMapper::toDTO);
        return page;
    }

    @Override
    public void ImportProduct(ProductImportHistoriesRequest dto) {

        ItemProduct product = getById(dto.getProductId());
        Integer availableUnit = 0;
        if(product.getQty() !=null){
            availableUnit = product.getQty();
        }
        product.setQty( availableUnit + dto.getImportUnit());
        productRepository.save(product);

        ImportProductHistory histories = importProductHistoriesMapper.toEntity(dto, product);
        productImportHistoriesRepository.save(histories);
    }

}
