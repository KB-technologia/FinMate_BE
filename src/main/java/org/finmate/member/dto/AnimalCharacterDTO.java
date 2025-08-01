package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.AnimalCharacterVO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "동물 캐릭터 DTO")
public class AnimalCharacterDTO {

    @ApiModelProperty(value = "동물 캐릭터 이름", example = "고양이")
    private String animalName;

    @ApiModelProperty(value = "동물 캐릭터 이미지 경로")
    private String animalImage;

    public static AnimalCharacterDTO from(String userCharacterName, String userCharacterImage) {
        return AnimalCharacterDTO.builder()
                .animalName(userCharacterName)
                .animalImage(userCharacterImage)
                .build();
    }
}
