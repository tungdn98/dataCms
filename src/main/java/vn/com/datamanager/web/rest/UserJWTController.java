package vn.com.datamanager.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.datamanager.domain.Employee;
import vn.com.datamanager.domain.Roles;
import vn.com.datamanager.repository.EmployeeRepository;
import vn.com.datamanager.security.jwt.JWTFilter;
import vn.com.datamanager.security.jwt.TokenProvider;
import vn.com.datamanager.web.rest.vm.LoginVM;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    EmployeeRepository employeeRepository;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        if (authentication != null) {
            Optional<Employee> isEmployee = employeeRepository.findOneByUsernameIgnoreCase(loginVM.getUsername());
            Employee employee = isEmployee.orElseThrow();
            Set<Roles> setRoles = employee.getEmpGroup().getRoles();
            // lấy getResourceUrl đưa vào trong stream
            Stream<String> currentRoles = setRoles.stream().map(Roles::getResourceUrl);
            Stream<String> ortherRoles = employee.getRoles().stream().map(Roles::getResourceUrl);

            Set<String> roles = new HashSet<>();
            currentRoles.forEach(roles::add);
            ortherRoles.forEach(roles::add);
            Collection<GrantedAuthority> grantedAuthorities = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
            grantedAuthorities.add(new SimpleGrantedAuthority("auth/authenticate"));
            // Tạo đối tượng Authentication mới với grantedAuthorities
            authentication =
                new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorities);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
