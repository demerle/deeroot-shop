package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.auth.AuthResponse;
import deeroot.deeroot_shop.domain.dto.auth.LoginRequest;
import deeroot.deeroot_shop.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

     private final AuthenticationService authenticationService;

     @PostMapping
     public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
         UserDetails userDetails = authenticationService.authenticate(
                 loginRequest.getEmail(),
                 loginRequest.getPassword()
         );
         String tokenValue = authenticationService.generateToken(userDetails);
         AuthResponse authResponse = AuthResponse.builder()
                 .token(tokenValue)
                 .expiresIn(86400)
                 .build();
         return ResponseEntity.ok(authResponse);
     }

     @RequestMapping("/admin")
     @GetMapping ResponseEntity<Boolean> getAdminStatus(@AuthenticationPrincipal UserDetails userDetails){
         if (userDetails == null){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         boolean res = userDetails.getAuthorities()
                 .stream()
                 .anyMatch(a -> a.getAuthority()
                         .equals("ROLE_ADMIN"));

         return ResponseEntity.ok(res);
     }


}
