package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finmate.member.dto.MyPageResponseDto;

@Mapper
public interface MyPageMapper {
    MyPageResponseDto findMyPageInfo(@Param("userId") Long userId);

    void updateMyPageInfo(
            @Param("userId") Long userId,
            @Param("password") String password,
            @Param("email") String email,
            @Param("birth") String birth
    );
}
