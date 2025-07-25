package org.finmate.member.domain;

import lombok.*;
import org.finmate.member.domain.enums.Provider;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private Long id;
    private String name;
    private String accountId;
    private String email;
    private String password;
    private LocalDate birth;
    private Provider provider;
    private LocalDateTime createdAt;
}
