package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "캐릭터 POST 전용 DTO")
public class AnimalChangeDTO {

    @ApiModelProperty(value = "동물 캐릭터 ID", example = "2")
    private Long animalId;
}
