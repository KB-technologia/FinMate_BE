package org.finmate.auth.mapper;

import org.finmate.auth.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVO selectByAccountId(String accountId);
}
