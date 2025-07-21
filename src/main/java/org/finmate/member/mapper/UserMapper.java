package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.member.domain.UserVO;

@Mapper
public interface UserMapper {

    UserVO selectByAccountId(String accountId);

    void insertUser(UserVO userVO);
}
