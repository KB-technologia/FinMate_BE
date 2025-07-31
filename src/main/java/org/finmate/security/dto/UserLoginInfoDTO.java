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
    private String accountId;
    private String email;
    private String name;
    private String birth;
    private String gender;
    private List<String> roles;

    public static UserLoginInfoDTO of(UserVO user){
        return new UserLoginInfoDTO(
                user.getAccountId(),
                user.getEmail(),
                user.getName(),
                null, // birth (UserVO에 없으므로 null)
                null, // gender (UserVO에 없으므로 null)
                List.of("ROLE_USER")
        );
    }

//    public static UserLoginInfoDTO of(UserVO user){
//        return new UserLoginInfoDTO(
//                user.getAccountId(),
//                user.getEmail(),
//                user.getName(),
//                user.getBirth(),
//                user.getGender(),
//                List.of("ROLE_USER") // 현재는 고정값으로 설정
//        );
//    }
}
