package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.SupplierMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.Supplier;
import makara.co.min_pos.models.request.SupplierRequest;
import makara.co.min_pos.models.response.SupplierResponse;
import makara.co.min_pos.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("supplies")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    private final SupplierMapper itemSupplierMapper;

    @PostMapping
    public BaseApi<?> save(@Valid @RequestBody SupplierRequest request){
        Supplier supplier = itemSupplierMapper.toEntity(request);
        Supplier save = supplierService.create(supplier);
        SupplierResponse responseData = itemSupplierMapper.toDTO(save);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("supplier has been save!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();

    }
    @GetMapping("{id}")
    public BaseApi<?> getById(@Valid @PathVariable("id")Long supplierId){
        Supplier supplier = supplierService.getById(supplierId);
        SupplierResponse response = itemSupplierMapper.toDTO(supplier);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("supplier have been found!")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @PutMapping("{id}")
    public BaseApi<?> update(@Valid @PathVariable("id")Long supplierId,
                             @RequestBody SupplierRequest request){
        Supplier supplier = itemSupplierMapper.toEntity(request);
        Supplier load = supplierService.updateSupplier(supplierId, supplier);
        SupplierResponse response = itemSupplierMapper.toDTO(load);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("supplier has been updated!")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deleteById(@Valid @PathVariable("id") Long supplierId){
        Supplier supplier = supplierService.deleteId(supplierId);
        SupplierResponse response = itemSupplierMapper.toDTO(supplier);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("supplier has been deleted")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getAllSupplier(){
        List<SupplierResponse> list = supplierService.listAll();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("supplier have been found")
                .timeStamp(LocalDateTime.now())
                .data(list)
                .build();
    }
    @GetMapping("pagination")
    public BaseApi<?> getSpecification(@Valid @RequestParam Map<String, String> params){
        Page<SupplierResponse> saveData = supplierService.getAllPagination(params);
        PageDTO pageDTO = new PageDTO(saveData);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Specification with Pagination")
                .timeStamp(LocalDateTime.now())
                .data(pageDTO)
                .build();
    }

}
