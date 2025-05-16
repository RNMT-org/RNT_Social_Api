package ir.rayanovinmt.rnt_social_api.person;

import ir.rayanovinmt.rnt_social_api.core.entity.BaseMapper;
import org.mapstruct.*;
import ir.rayanovinmt.rnt_social_api.person.dto.*;

@Mapper(componentModel = "spring")
public interface PersonMapper extends BaseMapper<PersonEntity, PersonCreateDto, PersonUpdateDto, PersonLoadDto> {
}
