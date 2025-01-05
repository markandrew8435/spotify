package voosh.ai.spotify.config;//package voosh.ai.spotify.config;
//
//import voosh.ai.spotify.service.CustomUserDetailsService;
//import voosh.ai.spotify.service.JWTService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.authentication.EmailPasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JWTFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    private ApplicationContext context;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//        String token = null;
//        String email = null;
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            token = authorizationHeader.substring(7);
//            email = jwtService.extractUserName(token);
//        }
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByEmail(email);
//
//            if(jwtService.validateToken(token, userDetails)){
//                EmailPasswordAuthenticationToken authenticationToken =
//                        new EmailPasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//
//        }
//        filterChain.doFilter(request, response);
//    }
//}