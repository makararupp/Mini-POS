package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.ItemUnitMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.ItemUnit;
import makara.co.min_pos.models.request.ItemUnitRequest;
import makara.co.min_pos.models.response.ItemUnitResponse;
import makara.co.min_pos.service.ItemUnitService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("units")
@RequiredArgsConstructor
public class ItemUnitController {
    private final ItemUnitService unitService;
    private final ItemUnitMapper unitMapper;


    @PostMapping
    public BaseApi<?> createUnit(@Valid @RequestBody ItemUnitRequest request){
        ItemUnit itemUnit = unitMapper.toEntity(request);
        ItemUnit loadData = unitService.create(itemUnit);
        ItemUnitResponse responseData = unitMapper.toDTO(loadData);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("item uni have been save")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }

    @GetMapping("{id}")
    public BaseApi<?> getUnitId(@Valid @PathVariable("id")Long unitId){
        ItemUnit unit = unitService.getById(unitId);
        ItemUnitResponse responseData = unitMapper.toDTO(unit);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("unit have been found!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }

    @DeleteMapping("{id}")
    public BaseApi<?> deleteUnitId(@Valid @PathVariable("id")Long unitId){
        ItemUnit unit = unitService.deleteById(unitId);
        ItemUnitResponse responseUnit = unitMapper.toDTO(unit);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("unit item have been delete")
                .timeStamp(LocalDateTime.now())
                .data(responseUnit)
                .build();

    }
    @PutMapping("{id}")
    public BaseApi<?> update(@Valid @PathVariable("id")Long id,
                             @RequestBody ItemUnitRequest request){

        ItemUnit itemUnit = unitMapper.toEntity(request);
        ItemUnit newUnit = unitService.updateUnit(id,itemUnit);
        ItemUnitResponse responseUnit = unitMapper.toDTO(newUnit);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("unit have been update")
                .timeStamp(LocalDateTime.now())
                .data(responseUnit)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getAllUnit(){
        List<ItemUnitResponse> responses = unitService.getAllUnit();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("unit have been found")
                .timeStamp(LocalDateTime.now())
                .data(responses)
                .build();
    }
    @GetMapping("pagination")
    public BaseApi<?> getWithPage(@Valid @RequestParam Map<String, String> params){
        Page<ItemUnitResponse> responses = unitService.findWithPagination(params);
        PageDTO dto = new PageDTO(responses);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("unit have been get pagination")
                .timeStamp(LocalDateTime.now())
                .data(dto)
                .build();

    }

}
