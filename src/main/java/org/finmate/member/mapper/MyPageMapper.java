package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finmate.member.domain.MyPageVO;

@Mapper
public interface MyPageMapper {
    MyPageVO findMyPageInfo(@Param("userId") Long userId);

    void updateMyPageInfo(
            @Param("userId") Long userId,
            @Param("password") String password,
            @Param("vo") MyPageVO myPageVO
    );
}
