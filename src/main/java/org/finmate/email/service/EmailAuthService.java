package org.finmate.email.service;

public interface EmailAuthService {
    String sendAuthCodeForSignUp(String email);
    String sendAuthCode(String email);
    boolean verifyCode(String uuid, String inputCode);
}
