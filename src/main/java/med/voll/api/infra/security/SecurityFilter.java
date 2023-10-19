package med.voll.api.infra.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // anatocao para classe generica.
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService; // tokenService deve ser o do projeto e nao o do spring
    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("chamando filtro");
//        cabecalho de autorizacao.
        var tokenJWT = recuperarToken(request);
        if(tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subject);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());//
            SecurityContextHolder.getContext().setAuthentication(authentication); // considera o usuario logado
            System.out.println("logado na requisicao");
        }
//        System.out.println(subject);
//        System.out.println(tokenJWT); //testando se o token está retornando.

        //

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
//        if(authorizationHeader == null) {
//            throw new RuntimeException("Token JWT nao enviado no cabecalho Authoriation!");
//        }
//        o prefixo padrao do cabecalho e Bearer. A linha a baixo é para trazer apenas o token sem cabecalho.
//        return authorizationHeader.replace("Bearer ", "");
    }

    }


