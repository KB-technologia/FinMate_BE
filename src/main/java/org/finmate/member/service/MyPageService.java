package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDto;
import org.finmate.member.mapper.MyPageMapper;
import org.springframework.stereotype.Service;
import org.finmate.member.dto.MyPageUpdateRequestDto;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public MyPageResponseDto getMyPageInfo(Long userId) {
        return myPageMapper.findMyPageInfo(userId);
    }

    public void updateMyPageInfo(Long userId, MyPageUpdateRequestDto dto) {
        myPageMapper.updateMyPageInfo(userId, dto.getPassword(), dto.getEmail(), dto.getBirth());
    }
}