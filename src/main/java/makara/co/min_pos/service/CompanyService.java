package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.Company;
import makara.co.min_pos.models.response.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    Company create(Company company);
    Company getById(Long id);
    Company update(Long id, Company newCompany);
    Company deleteById(Long id);
    List<CompanyResponse> listAll();
    Page<CompanyResponse> findPagination(Map<String, String> params);
    Company saveImage (Long id, MultipartFile file) throws Exception;
}
