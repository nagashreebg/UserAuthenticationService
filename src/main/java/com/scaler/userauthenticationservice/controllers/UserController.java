package com.scaler.userauthenticationservice.controllers;

import com.scaler.userauthenticationservice.dtos.PasswordChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDetailsManager userDetailsManager;

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) {
        userDetailsManager.changePassword(request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")  // Only admins can delete users
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userDetailsManager.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}
