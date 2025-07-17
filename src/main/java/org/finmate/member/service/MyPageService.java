package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDto;
import org.finmate.member.mapper.MyPageMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public MyPageResponseDto getMyPageInfo(Long userId) {
        return myPageMapper.findMyPageInfo(userId);
    }
}
