package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.Company;
import makara.co.min_pos.models.request.CompanyRequest;
import makara.co.min_pos.models.response.CompanyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toEntity(CompanyRequest request);
    CompanyResponse toDTO(Company company);
}
