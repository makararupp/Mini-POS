package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.Customer;
import makara.co.min_pos.models.request.CustomerRequest;
import makara.co.min_pos.models.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerRequest request);
    CustomerResponse toDTO(Customer entity);
}
