package org.finmate.character.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "동물 캐릭터 DTO")
public class CharacterResponseDTO {

    @ApiModelProperty(value = "동물 캐릭터 이름", example = "고양이")
    private String animalName;

    @ApiModelProperty(value = "사용자 동물 캐릭터 ID", example = "7")
    private Long animalId;

    @ApiModelProperty(value = "동물 캐릭터 이미지 경로")
    private String animalImage;

    public static CharacterResponseDTO from(final String userCharacterName, final Long userAnimalId, final String userCharacterImage) {
        return CharacterResponseDTO.builder()
                .animalName(userCharacterName)
                .animalId(userAnimalId)
                .animalImage(userCharacterImage)
                .build();
    }
}
