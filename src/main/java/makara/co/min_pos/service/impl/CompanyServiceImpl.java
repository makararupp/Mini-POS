package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.CompanyMapper;
import makara.co.min_pos.models.entities.Company;
import makara.co.min_pos.models.response.CompanyResponse;
import makara.co.min_pos.repository.CompanyRepository;
import makara.co.min_pos.service.CompanyService;
import makara.co.min_pos.service.GeneralFileService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper itemCompanyMapper;
    private final GeneralFileService generalFileService;

    @Value("${file.server-path}")
    private String fileServerPath;

    @Override
    public Company create(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company getById(Long id) {
        return companyRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("Company",id));
    }

    @Override
    public Company update(Long id, Company newCompany) {
         Company company = getById(id);
         company.setCompanyLocalName(newCompany.getCompanyLocalName());
         company.setCompanyEngName(newCompany.getCompanyEngName());
         company.setCompanyEmail(newCompany.getCompanyEmail());
         company.setCompanyPhone(newCompany.getCompanyPhone());
         company.setCompanyPhone(newCompany.getCompanyPhone());
         company.setCompanyAddress(newCompany.getCompanyAddress());
         company.setVatNumber(newCompany.getVatNumber());
         company.setImagePath(newCompany.getImagePath());
         company.setImage(newCompany.getImage());
        return companyRepository.save(company);
    }

    @Override
    public Company deleteById(Long id) {
        Company company = getById(id);
        company.setIsDeleted(true);
        Company save = companyRepository.save(company);
        return save;
    }

    @Override
    public List<CompanyResponse> listAll() {
        return companyRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(itemCompanyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CompanyResponse> findPagination(Map<String, String> params) {
          int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
          if(params.containsKey(PageUtil.PAGE_LIMIT)){
              pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
          }

          int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
          if(params.containsKey(PageUtil.PAGE_NUMBER)){
              pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
          }

        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<CompanyResponse> page = companyRepository.findByIsDeletedIsFalseOrderByIdDesc(pageable)
                .map(itemCompanyMapper::toDTO);

        return page;
    }

    @Override
    public Company saveImage(Long id, MultipartFile file) throws Exception {
        String newFileName = generalFileService.generalFile(file.getOriginalFilename());
        //After change file it is make transfer image to file path.
        String destinationPath = fileServerPath + newFileName;
        file.transferTo(new File(destinationPath));

        Company saveImageCompany = getById(id);
        //Save name path into db.
        saveImageCompany.setImagePath(newFileName);
        return companyRepository.save(saveImageCompany);

    }
}
