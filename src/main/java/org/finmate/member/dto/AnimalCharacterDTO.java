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

    @ApiModelProperty(value = "동물 캐릭터 이미지 경로1")
    private String animalImage1;

    @ApiModelProperty(value = "동물 캐릭터 이미지 경로2")
    private String animalImage2;

    @ApiModelProperty(value = "동물 캐릭터 이미지 경로3")
    private String animalImage3;

    @ApiModelProperty(value = "동물 캐릭터 이미지 경로4")
    private String animalImage4;

    public static AnimalCharacterDTO toDTO(AnimalCharacterVO vo) {
        return AnimalCharacterDTO.builder()
                .animalName(vo.getAnimalName())
                .animalImage1(vo.getAnimalImage1())
                .animalImage2(vo.getAnimalImage2())
                .animalImage3(vo.getAnimalImage3())
                .animalImage4(vo.getAnimalImage4())
                .build();
    }
}
