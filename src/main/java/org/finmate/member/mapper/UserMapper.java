package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.member.domain.UserVO;

@Mapper
public interface UserMapper {

    UserVO selectByAccountId(String accountId);

    void insertUser(UserVO userVO);
    void deleteByAccountId(String accountId);

    Long findUserIdByAccountId(String accountId);

    void deleteUserInfoByUserId(Long userId);
    void deleteUserAttendanceByUserId(Long userId);
    void deleteUserById(Long userId);
}
