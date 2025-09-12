package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.ExchangeRateMapper;
import makara.co.min_pos.models.entities.ExchangeRate;
import makara.co.min_pos.models.response.ExchangeRateResponse;
import makara.co.min_pos.repository.ExchangeRatesRepository;
import makara.co.min_pos.service.ExchangeRateService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRatesRepository exchangeRatesRepository;
    private final ExchangeRateMapper itemExchangeRateMapper;

    @Override
    public ExchangeRate create(ExchangeRate exchangeRate) {
        return exchangeRatesRepository.save(exchangeRate);
    }

    @Override
    public ExchangeRate getById(Long id) {
        return exchangeRatesRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("ExchangeRate",id));
    }

    @Override
    public ExchangeRate delete(Long id) {
        ExchangeRate getID = getById(id);
        getID.setIsDeleted(true);
        ExchangeRate save = exchangeRatesRepository.save(getID);
        return save;
    }

    @Override
    public ExchangeRate update(Long id, ExchangeRate newExchangeRate) {
        ExchangeRate rate = getById(id);
        rate.setExchangeRate(newExchangeRate.getExchangeRate());
        rate.setFromCurrency(newExchangeRate.getFromCurrency());
        rate.setToCurrency(newExchangeRate.getToCurrency());
        return exchangeRatesRepository.save(rate);
    }

    @Override
    public List<ExchangeRateResponse> listAll() {
        return exchangeRatesRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(itemExchangeRateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ExchangeRateResponse> getWithPagination(Map<String, String> params) {
        int  pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber, pageLimit);
        Page<ExchangeRateResponse> data = exchangeRatesRepository
                .findByIsDeletedIsFalseOrderByIdDesc(pageable)
                .map(itemExchangeRateMapper::toDTO);
        return data;
    }
}
