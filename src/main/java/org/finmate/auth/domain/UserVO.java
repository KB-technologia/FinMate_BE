package org.finmate.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Provider;
import java.time.LocalDate;
import java.time.LocalDateTime;

// 임시 테스트용...
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
    private LoginProvider provider;
    private LocalDateTime createAt;
}
