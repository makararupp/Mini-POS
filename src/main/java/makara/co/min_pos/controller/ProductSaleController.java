package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.ProductSaleMapper;
import makara.co.min_pos.models.entities.ProductSale;
import makara.co.min_pos.models.request.ProductSaleRequest;
import makara.co.min_pos.models.response.ProductSaleResponse;
import makara.co.min_pos.service.ProductSaleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("product-sales")
@RequiredArgsConstructor
@Slf4j
public class ProductSaleController {

    private final ProductSaleService productSaleService;
    private final ProductSaleMapper mapper;

    @PostMapping("/{saleId}")
    public BaseApi<?> creatProductSale(@Valid @PathVariable Long saleId,
                                       @RequestBody ProductSaleRequest request){
        request.setSaleId(saleId);
        request.setProductId(request.getProductId());

        ProductSale  updatedProductSale = productSaleService.create(request);
        ProductSaleResponse response = mapper.toResponse(updatedProductSale);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Product sale has been saved")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @PutMapping("increment-qty/{productSaleId}")
    public BaseApi<?> incrementQty(@PathVariable Long productSaleId){
        ProductSaleResponse getById = productSaleService.incrementQty(productSaleId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Quantity increment successfully!")
                .timeStamp(LocalDateTime.now())
                .data(getById)
                .build();
    }

    @PutMapping("decrement-qty")
    public BaseApi<?> decrement(@PathVariable Long productSaleId){
        ProductSaleResponse response = productSaleService.decrementQty(productSaleId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Quantity decrement successfully!")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }

    @GetMapping("{saleId}/{productSaleId}")
    public BaseApi<?> getById(@PathVariable Long saleId, @PathVariable Long productSaleId){
        ProductSaleResponse responseProductSaleId = productSaleService.getById(saleId,productSaleId);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Product saleId and saleId have been found")
                .timeStamp(LocalDateTime.now())
                .data(responseProductSaleId)
                .build();
    }
    @PutMapping("discount-rate/{productSaleId}")
    public BaseApi<?>  updateDiscountRate(
            @PathVariable Long productSaleId,
            @RequestParam Double discount){

       ProductSale updatedProductSale = productSaleService.updateDiscountRate(productSaleId,discount);
       ProductSaleResponse response = mapper.toResponse(updatedProductSale);
       return BaseApi.builder()
               .status(true)
               .code(HttpStatus.OK.value())
               .message("productSale have been update discount rate")
               .timeStamp(LocalDateTime.now())
               .data(response)
               .build();

    }

    @DeleteMapping("/{productSaleId}")
    public BaseApi<?> deleteProductSale(@Valid @PathVariable Long productSaleId){
        ProductSale sale = productSaleService.deleteProductSale(productSaleId);
        ProductSaleResponse  getId = mapper.toResponse(sale);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("product sale have been deleted")
                .timeStamp(LocalDateTime.now())
                .data(getId)
                .build();

    }

}
