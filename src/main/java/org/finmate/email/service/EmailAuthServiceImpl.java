package org.finmate.email.service;

import lombok.RequiredArgsConstructor;
import org.finmate.email.domain.EmailAuthVO;
import org.finmate.email.mapper.EmailAuthMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailAuthServiceImpl implements EmailAuthService {

    private final EmailAuthMapper emailAuthMapper;
    private final JavaMailSender mailSender;

    //이메일 인증 코드 전송 - 회원가입 / 마이페이지 에서 공통 사용
    @Override
    @Transactional
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

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("[FinMate] 이메일 인증을 진행해주세요.");
            helper.setText(buildEmailHtml(authCode), true); // HTML 사용(true)

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }

        return uuid;
    }

    private String buildEmailHtml(String authCode) {
        return """
    <html>
    <body style="font-family: 'Arial'; background-color: #f2f4f6; padding: 40px;">
      <div style="max-width: 600px; margin: auto; background-color: white; padding: 30px 40px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">

        <div style="display: flex; align-items: center; margin-bottom: 25px;">
          <img src="https://github.com/yuchan628/minihomepage/blob/main/kiwiLogo.png?raw=true"
               alt="logo"
               style="width: 48px; height: 48px; margin-right: 2px;" />
          <h2 style="margin: 0; font-size: 22px; color: #222;">FinMate 이메일 인증</h2>
        </div>

        <p style="font-size: 16px; color: #333;">아래 인증코드를 3분 이내에 입력해주세요.</p>

        <div style="background-color: #f0f4ff; border-left: 5px solid #3366cc; padding: 20px; margin: 30px 0; text-align: center; border-radius: 4px;">
          <span style="font-size: 36px; font-weight: bold; color: #003399;">%s</span>
        </div>

        <p style="font-size: 13px; color: #999;">※ 인증코드는 보안상 노출되지 않도록 주의해주세요.</p>
        <p style="font-size: 12px; color: #bbb;">본 메일은 발신 전용입니다.</p>

      </div>
    </body>
    </html>
    """.formatted(authCode);
    }

    // 인증 코드 검증 - 회원가입 / 마이페이지에서 공통 사용
    @Override
    @Transactional
    public boolean verifyCode(String uuid, String inputCode) {
        EmailAuthVO auth = emailAuthMapper.findByUuid(uuid);
        if (auth == null || auth.getIsVerified()) return false;
        if (auth.getExpiredAt().isBefore(LocalDateTime.now())) return false; //3분 이내에 응답
        if (!auth.getAuthCode().equals(inputCode)) return false; //인증코드랑 같은지

        emailAuthMapper.updateVerified(uuid);
        return true;
    }
}
