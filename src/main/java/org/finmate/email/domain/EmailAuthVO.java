package org.finmate.email.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailAuthVO {
    private String email;
    private String uuid;
    private String authCode;
    private LocalDateTime expiredAt;
    private Boolean isVerified;
}


