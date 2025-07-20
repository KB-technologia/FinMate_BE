package org.finmate.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "캐릭터 POST 전용 DTO")
public class AnimalChangeDTO {

    private Long userId;
    private Long animalId;
}
