package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.PurchaseDetailMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.PurchaseDetails;
import makara.co.min_pos.models.request.PurchaseDetailRequest;
import makara.co.min_pos.models.response.PurchaseDetailResponse;
import makara.co.min_pos.service.PurchaseDetailService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchase-details")
@RequiredArgsConstructor
public class PurchaseDetailController {

    private final PurchaseDetailService detailService;
    private final PurchaseDetailMapper detailMapper;

    @PostMapping("{purchaseId}")
    public BaseApi<?> createDetailPurchase(@Valid  @PathVariable Long purchaseId,
                                       @RequestBody  PurchaseDetailRequest request){
        PurchaseDetails details = detailMapper.toEntity(request);
        details = detailService.createPurchaseDetail(purchaseId,request);
        PurchaseDetailResponse response = detailMapper.toDTO(details);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase detail have been saved")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @GetMapping("{purchaseDetailId}")
    public BaseApi<?> getDetailId(@Valid @PathVariable Long purchaseDetailId){
        PurchaseDetails details = detailService.getById(purchaseDetailId);
        PurchaseDetailResponse response = detailMapper.toDTO(details);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been found")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }

    @DeleteMapping("{Id}")
    public BaseApi<?> deleteById(@Valid @PathVariable("Id") Long Id){
        PurchaseDetails details = detailService.deleteById(Id);
        PurchaseDetailResponse responseData = detailMapper.toDTO(details);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase delete have been saved")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @PutMapping("update/{Id}")
    public BaseApi<?> updateDetail(@Valid @PathVariable("Id")Long Id,
                                   @RequestBody PurchaseDetailRequest request){
        PurchaseDetails details = detailMapper.toEntity(request);
        PurchaseDetails loadData = detailService.update(Id, request);
        PurchaseDetailResponse response = detailMapper.toDTO(loadData);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase detail have been save")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getAllDetail(){
      List<PurchaseDetailResponse> responses = detailService.getAll();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase detail have bee found")
                .timeStamp(LocalDateTime.now())
                .data(responses)
                .build();
    }

    @GetMapping("list-pagination")
    public BaseApi<?> getWithPagination(@Valid @RequestParam Map<String,String> params){
        Page<PurchaseDetailResponse> responses = detailService.pagination(params);
        PageDTO dto = new PageDTO(responses);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("pagination have been found")
                .timeStamp(LocalDateTime.now())
                .data(dto)
                .build();

    }
}
