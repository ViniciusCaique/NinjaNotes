package br.com.fiap.noteninja.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.noteninja.user.User;
import br.com.fiap.noteninja.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends OncePerRequestFilter {

    private UserRepository userRepository;

    public LoginFilter(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication != null){
                    var principal = (OAuth2User) authentication.getPrincipal();
                    var optional = userRepository.findById(Long.valueOf(principal.getName()));
                    if (optional.isEmpty()){
                        userRepository.save(User.convert(principal));
                    }
                }

                filterChain.doFilter(request, response);
    }
    
}
