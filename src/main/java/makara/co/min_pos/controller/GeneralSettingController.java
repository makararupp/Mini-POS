package makara.co.min_pos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.mapper.GeneralSettingMapper;
import makara.co.min_pos.models.DTO.PageDTO;
import makara.co.min_pos.models.entities.GeneralSetting;
import makara.co.min_pos.models.request.GeneralSettingRequest;
import makara.co.min_pos.models.response.GeneralSettingResponse;
import makara.co.min_pos.service.GeneralSettingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("general-setting")
@RequiredArgsConstructor
public class GeneralSettingController {
    private final GeneralSettingService generalSettingService;
    private final GeneralSettingMapper itemGeneralSettingMapper;

    @PostMapping
    public BaseApi<?> createSetting(@Valid @RequestBody GeneralSettingRequest request){
        GeneralSetting setting = itemGeneralSettingMapper.toEntity(request);
        GeneralSetting saveData = generalSettingService.create(setting);
        GeneralSettingResponse response = itemGeneralSettingMapper.toDTO(saveData);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Setting have been saved")
                .timeStamp(LocalDateTime.now())
                .data(response)
                .build();
    }
    @GetMapping("{id}")
    public BaseApi<?> getById(@Valid @PathVariable("id")Long settingId){
        GeneralSetting setting = generalSettingService.getById(settingId);
        GeneralSettingResponse saveData = itemGeneralSettingMapper.toDTO(setting);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Setting have been found")
                .timeStamp(LocalDateTime.now())
                .data(saveData)
                .build();
    }

    @PutMapping("{id}")
    public BaseApi<?> updateSetting(@Valid @PathVariable("id")Long settingId,
                                    @RequestBody GeneralSettingRequest request){
        GeneralSetting setting =  itemGeneralSettingMapper.toEntity(request);
        GeneralSetting saveData = generalSettingService.update(settingId, setting);
        GeneralSettingResponse responseData = itemGeneralSettingMapper.toDTO(saveData);

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("setting has been updated!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @DeleteMapping("{id}")
    public BaseApi<?> deleteRecode(@Valid @PathVariable("id")Long settingId){
        GeneralSetting setting = generalSettingService.deleteById(settingId);
        GeneralSettingResponse responseData = itemGeneralSettingMapper.toDTO(setting);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("setting has been deleted!")
                .timeStamp(LocalDateTime.now())
                .data(responseData)
                .build();
    }
    @GetMapping("list-all")
    public BaseApi<?> getAllSetting(){
        List<GeneralSettingResponse> list = generalSettingService.listAll();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("setting have been found")
                .timeStamp(LocalDateTime.now())
                .data(list)
                .build();
    }
    @GetMapping("pagination")
    public BaseApi<?> getPagination(@Valid @RequestParam Map<String,String> params){
        Page<GeneralSettingResponse> responses = generalSettingService.getWithPagination(params);
        PageDTO save = new PageDTO(responses);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("pagination have been found")
                .timeStamp(LocalDateTime.now())
                .data(save)
                .build();
    }
}
