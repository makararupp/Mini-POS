package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.GeneralSetting;
import makara.co.min_pos.models.request.GeneralSettingRequest;
import makara.co.min_pos.models.response.GeneralSettingResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeneralSettingMapper {

    GeneralSetting toEntity(GeneralSettingRequest generalSetting);
    GeneralSettingResponse toDTO(GeneralSetting setting);

}
