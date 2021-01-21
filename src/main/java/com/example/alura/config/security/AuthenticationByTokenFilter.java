package com.example.alura.config.security;

import com.example.alura.modelo.Usuario;
import com.example.alura.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationByTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService; //O filtro foi instanciado manualmente na classe SecurityConfigurations e portanto o Spring não consegue realizar injeção de dependências via @Autowired
    private final UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);
        boolean valid = tokenService.isTokenValid(token); // Valida se o token é valido ou não

        if (valid)
            authClient(token);
        filterChain.doFilter(request, response);
    }

    private void authClient(String token) {
        Long idUser = tokenService.getIdUsuario(token);
        Optional<Usuario> usuario = repository.findById(idUser); // Precisa ser findbyid pq carrega o objeto
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
            return null;
        return token.substring(7); //Retorna só o token sem o Bearer

    }
}
