package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.GeneralSettingMapper;
import makara.co.min_pos.models.entities.GeneralSetting;
import makara.co.min_pos.models.response.ExchangeRateResponse;
import makara.co.min_pos.models.response.GeneralSettingResponse;
import makara.co.min_pos.repository.GeneralSettingRepository;
import makara.co.min_pos.service.GeneralSettingService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralSettingServiceImpl implements GeneralSettingService {
    private final GeneralSettingRepository settingRepository;
    private final GeneralSettingMapper itemMapper;

    @Override
    public GeneralSetting create(GeneralSetting setting) {
        return settingRepository.save(setting);
    }

    @Override
    public GeneralSetting getById(Long id) {
        return settingRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("GeneralSetting",id));
    }

    @Override
    public GeneralSetting update(Long id, GeneralSetting newSetting) {
        //getId for Verify catch id to update not add new recode.
        GeneralSetting setting = getById(id);
        setting.setSiteTitle(newSetting.getSiteTitle());
        setting.setSiteLogo(newSetting.getSiteLogo());
        setting.setSitePhone(newSetting.getSitePhone());
        setting.setSiteAddress(newSetting.getSiteAddress());
        return settingRepository.save(setting);

    }
    @Override
    public GeneralSetting deleteById(Long id) {
        GeneralSetting setting = getById(id);
        setting.setIsDeleted(true);
        GeneralSetting save = settingRepository.save(setting);
        return  save;
    }

    @Override
    public List<GeneralSettingResponse> listAll() {
        return settingRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<GeneralSettingResponse> getWithPagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<GeneralSettingResponse> page = settingRepository.findByIsDeletedIsFalseOrderByIdDesc(pageable)
                        .map(itemMapper::toDTO);
        return page;
    }
}
