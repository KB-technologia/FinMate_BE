package org.finmate.member.service;

import lombok.RequiredArgsConstructor;
import org.finmate.member.domain.EmailAuthVO;
import org.finmate.member.mapper.EmailAuthMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailAuthMapper emailAuthMapper;
    private final JavaMailSender mailSender;

    public String sendAuthCode(String email) {
        String uuid = UUID.randomUUID().toString();
        String authCode = String.valueOf((int)(Math.random() * 900000) + 100000); // 6자리

        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(3);
        EmailAuthVO auth = EmailAuthVO.builder()
                .email(email)
                .uuid(uuid)
                .authCode(authCode)
                .expiredAt(expiredAt)
                .isVerified(false)
                .build();

        emailAuthMapper.insertAuthCode(auth);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Finmate] 이메일 인증 코드입니다");
        message.setText("인증코드: " + authCode + "\n요청 ID: " + uuid + "\n3분 이내 인증 바랍니다.");

        mailSender.send(message);
        return uuid;
    }

    public boolean verifyCode(String uuid, String inputCode) {
        EmailAuthVO auth = emailAuthMapper.findByUuid(uuid);
        if (auth == null || auth.getIsVerified()) return false;
        if (auth.getExpiredAt().isBefore(LocalDateTime.now())) return false; //3분 이내에 응답
        if (!auth.getAuthCode().equals(inputCode)) return false; //인증코드랑 같은지

        emailAuthMapper.updateVerified(uuid);
        return true;
    }
}
