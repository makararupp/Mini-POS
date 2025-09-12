package makara.co.min_pos.service.impl;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.ItemUnitMapper;
import makara.co.min_pos.models.entities.ItemUnit;
import makara.co.min_pos.models.response.ItemUnitResponse;
import makara.co.min_pos.repository.UnitRepository;
import makara.co.min_pos.service.ItemUnitService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemUnitServiceImpl implements ItemUnitService {

    private final UnitRepository unitRepository;
    private final ItemUnitMapper itemUnitMapper;

    @Override
    public ItemUnit create(ItemUnit itemUnit) {
        ItemUnit parentUint = new ItemUnit();
        parentUint.setItemUnitCode(itemUnit.getItemUnitCode());
        parentUint.setItemUnitName(itemUnit.getItemUnitName());
        parentUint.setOperator(itemUnit.getOperator());
        parentUint.setOperationValue(itemUnit.getOperationValue());
        if(itemUnit.getParent() !=null && itemUnit.getParent().getId() !=null){
            parentUint.setParent(itemUnit.getParent());
        }
        return unitRepository.save(parentUint);
    }

    @Override
    public ItemUnit getById(Long id) {
        return unitRepository.findByIdIsAndIsDeletedFalse(id).orElseThrow(()->
                new ResourceNotFoundException("ItemUnit",id));
    }

    @Override
    public ItemUnit deleteById(Long id) {
        ItemUnit getId = getById(id);
        getId.setIsDeleted(true);
        ItemUnit save = unitRepository.save(getId);
        return save;
    }

    @Override
    public ItemUnit updateUnit(Long id, ItemUnit newItemUnit) {
        ItemUnit unit = getById(id);
        unit.setItemUnitCode(newItemUnit.getItemUnitCode());
        unit.setItemUnitName(newItemUnit.getItemUnitName());
        unit.setOperator(newItemUnit.getOperator());
        unit.setOperationValue(newItemUnit.getOperationValue());
        if(unit.getParent() !=null && unit.getParent() !=null){
            ItemUnit parentItemUnit1 = getById(unit.getParent().getId());
        }else {
            unit.setParent(null);
        }
        return unitRepository.save(unit);
    }

    @Override
    public List<ItemUnitResponse> getAllUnit() {
        return unitRepository.findAllByParentIsNullAndIsDeletedFalseOrderByIdDesc()
                .stream()
                .map(itemUnitMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ItemUnitResponse> findWithPagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<ItemUnitResponse> page = unitRepository
                .findAllByParentIsNullAndIsDeletedFalseOrderByIdDesc(pageable)
                .map(itemUnitMapper::toDTO);

        return page;
    }
}
