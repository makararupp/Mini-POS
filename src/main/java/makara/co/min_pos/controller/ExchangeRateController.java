package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.ExchangeRateMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.ExchangeRate;
import makara.co.min_pos.models.request.ExchangeRateRequest;
import makara.co.min_pos.models.response.ExchangeRateResponse;
import makara.co.min_pos.service.ExchangeRateService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateMapper exchangeRateMapper;

    @PostMapping
    public BaseApi<?> saveRate(@Valid @RequestBody ExchangeRateRequest request){
        ExchangeRate exchangeRate = exchangeRateMapper.toEntity(request);
        ExchangeRate getData = exchangeRateService.create(exchangeRate);
        ExchangeRateResponse responseData = exchangeRateMapper.toDTO(getData);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("exchange rate have been saved")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("{id}")
    public BaseApi<?> getRateById(@Valid @PathVariable("id")Long id){
        ExchangeRate exchangeRate = exchangeRateService.getById(id);
        ExchangeRateResponse getRates = exchangeRateMapper.toDTO(exchangeRate);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("exchange rate have been found")
                .timeStamp(LocalDateTime.now())
                .data(getRates)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deleteById(@Valid @PathVariable("id") Long rateId){
        ExchangeRate rate = exchangeRateService.delete(rateId);
        ExchangeRateResponse response = exchangeRateMapper.toDTO(rate);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Exchange rate has been deleted")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();

    }
    @PutMapping("{id}")
    public BaseApi<?> update(@Valid @PathVariable("id")Long rateId,
                             @RequestBody ExchangeRateRequest request){
        ExchangeRate exchange = exchangeRateMapper.toEntity(request);
        ExchangeRate update = exchangeRateService.update(rateId, exchange);
        ExchangeRateResponse rateResponse = exchangeRateMapper.toDTO(update);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("exchange rate have been updated!")
                .timeStamp(LocalDateTime.now())
                .data(rateResponse)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> listAllExchange(){
        List<ExchangeRateResponse> list = exchangeRateService.listAll();

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("exchange rate have been founds")
                .timeStamp(LocalDateTime.now())
                .data(list)
                .build();
    }
    @GetMapping("pagination")
    public BaseApi<?> getPage(@Valid @RequestParam Map<String, String> params){
        Page<ExchangeRateResponse> list = exchangeRateService.getWithPagination(params);
        PageDTO loadPage = new PageDTO(list);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("pagination have been found")
                .timeStamp(LocalDateTime.now())
                .data(loadPage)
                .build();
    }
}
