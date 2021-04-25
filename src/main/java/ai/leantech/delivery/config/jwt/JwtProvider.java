package ai.leantech.delivery.config.jwt;

import ai.leantech.delivery.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.signingKey}")
    private String jwtPassword;

    private SecretKey key;

    @PostConstruct
    private void postConstruct() {
         key = Keys.hmacShaKeyFor(jwtPassword.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String login, Set<Role> roles) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<String> roleNameList = roles.stream()
                .sorted(Comparator.comparing(Role::getId))
                .map(Role::getName)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setIssuer(jwtIssuer)
                .setSubject(login)
                .setExpiration(date)
                .claim("roles", roleNameList)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (SignatureException sigEx) {
            log.error("Invalid signature");
        } catch (IllegalArgumentException ilArgEx) {
            log.error("Claims string is empty or whitespace");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
