package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.SaleMapper;
import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.enums.PaymentStatus;
import makara.co.min_pos.models.request.SaleRequest;
import makara.co.min_pos.models.response.SaleResponse;
import makara.co.min_pos.repository.SaleRepository;
import makara.co.min_pos.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;
    private final SaleMapper saleMapper;
    private final SaleRepository saleRepository;

    @PostMapping
    public BaseApi<?> save(@Valid @RequestBody SaleRequest request){
        Sale sale = saleMapper.toEntities(request);
        Sale load = saleService.create(sale);
        SaleResponse responseSale = saleMapper.toDTO(load);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Sale have been save")
                .timeStamp(LocalDateTime.now())
                .data(responseSale)
                .build();
    }
    @GetMapping("{id}")
    public BaseApi<?> findSaleThatTrue(@Valid @PathVariable("id")Long saleId){
        Sale sale = saleService.getById(saleId);
        SaleResponse responseSale = saleMapper.toDTO(sale);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("sale have been found")
                .timeStamp(LocalDateTime.now())
                .data(responseSale)
                .build();
    }
    @GetMapping("/date/{status}/{date}")
    public BaseApi<?> getSoldDate(@Valid @PathVariable PaymentStatus status,
                                  @PathVariable  String date){
        List<Sale> sales = saleService.getSalesByStatusAndDate(status, LocalDate.parse(date));

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("sale have been found")
                .timeStamp(LocalDateTime.now())
                .data(sales)
                .build();
    }
    @GetMapping("getAllSold")
    public BaseApi<?> getAllSold(){
        List<SaleResponse> responses = saleService.getAllSold();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("sold date have been found")
                .timeStamp(LocalDateTime.now())
                .data(responses)
                .build();
    }
    @PutMapping("payment/{saleId}")
    public BaseApi<?> updatePaymentSale( @PathVariable Long saleId,
                                         @RequestParam PaymentStatus status){
        Sale updatePayment = saleService.updatePayment(saleId,status);
        return BaseApi.builder().status(true)
                .code(HttpStatus.OK.value())
                .message("sold date have been found")
                .timeStamp(LocalDateTime.now())
                .data(updatePayment)
                .build();
    }
    @DeleteMapping("/{id}")
    public BaseApi<?> deleteSale(@PathVariable("id") Long id) {
        Sale sale = saleService.delete(id);
        SaleResponse response = saleMapper.toDTO(sale);
        return BaseApi.builder()
                .code(HttpStatus.OK.value())
                .message("sold date have been found")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @PutMapping("{saleId}/{PaymentStatus}")
    public BaseApi<?> cancelSale(@Valid @PathVariable("saleId") Long saleId){
       Sale sale = saleService.cancelSale(saleId);
       SaleResponse response = saleMapper.toDTO(sale);

       return BaseApi.builder()
               .status(true)
               .code(HttpStatus.OK.value())
               .message("Sale has been canceled successfully")
               .timeStamp(LocalDateTime.now())
               .data(response)
               .build();
    }
    
}
