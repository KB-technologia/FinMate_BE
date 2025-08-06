package org.finmate.mypage.service;

import org.finmate.mypage.dto.MyPageResponseDTO;
import org.finmate.mypage.dto.MyPageUpdateRequestDTO;
import org.finmate.mypage.dto.UserStatResponseDTO;


public interface MyPageService {
    MyPageResponseDTO getMyPageInfo(Long userId);

    void updateMyPageInfo(Long userId, MyPageUpdateRequestDTO dto);

    UserStatResponseDTO getStatByUserId(Long userId);
}