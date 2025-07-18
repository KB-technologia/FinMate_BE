package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDto;
import org.finmate.member.dto.MyPageUpdateRequestDto;
import org.finmate.member.domain.MyPage;
import org.finmate.member.mapper.MyPageMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public MyPageResponseDto getMyPageInfo(Long userId) {
        MyPage myPage = toDomain(myPageMapper.findMyPageInfo(userId));
        return MyPageResponseDto.from(myPage);
    }

    public void updateMyPageInfo(Long userId, MyPageUpdateRequestDto dto) {
        MyPage updated = dto.toDomainWithAccountId(userId.toString());

        myPageMapper.updateMyPageInfo(
                userId,
                dto.getPassword(),
                updated.getEmail(),
                updated.getBirth()
        );
    }
    private MyPage toDomain(MyPageResponseDto dto) {
        return new MyPage(dto.getAccountId(), dto.getEmail(), dto.getBirth());
    }
}
