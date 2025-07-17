package org.finmate.signup.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.signup.domain.UserVO;

@Mapper
public interface UserMapper {
    void insertUser(UserVO userVO);
}
