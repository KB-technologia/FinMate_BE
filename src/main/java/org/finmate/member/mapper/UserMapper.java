package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.member.domain.UserVO;

@Mapper
public interface UserMapper {
    void insertUser(UserVO userVO);
    void deleteByAccountId(String accountId);
}
