package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.dto.MyPageResponseDTO;
import org.finmate.member.dto.MyPageUpdateRequestDTO;
import org.finmate.member.domain.MyPageVO;
import org.finmate.member.mapper.MyPageMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public MyPageResponseDTO getMyPageInfo(Long userId) {
        MyPageVO myPageVO = myPageMapper.findMyPageInfo(userId);
        return MyPageResponseDTO.from(myPageVO);
    }

    public void updateMyPageInfo(Long userId, MyPageUpdateRequestDTO dto) {
        MyPageVO updated = dto.toDomainWithAccountId(userId.toString());

        myPageMapper.updateMyPageInfo(userId, dto.getPassword(), updated);
    }
}
