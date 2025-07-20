package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.AnimalCharacterVO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "캐릭터 DTO")
public class AnimalCharacterDTO {

    private String animalName;

    private String animalImage1;
    private String animalImage2;
    private String animalImage3;
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
