package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDto;
import org.finmate.member.dto.MyPageUpdateRequestDto;
import org.finmate.member.domain.MyPageVO;
import org.finmate.member.mapper.MyPageMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public MyPageResponseDto getMyPageInfo(Long userId) {
        MyPageVO myPageVO = myPageMapper.findMyPageInfo(userId);
        return MyPageResponseDto.from(myPageVO);
    }

    public void updateMyPageInfo(Long userId, MyPageUpdateRequestDto dto) {
        MyPageVO updated = dto.toDomainWithAccountId(userId.toString());

        myPageMapper.updateMyPageInfo(userId, dto.getPassword(), updated);
    }
}
