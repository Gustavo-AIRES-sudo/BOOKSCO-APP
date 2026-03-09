package Book.demo.User.Service;

import Book.demo.User.Entity.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserModel user){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getUserGmail())//who's the administrator of token
                .withClaim("UserId", user.getId())
                .withClaim("Name", user.getUsername())
                .withClaim("Age", user.getUserAge())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("API DEMO")
                .sign(algorithm);

    }

}
