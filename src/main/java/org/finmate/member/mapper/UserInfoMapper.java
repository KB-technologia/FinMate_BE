package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finmate.member.domain.UserInfoVO;

@Mapper
public interface UserInfoMapper {

    // user_info 조회
    UserInfoVO getUserInfoById(Long userId);

    // user_info 테이블에 저장
    void insertUserInfo(UserInfoVO userInfo);
}
