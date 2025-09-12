package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.PurchaseMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.request.PurchaseRequest;
import makara.co.min_pos.models.response.PurchaseResponse;
import makara.co.min_pos.service.PurchaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchase-pro")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @PostMapping
    public BaseApi<?> savePurchase(@Valid @RequestBody PurchaseRequest request){
        Purchase purchase = purchaseMapper.toEntity(request);
        purchase = purchaseService.create(request);
        PurchaseResponse response = purchaseMapper.toDTO(purchase);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been saved")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @GetMapping("{id}")
    public BaseApi<?> findPurchase(@Valid @PathVariable("id")Long id){
        Purchase purchase = purchaseService.getById(id);
        PurchaseResponse responseId = purchaseMapper.toDTO(purchase);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been found")
                .timeStamp(LocalDateTime.now())
                .data(responseId)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deletPurchaseId(@Valid @PathVariable("id")Long id){
        Purchase purchase = purchaseService.deleteById(id);
        PurchaseResponse deleteId = purchaseMapper.toDTO(purchase);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been deleted")
                .timeStamp(LocalDateTime.now())
                .data(deleteId)
                .build();
    }
    @PutMapping("update/{id}")
    public BaseApi<?> updatePurchase(@Valid @PathVariable("id") Long purId,
                                     @RequestBody PurchaseRequest request){
        Purchase purchase =  purchaseMapper.toEntity(request);
        Purchase update = purchaseService.update(purId,purchase);
        PurchaseResponse responseData = purchaseMapper.toDTO(update);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been updated")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getAllPurchase(){
        List<PurchaseResponse> responses = purchaseService.getAllPurchase();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchases has been found")
                .timeStamp(LocalDateTime.now())
                .data(responses)
                .build();
    }
    @GetMapping("specification-pagin")
    public BaseApi<?> findPagination(@Valid @RequestParam Map<String, String> params){
        Page<PurchaseResponse> responses = purchaseService.findWithPagination(params);
        PageDTO dto = new PageDTO(responses);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("specification pagination has been found")
                .timeStamp(LocalDateTime.now())
                .data(dto)
                .build();


    }

}
