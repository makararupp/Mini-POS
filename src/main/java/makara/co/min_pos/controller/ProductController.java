package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.ImportProductHistoriesMapper;
import makara.co.min_pos.mapper.ProductMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.ImportProductHistory;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.request.ItemProductRequest;
import makara.co.min_pos.models.request.ProductImportHistoriesRequest;
import makara.co.min_pos.models.response.ItemProductResponse;
import makara.co.min_pos.models.response.ProductImportHistoriesResponse;
import makara.co.min_pos.service.ItemProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ItemProductService itemProductService;
    private final ProductMapper productMapper;
    private final ImportProductHistoriesMapper historiesMapper;


    @PostMapping
    public BaseApi<?> save(@Valid @RequestBody ItemProductRequest request){
        ItemProduct itemPro = productMapper.toEntity(request);
        ItemProduct loadData = itemProductService.create(itemPro);
        ItemProductResponse responseData = productMapper.toDTO(loadData);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("product have been save!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();

    }
    @GetMapping("{id}")
    public BaseApi<?> getProductId(@Valid @PathVariable("id")Long proId){
        ItemProduct product = itemProductService.getById(proId);
        ItemProductResponse responsePro = productMapper.toDTO(product);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("product have been found")
                .timeStamp(LocalDateTime.now())
                .data(responsePro)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deleteProductId(@Valid @PathVariable("id")Long id){
        ItemProduct pro = itemProductService.deleteBydId(id);
        ItemProductResponse responsePro = productMapper.toDTO(pro);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("product has been deleted")
                .timeStamp(LocalDateTime.now())
                .data(responsePro)
                .build();
    }
    @PutMapping("{id}")
    public BaseApi<?> updatePro(@Valid @PathVariable("id")Long id,
                                @RequestBody ItemProductRequest request){
        ItemProduct itemPro = productMapper.toEntity(request);
        ItemProduct loadData = itemProductService.update(id, itemPro);
        ItemProductResponse responsePro = productMapper.toDTO(loadData);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("product have been update")
                .timeStamp(LocalDateTime.now())
                .data(responsePro)
                .build();
    }
    @GetMapping("list-product")
    public BaseApi<?> getProducts(){
        List<ItemProductResponse> productResponses = itemProductService.listProduct();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("product have been found")
                .timeStamp(LocalDateTime.now())
                .data(productResponses)
                .build();
    }
    @PostMapping("image/{id}")
    public ResponseEntity<?> saveImage(@Valid @PathVariable("id") Long id, @RequestParam
    MultipartFile file) throws Exception{

        if(file.isEmpty()){
            return  new ResponseEntity<>("file to select file upload",
                    HttpStatus.BAD_REQUEST);

        }
        if(!file.getContentType().startsWith("image")){
            return new ResponseEntity<>("Please upload an image file",
                    HttpStatus.BAD_REQUEST);
        }
        ItemProduct save = itemProductService.uploadImage(id, file);
        ItemProductResponse response = productMapper.toDTO(save);
        return ResponseEntity.ok(save);
    }
    @GetMapping("pagination")
    public BaseApi<?> getWithPage(@Valid @RequestParam Map<String,String> params){
        Page<ItemProductResponse> responsesPage = itemProductService.getWithPagination(params);
        PageDTO dto = new PageDTO(responsesPage);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("product have been found pagination specification")
                .timeStamp(LocalDateTime.now())
                .data(dto)
                .build();
    }

    @PostMapping("/importProduct")
    public BaseApi<?>  importProduct(@Valid @RequestBody ProductImportHistoriesRequest request){
        itemProductService.ImportProduct(request);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Import product successfully.")
                .timeStamp(LocalDateTime.now())
                .data(request)
                .build();
    }

}
