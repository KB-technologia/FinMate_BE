package org.finmate.security.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class LoginDTO {
    private String username;
    private String password;

    public static LoginDTO of(HttpServletRequest request){
        ObjectMapper om = new ObjectMapper();
        try{
//            log.warn(om.readValue(request.getInputStream(), LoginDTO.class));
            LoginDTO dto = om.readValue(request.getInputStream(), LoginDTO.class);
            log.warn("로그인 요청 DTO : {}", dto);
            return dto;
//            return om.readValue(request.getInputStream(), LoginDTO.class);
        }
        catch(IOException e){
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
