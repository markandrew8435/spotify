  package voosh.ai.spotify;

  import jakarta.servlet.http.HttpServletRequest;
  import jakarta.servlet.http.HttpServletResponse;
  import mes.job_cron.constants.Roles;
  import mes.job_cron.exception.ApiException;
  import mes.job_cron.service.AuthenticationFacade;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  import org.springframework.web.servlet.HandlerInterceptor;

  import java.util.HashMap;
  import java.util.List;
  import java.util.Map;
  
  @Component
  public class RoleInterceptor implements HandlerInterceptor {
  
    // Define path-role mapping
    private final Map<String, List<Roles>> roleAccessMap = new HashMap<>();
    
    @Autowired
    public AuthenticationFacade authenticationFacade;
  
    /**
     * Authenticate user and fetch Roles.
     * Implement based on your authentication system.
     */
    private Roles getRole(HttpServletRequest request) throws ApiException {
      return authenticationFacade.getUser(request).getRole();
      
    }
  
    public RoleInterceptor() {
      // Publicly accessible paths
      roleAccessMap.put("/logout", List.of(Roles.PUBLIC));
      roleAccessMap.put("/signup", List.of(Roles.PUBLIC));
      roleAccessMap.put("/login", List.of(Roles.PUBLIC));
      roleAccessMap.put("/users/update-password", List.of(Roles.PUBLIC));

      // Restricted paths
      roleAccessMap.put("/users/**", List.of(Roles.ADMIN));
      roleAccessMap.put("/artists/**", List.of(Roles.ADMIN, Roles.EDITOR, Roles.VIEWER));
      roleAccessMap.put("/albums/**", List.of(Roles.ADMIN, Roles.EDITOR, Roles.VIEWER));
      roleAccessMap.put("/tracks/**", List.of(Roles.ADMIN, Roles.EDITOR, Roles.VIEWER));
      roleAccessMap.put("/favorites/**", List.of(Roles.ADMIN, Roles.EDITOR, Roles.VIEWER));
    }
  
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
      String path = (request).getRequestURI();

      String authHeader = request.getHeader("Authorization");
      boolean hasRole = false;

      if(authHeader!=null){
        authenticationFacade.verifyToken(request);
        hasRole = true;
      }
      Roles role = hasRole?getRole(request):Roles.PUBLIC;

      // Check access
      boolean accessGranted = roleAccessMap.entrySet().stream()
              .anyMatch(entry -> matchesPath(entry.getKey(), path) && (role.equals(Roles.PUBLIC) || entry.getValue().contains(role)));
  
      if (!accessGranted) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
        response.getWriter().write("Forbidden: Access denied");
        return false;
      }
  
      return true; // Access granted
    }
  
    /**
     * Check if the request path matches the defined pattern.
     */
    private boolean matchesPath(String pattern, String path) {
      if (pattern.endsWith("/**")) {
        String basePattern = pattern.substring(0, pattern.length() - 3); // Remove "/**"
        return path.startsWith(basePattern);
      }
      return pattern.equals(path);
    }
  }
