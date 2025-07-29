package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finmate.member.domain.MyPageVO;

@Mapper
public interface MyPageMapper {

    MyPageVO findMyPageInfo(@Param("userId") Long userId);

    void updateUserWithPassword(
            @Param("userId") Long userId,
            @Param("password") String password,
            @Param("email") String email
    );

    void updateUserWithoutPassword(
            @Param("userId") Long userId,
            @Param("email") String email
    );

    void updateUserInfo(
            @Param("userId") Long userId,
            @Param("birth") String birth
    );
}
