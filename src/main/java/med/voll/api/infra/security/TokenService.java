package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}") // anotacao value deve ser do spring boot e nao do lombok. esta anotacao indica a propriendade a qual a variável deve ser referenciada no arquivo application.properties
    private String secret;

    public String gerarToken(Usuario usuario) {
        // System.out.println(secret); // testa se o @Value("${api.security.token.secret}") foi realmente implementado


        //código abaixo disponível na página de documentacao do JWT.. Código foi ajustado as necessidades do presente projeto
//        try {
//            Algorithm algorithm = Algorithm.RSA256(rsaPublickey, rsaPrivateKey);
//            String token = JWT.create()
//                    .withIssuer("auth0")
//                    .sign(algorithm);
//        }catch (JWTCreationException exception){
//
//        }
        // Código foi ajustado as necessidades do presente projeto

        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med")
                    .withSubject(usuario.getLogin())
                    //                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritimo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }

   }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); //time zone do Brasil
    }
}

