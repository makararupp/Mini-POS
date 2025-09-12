package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.CategoryMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.Category;
import makara.co.min_pos.models.request.CategoryRequest;
import makara.co.min_pos.models.response.CategoryResponse;
import makara.co.min_pos.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public BaseApi<?> create(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category loadData = categoryService.save(category);
        CategoryResponse responseData = categoryMapper.toDto(loadData);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("category have been save!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }

    @GetMapping("{id}")
    public BaseApi<?> getById(@Valid @PathVariable("id")Long id){
        Category category =  categoryService.getById(id);
        CategoryResponse  responseData = categoryMapper.toDto(category);
        log.info("data"+responseData);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("category have been found")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deleteById(@Valid @PathVariable("id")Long id){
        Category category = categoryService.deleteById(id);
        CategoryResponse response = categoryMapper.toDto(category);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("category has been deleted")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getCategories(){
        List<CategoryResponse> responsesData = categoryService.listAllCategory();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("category have been found")
                .timeStamp(LocalDateTime.now())
                .data(responsesData)
                .build();
    }
    @PutMapping("{id}")
    public BaseApi<?> updated(@Valid @PathVariable("id")Long catId,
                              @RequestBody CategoryRequest request){
        Category category = categoryMapper.toEntity(request);
        Category loadData = categoryService.update(catId,category);
        CategoryResponse responseData = categoryMapper.toDto(loadData);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("category has been updated!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("pagination")
    public BaseApi<?> getPagination(@Valid @RequestParam Map<String, String> params){
        Page<CategoryResponse> responsesData = categoryService.getWithPagination(params);
        PageDTO dto = new PageDTO(responsesData);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("category list all pagination")
                .timeStamp(LocalDateTime.now())
                .data(dto)
                .build();

    }

    @PostMapping("/image/{id}")
    public ResponseEntity<?> saveImage(@PathVariable("id") Long imagId,
                                       @RequestParam  MultipartFile file)
            throws Exception {
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
        Category saveImage = categoryService.saveImage(imagId, file);
        // CategoryResponse response = categoryMapper.toDto(saveImage);
      //  log.info("file saved"+saveImage);
        return ResponseEntity.ok(saveImage);

    }
}
