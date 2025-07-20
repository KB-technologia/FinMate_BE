package org.finmate.security.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String username;
    private String password;

    public static LoginDTO of(HttpServletRequest request){
        ObjectMapper om = new ObjectMapper();
        try{
            return om.readValue(request.getInputStream(), LoginDTO.class);
        }
        catch(IOException e){
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
