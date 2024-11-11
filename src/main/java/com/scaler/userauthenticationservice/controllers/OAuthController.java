package com.scaler.userauthenticationservice.controllers;

import com.google.zxing.WriterException;
import com.scaler.userauthenticationservice.dtos.GenericResponse;
import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.services.IOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @Autowired
    private IOAuthService userService;

    @PostMapping("/user/update/2fa")
    public GenericResponse modifyUser2FA(@RequestParam("use2FA") boolean use2FA)
            throws UnsupportedEncodingException {
        User user = userService.updateUser2FA(use2FA);
        if (use2FA) {
            return new GenericResponse(userService.generateQRUrl(user));
        }
        return null;
    }

    @PostMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) throws IOException, WriterException {
        Boolean result = userService.validateVerificationToken(token);
        if(result) {
            User user = userService.getUser(token);
            if (user.isUsing2FA()) {
//                byte [] image = QRCodeGenerator.getQRCodeImage("http://localhost:9000", 200, 200 );
//                String qrcode = Base64.getEncoder().encodeToString(image);
//                return new GenericResponse( qrcode );
                return "redirect:" + "http://localhost:9000";
            }
        }
        return  "redirect:" + "http://localhost:9000/login";
    }
}
