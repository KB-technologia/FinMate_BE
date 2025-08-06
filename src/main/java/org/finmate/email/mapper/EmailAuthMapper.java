package org.finmate.email.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finmate.email.domain.EmailAuthVO;

@Mapper
public interface EmailAuthMapper {
    void insertAuthCode(EmailAuthVO auth);
    EmailAuthVO findByUuid(String uuid);
    void updateVerified(@Param("uuid") String uuid);
    String findEmailByVerifiedUuid(@Param("uuid") String uuid);
}

