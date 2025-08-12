package org.finmate.member.service;

import org.finmate.member.dto.ChangePasswordRequestDTO;
import org.finmate.member.dto.FindAccountIdResponseDTO;
import org.finmate.member.dto.SignupRequestDTO;


public interface MemberService {

    FindAccountIdResponseDTO findAccountIdByUuid(String uuid);

    void verifyUser(String uuid, String accountId);

    void resetPassword(ChangePasswordRequestDTO dto);

    void withdraw(Long userId);

    void signup(SignupRequestDTO dto);

    boolean isAccountIdDuplicate(String accountId);
}

