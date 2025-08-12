package org.finmate.character.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class CharacterVO {

    private final Long animalId;
    private final String animalName;

    private final String animalImage1;
    private final String animalImage2;
    private final String animalImage3;
    private final String animalImage4;
}
