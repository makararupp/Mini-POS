package makara.co.min_pos.mapper.helper;

import makara.co.min_pos.models.entities.Brand;
import makara.co.min_pos.service.BrandService;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MapperHelper {
    @Autowired
    private BrandService brandService;
    @Named("getBrandById")
    public Brand getBrandById(Long id){
        if(id == null){
            return null;
        }
        return brandService.getById(id);
    }
}
