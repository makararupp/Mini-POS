package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.ItemPurchaseMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.PurchaseItemUnit;
import makara.co.min_pos.models.request.ItemPurchaseRequest;
import makara.co.min_pos.models.response.ItemPurchaseResponse;
import makara.co.min_pos.service.ItemPurchaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchase")
@RequiredArgsConstructor
public class
ItemPurChaseController {
    private final ItemPurchaseService purchaseService;
    private final ItemPurchaseMapper purchaseMapper;

    @PostMapping
    public BaseApi<?> savePurchase(@Valid @RequestBody ItemPurchaseRequest request){
        PurchaseItemUnit itemUnit =  purchaseMapper.toEntity(request);
        PurchaseItemUnit itemUnit1 = purchaseService.save(itemUnit);
        ItemPurchaseResponse responseData = purchaseMapper.toDTO(itemUnit1);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been saved!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("{id}")
    public BaseApi<?> getPurchaseId(@Valid @PathVariable("id")Long purId){
        PurchaseItemUnit unit = purchaseService.getById(purId);
        ItemPurchaseResponse responseData = purchaseMapper.toDTO(unit);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been found")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deletePurId(@Valid @PathVariable("id")Long id){
        PurchaseItemUnit itemUnitId = purchaseService.deleteById(id);
        ItemPurchaseResponse responseDelete = purchaseMapper.toDTO(itemUnitId);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase has bee deleted")
                .timeStamp(LocalDateTime.now())
                .data(responseDelete)
                .build();
    }
    @PutMapping("{id}")
    public BaseApi<?> updatePurchaseId(@Valid @PathVariable("id")Long id,
                                    @RequestBody ItemPurchaseRequest request){
        PurchaseItemUnit unit = purchaseMapper.toEntity(request);
        PurchaseItemUnit unit1 = purchaseService.updateById(id, unit);
        ItemPurchaseResponse responseData = purchaseMapper.toDTO(unit1);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been updated")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("list-all-purchase")
    public BaseApi<?> findAllPurchaseProduct(){
        List<ItemPurchaseResponse> list = purchaseService.getAllPurchase();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been found")
                .timeStamp(LocalDateTime.now())
                .data(list)
                .build();
    }
    @GetMapping("pagination")
    public BaseApi<?> findWithPagination(@Valid @RequestParam Map<String, String> params){
        Page<ItemPurchaseResponse> responsesPage = purchaseService.getWithPagination(params);
        PageDTO dto = new PageDTO(responsesPage);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("specification have bee found")
                .timeStamp(LocalDateTime.now())
                .data(dto)
                .build();
    }

}
