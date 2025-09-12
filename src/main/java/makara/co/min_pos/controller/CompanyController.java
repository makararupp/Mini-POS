package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.CompanyMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.Company;
import makara.co.min_pos.models.request.CompanyRequest;
import makara.co.min_pos.models.response.CompanyResponse;
import makara.co.min_pos.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper itemCompanyMapper;


    @PostMapping
    public BaseApi<?> save(@Valid @RequestBody CompanyRequest request){
        Company company = itemCompanyMapper.toEntity(request);
        Company save = companyService.create(company);
        CompanyResponse responseData = itemCompanyMapper.toDTO(save);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("company have been save!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("{id}")
    public BaseApi<?> getCompanyId(@Valid @PathVariable("id") Long companyId){
        Company company = companyService.getById(companyId);
        CompanyResponse responseData = itemCompanyMapper.toDTO(company);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("company have been found")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @PutMapping("{id}")
    public BaseApi<?> updateCompany(@Valid @PathVariable("id")Long companyId,
                                    @RequestBody CompanyRequest request){
        Company company = itemCompanyMapper.toEntity(request);
        Company saveCom = companyService.update(companyId, company);
        CompanyResponse responseData = itemCompanyMapper.toDTO(saveCom);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("company have been updated!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deleteId(@Valid @PathVariable("id") Long id){
        Company company = companyService.deleteById(id);
        CompanyResponse responseData = itemCompanyMapper.toDTO(company);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("company have been deleted!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getAll(){
        List<CompanyResponse> list = companyService.listAll();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("company have been found!")
                .timeStamp(LocalDateTime.now())
                .data(list)
                .build();

    }
    @GetMapping("pagination")
    public BaseApi<?> getSpecification(@Valid @RequestParam Map<String, String > params){
        Page<CompanyResponse> page = companyService.findPagination(params);
        PageDTO dto = new PageDTO(page);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("company have been specification with pages!")
                .timeStamp(LocalDateTime.now())
                .data(dto)
                .build();

    }
    @PostMapping("image/{id}")
    public ResponseEntity<?> saveImage(@PathVariable("id") Long id,
                                       @RequestParam  MultipartFile file) throws Exception {
        if(file.isEmpty()){
            return  new ResponseEntity<>("Please to select file upload.",
                    HttpStatus.BAD_REQUEST);
        }
        //if user upload from PDF EXCEL Can't to uploads.
        if(!file.getContentType().startsWith("image")){
            return new ResponseEntity<>("Please upload an image file",
                    HttpStatus.BAD_REQUEST);
        }
        //log.info("file saved",file);
        Company saveImage = companyService.saveImage(id, file);
        CompanyResponse response = itemCompanyMapper.toDTO(saveImage);
        return ResponseEntity.ok(response);

    }

}
