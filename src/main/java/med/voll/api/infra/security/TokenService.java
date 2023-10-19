package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
         System.out.println(secret); // testa se o @Value("${api.security.token.secret}") foi realmente implementado



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
    public String getSubject (String tokenJWT){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch (JWTVerificationException exception){
            throw new RuntimeException(tokenJWT + ": invalido ou expirado");
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); //time zone do Brasil
    }
}


//    public String getSubject (String tokenJWT){
////código abaixo disponível na página de documentacao do JWT.. Código foi ajustado as necessidades do presente projeto
////código abaixo disponível na página de documentacao do JWT.. Código foi ajustado as necessidades do presente projeto
////        try {
////            Algorithm algorithm = Algorithm.RSA256(rsaPublickey, rsaPrivateKey);
////           JWTVerifier verifier = JWT.require(algorithm)
////                   //specify an specific claim validations
////                    .withIssuer("auth0")
////                   // reusable verifier instance
////                    .build();
////           decodeJWT jwt = verifier.verify(token);
////        }catch (JWTVerificationException exception){
////            //Invalid signature/claims
////
////        }
