package org.finmate.member.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserRequestDTO {
    private String uuid;
    private String accountId;
}

