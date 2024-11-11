package com.scaler.userauthenticationservice.services;

import com.scaler.userauthenticationservice.models.User;

import java.io.UnsupportedEncodingException;

public interface IOAuthService {
    public static String QR_PREFIX =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    User updateUser2FA(boolean use2FA);

    Object generateQRUrl(User user) throws UnsupportedEncodingException;

    boolean validateVerificationToken(String token);

    User getUser(String token);
}
