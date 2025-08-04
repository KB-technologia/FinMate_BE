package org.finmate.member.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAccountIdResponseDTO {
    private String name;
    private String accountId;
}
