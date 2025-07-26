package org.finmate.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finmate.member.domain.EmailAuthVO;

@Mapper
public interface EmailAuthMapper {
    void insertAuthCode(EmailAuthVO auth);
    EmailAuthVO findByUuid(String uuid);
    void updateVerified(@Param("uuid") String uuid);
    String findEmailByVerifiedUuid(@Param("uuid") String uuid);
}

