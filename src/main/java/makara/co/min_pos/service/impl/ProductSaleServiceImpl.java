package makara.co.min_pos.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.ProductSaleMapper;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.entities.ProductSale;
import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.enums.PaymentStatus;
import makara.co.min_pos.models.request.ProductSaleRequest;
import makara.co.min_pos.models.response.ProductSaleResponse;
import makara.co.min_pos.repository.ProductSaleRepository;
import makara.co.min_pos.repository.SaleRepository;
import makara.co.min_pos.service.ItemProductService;
import makara.co.min_pos.service.ProductSaleService;
import makara.co.min_pos.service.SaleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSaleServiceImpl implements ProductSaleService {
    private final ProductSaleRepository productSaleRepository;
    private final SaleRepository saleRepository;
    private final ItemProductService itemProductService;
    private final SaleService service;
    private final ProductSaleMapper saleMapper;

    @Override
    @Transactional
    public ProductSale create(ProductSaleRequest request) {
        Long productId = request.getProductId();
        Long saleId = request.getSaleId();
        Double price = request.getUnitPrice();

        verifySaleExistsAndIsPending(saleId);

        ProductSale existingProductSale = productSaleRepository
                .findByItemProductIdAndSaleId(productId, saleId);
        if (existingProductSale != null) {
            return updateExistingProductSale(existingProductSale);
        } else {
            return createNewProductSale(productId, saleId, price);
        }
    }

    private void saveTotalQty(Integer totalQty, Long saleId) {
        Sale sale = service.getById(saleId);
        sale.setTotalQty(totalQty);
        saleRepository.save(sale);
    }

    @Override
    public void verifySaleExistsAndIsPending(Long saleId) {
        if (!saleRepository.existsByIdAndIsDeletedFalseAndPaymentStatus(saleId,
                PaymentStatus.PENDING)) {
            throw new ResourceNotFoundException("Sale", saleId);
        }
    }

    @Override
    public ProductSaleResponse incrementQty(Long productSaleId) {
        ProductSale productSale = productSaleRepository.findById(productSaleId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductSale", productSaleId));
        // Increment the quantity
        int updatedQty = productSale.getQty() + 1;
        // Handle potential null for discount and recalculate total price
        double discount = productSale.getDiscount() != null ? productSale.getDiscount() : 0.0;
        productSale.setQty(updatedQty);
        productSale.setTotalPrice((productSale.getUnitPrice() * updatedQty) - discount);
        // Save the updated entity
        ProductSale updatedProductSale = productSaleRepository.save(productSale);
        // Map the entity to a response DTO
        return saleMapper.toResponse(updatedProductSale);
    }

    @Override
    public ProductSaleResponse decrementQty(Long productSaleId) {
        ProductSale productSale = productSaleRepository.findById(productSaleId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductSale", productSaleId));
        if (productSale.getQty() > 0) {
            productSale.setQty(productSale.getQty() - 1);
        } else {
            throw new RuntimeException("Quantity cannot be less than 0");
        }
        ProductSale updatedProductSale = productSaleRepository.save(productSale);
        // Return the response
        return saleMapper.toResponse(productSale);
    }

    private double calculateTotalPrice(int qty, double unitPrice, double discount) {
        return (unitPrice * qty) - discount;
    }

    @Override
    public ProductSaleResponse getById(Long saleId, Long productSaleId) {
        ProductSale productSale = productSaleRepository
                .findById(productSaleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ProductSale not found", productSaleId));
        return saleMapper.toResponse(productSale);
    }

    @Override
    @Transactional
    public ProductSale updateDiscountRate(Long productSaleId, Double discount) {
        ProductSale productSale = productSaleRepository.findById(productSaleId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Product Sale not found",productSaleId));

        if(discount <0.0 || discount>100.0){
            throw new RuntimeException("Discount rate must be between 0.0 and 1.0");
        }
        productSale.setDiscount(discount);
        return productSaleRepository.save(productSale);
    }

    @Override
    public ProductSale deleteProductSale(Long productSaleId) {
        ProductSale productSale = getProductSale(productSaleId);
        productSale.setIsDeleted(true);
        ProductSale save = productSaleRepository.save(productSale);
        return save;
    }


    private ProductSale updateExistingProductSale(ProductSale existingProductSale){
            int updatedQty = existingProductSale.getQty() + 1;
            double discount = existingProductSale.getDiscount() !=null? existingProductSale.getDiscount() : 0.0;
            existingProductSale.setQty(updatedQty);
            existingProductSale.setDiscount(existingProductSale.getDiscount());
            existingProductSale.setTotalPrice((existingProductSale.getUnitPrice() * updatedQty) - discount);
            saveTotalQty(updatedQty, existingProductSale.getSale().getId());
            return productSaleRepository.save(existingProductSale);
    }
    private ProductSale createNewProductSale(Long productId, Long saleId, Double price){
        ItemProduct product =  itemProductService.getById(productId);
        ProductSale newProductSale = new ProductSale();
        newProductSale.setItemProduct(product);

        Sale sale = new Sale();
        sale.setId(saleId);  // Setting the ID using a setter
        newProductSale.setSale(sale);

        newProductSale.setQty(1);
        newProductSale.setTotalPrice(product.getPrice());
        newProductSale.setUnitPrice(price != null ? price : product.getPrice());
        newProductSale.setDiscount(0.0);

        saveTotalQty(1,saleId);
        return  productSaleRepository.save(newProductSale);
    }
    @Transactional
    private ProductSale getProductSale(Long saleId) {
        return productSaleRepository.findByIdAndIsDeletedFalse(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductSale", saleId));
    }

}
