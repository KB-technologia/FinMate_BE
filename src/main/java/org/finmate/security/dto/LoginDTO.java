package org.finmate.security.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "로그인 요청 DTO")
public class LoginDTO {
    @ApiModelProperty(value = "사용자 아이디", example= "test" )
    private String username;

    @ApiModelProperty(value = "사용자 비밀번호", example = "1234")
    private String password;

    public static LoginDTO of(HttpServletRequest request){
        ObjectMapper om = new ObjectMapper();
        try{
            LoginDTO dto = om.readValue(request.getInputStream(), LoginDTO.class);
            log.warn("로그인 요청 DTO : {}", dto);
            return dto;
        }
        catch(IOException e){
            throw new BadCredentialsException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
