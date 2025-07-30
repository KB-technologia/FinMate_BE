package org.finmate.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.member.domain.UserVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfoDTO {
    private String username;
    private String email;
    private List<String> roles;

    public static UserLoginInfoDTO of(UserVO member){
        return new UserLoginInfoDTO(
                member.getAccountId(),
                member.getEmail(),
                List.of("ROLE_USER") // 현재는 고정값으로 설정
        );
    }
}
