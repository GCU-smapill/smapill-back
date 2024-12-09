package gcu.smapill_back.config.jwt;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.config.auth.UserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil implements InitializingBean {

    @Value("${spring.jwt.secret}")
    private String secret;
    private static SecretKey secretKey;
    private final UserDetailService userDetailService;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }
    public static String createAccessToken(Long userId, String email) {
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .claim("userId", userId)
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명");
            throw new JwtExceptionHandler(ErrorStatus.WRONG_TYPE_SIGNATURE.getMessage(), e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰");
            throw new JwtExceptionHandler(ErrorStatus.TOKEN_EXPIRED.getMessage(), e);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰");
            throw new JwtExceptionHandler(ErrorStatus.WRONG_TYPE_TOKEN.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰");
            throw new JwtExceptionHandler(ErrorStatus.NOT_VALID_TOKEN.getMessage(), e);
        }

    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) { // 띄어쓰기 삭제
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public static String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }
}
