package org.finmate.character.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "캐릭터 POST 전용 DTO")
public class CharacterChangeRequestDTO {

    @ApiModelProperty(value = "동물 캐릭터 ID", example = "2")
    private Long animalId;
}
