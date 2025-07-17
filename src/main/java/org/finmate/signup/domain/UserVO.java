package org.finmate.signup.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private LocalDateTime createAt;
}
