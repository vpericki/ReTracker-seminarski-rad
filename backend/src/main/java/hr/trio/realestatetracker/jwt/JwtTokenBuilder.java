package hr.trio.realestatetracker.jwt;

import hr.trio.realestatetracker.dto.RoleDto;
import hr.trio.realestatetracker.model.Role;
import hr.trio.realestatetracker.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenBuilder {
    public static final long JWT_TOKEN_VALIDITY_DURATION = 24L * 60 * 60 * 1000; // 1 day

    private static String secret;

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private static boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        Role role = user.getRole();

        claims.put("userId", user.getId());
        claims.put("role", new RoleDto(role.getId(), role.getName()));

        return doGenerateToken(claims, user.getUsername());
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_DURATION))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Value("${spring.security.jwt.secret}")
    public synchronized void setSecret(String secretSet) {
        secret = secretSet;
    }
}
