package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.SupplierMapper;
import makara.co.min_pos.models.entities.Supplier;
import makara.co.min_pos.models.response.SupplierResponse;
import makara.co.min_pos.repository.SupplierRepository;
import makara.co.min_pos.service.SupplierService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper itemSupplierMapper;

    @Override
    public Supplier create(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier getById(Long id) {
        return supplierRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Supplier",id));
    }

    @Override
    public Supplier updateSupplier(Long id, Supplier newSupplier) {
        Supplier supplier = getById(id);
        supplier.setSupplierLocalName(newSupplier.getSupplierLocalName());
        supplier.setSupplierEngName(newSupplier.getSupplierEngName());
        supplier.setSupplierEmail(newSupplier.getSupplierEmail());
        supplier.setSupplierPhone(newSupplier.getSupplierPhone());
        supplier.setSupplierAddress(newSupplier.getSupplierAddress());
        supplier.setSupplierVatNumber(newSupplier.getSupplierVatNumber());
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier deleteId(Long id) {
        Supplier supplier = getById(id);
        supplier.setIsDeleted(true);
        return supplierRepository.save(supplier);
    }

    @Override
    public List<SupplierResponse> listAll() {
        return supplierRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(itemSupplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<SupplierResponse> getAllPagination(Map<String, String> params) {

        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
           pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<SupplierResponse> page = supplierRepository.findByIsDeletedIsFalseOrderByIdDesc(pageable)
                .map(itemSupplierMapper::toDTO);
        return page;
    }

}
