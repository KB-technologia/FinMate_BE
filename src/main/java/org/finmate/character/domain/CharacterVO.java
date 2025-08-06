package org.finmate.character.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterVO {

    private Long animalId;
    private String animalName;

    private String animalImage1;
    private String animalImage2;
    private String animalImage3;
    private String animalImage4;
}
