package org.finmate.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finmate.auth.domain.UserVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String username;
    private String email;
    private List<String> roles;

    public static UserInfoDTO of(UserVO member){
        return new UserInfoDTO(
                member.getAccountId(),
                member.getEmail(),
                List.of("ROLE_USER") // 현재는 고정값으로 설정
        );
    }
}
