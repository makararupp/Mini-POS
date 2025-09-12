package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.BrandMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.Brand;
import makara.co.min_pos.models.request.BrandRequest;
import makara.co.min_pos.models.response.BrandResponse;
import makara.co.min_pos.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    private final BrandMapper brandMapper;

    @PreAuthorize("hasAuthority('brand:write')")
    @PostMapping
    public BaseApi<?> save (@Valid @RequestBody BrandRequest request){
        Brand brand =  brandMapper.toEntity(request);
        Brand save = brandService.create(brand);
        BrandResponse response = brandMapper.toDto(save);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("brands has been save")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }


    @GetMapping("/{id}")
    public BaseApi<?> getOneBrand(@PathVariable("id") Long brandId){

        Brand getData = brandService.getById(brandId);
        BrandResponse response = brandMapper.toDto(getData);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("brand have been found")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public BaseApi<?> updateItemBrand(@Valid @PathVariable("id")Long brandId,
                                      @RequestBody BrandRequest request){
        Brand brand =brandMapper.toEntity(request);
        Brand uptoDated = brandService.update(brandId,brand);
        //When you add update brand use brand its make new filed on recode, Thanks
        BrandResponse response = brandMapper.toDto(uptoDated);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.ACCEPTED.value())
                .message("brand has been updated")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseApi<?> deleteById(@Valid @PathVariable("id") Long id){
        Brand brand = brandService.deleteById(id);
        BrandResponse response = brandMapper.toDto(brand);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.NO_CONTENT.value())
                .message("brand has been deleted")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }

    @PreAuthorize("hasAuthority('brand:read')")
    @GetMapping("list-all")
    public BaseApi<?> getAllBrand(){
        List<BrandResponse> list = brandService.listAllBrand();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.NO_CONTENT.value())
                .message("List all brands success")
                .timeStamp(LocalDateTime.now())
                .data(list)
                .build();
    }

    @GetMapping("pagination")
    public BaseApi<?> getWithPagination(@Valid @RequestParam Map<String,String> params){
        Page<BrandResponse> witPage = brandService.getWithPagination(params);
        PageDTO pageDTO = new PageDTO(witPage);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.NO_CONTENT.value())
                .message("Specification and Pagination have been found")
                .timeStamp(LocalDateTime.now())
                .data(pageDTO)
                .build();
    }

}
