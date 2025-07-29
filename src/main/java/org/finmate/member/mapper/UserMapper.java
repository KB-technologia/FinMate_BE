package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finmate.member.domain.UserVO;

import java.util.List;

@Mapper
public interface UserMapper {

    List<Long> getUserIdAll();

    UserVO selectByAccountId(String accountId);

    void insertUser(UserVO userVO);
    void deleteByAccountId(String accountId);

    Long findUserIdByAccountId(String accountId);

    void deleteUserInfoByUserId(Long userId);
    void deleteUserAttendanceByUserId(Long userId);
    void deleteUserById(Long userId);

    boolean existsByAccountId(@Param("accountId") String accountId);

    String findAccountIdByEmail(String email);
    boolean existsByAccountIdAndEmail(@Param("accountId") String accountId, @Param("email") String email);
    int updatePassword(@Param("accountId") String accountId, @Param("newPassword") String newPassword);
}
