package com.example.blogplatform.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.blogplatform.user.User;
import com.example.blogplatform.user.UserService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtGuard extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  )
          throws ServletException, IOException
  {
    String authHeader = request.getHeader("Authorization");
    if(authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String token = authHeader.substring(7);
    final String email = jwtService.extractExtraClaim(token, "email");

    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      User user = this.userService.findByEmail(email).orElseThrow();
      if(jwtService.isTokenValid(token, user)) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
        return;
      }
    }

    throw new UnavailableException("Unauthorized");
  }
}
