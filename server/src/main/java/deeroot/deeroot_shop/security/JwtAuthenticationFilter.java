package deeroot.deeroot_shop.security;

import deeroot.deeroot_shop.services.AuthenticationService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*
        Method purpose:

        To authenticate a user for each incoming HTTP request using a JWT or token-based authentication
        mechanism, and set the user details in the security context if the token is valid.
         */

        try {

            String token = extractToken(request);
            if (token != null) {
                UserDetails userDetails = authenticationService.validateToken(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (userDetails instanceof ShopUserDetails) {
                    request.setAttribute("userId", ((ShopUserDetails) userDetails).getId());
                }
            }
        } catch (Exception e) {
            // Do not throw exception, just don't authenticate the user
            log.warn("Received invalid auth token");
        }

        filterChain.doFilter(request, response);

    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
