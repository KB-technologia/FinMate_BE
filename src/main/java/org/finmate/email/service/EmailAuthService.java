package org.finmate.email.service;

public interface EmailAuthService {
    String sendAuthCode(String email);
    boolean verifyCode(String uuid, String inputCode);
}
