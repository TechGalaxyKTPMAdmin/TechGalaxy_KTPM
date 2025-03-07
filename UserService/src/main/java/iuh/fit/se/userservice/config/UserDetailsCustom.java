package iuh.fit.se.userservice.config;

import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public UserDetailsCustom(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccountByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username/password không hợp lệ"));

        List<String> roleNames = account.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .toList();

        List<SimpleGrantedAuthority> authorities = roleNames.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        User user = new User(
                account.getEmail(),
                account.getPassword(),
                authorities
        );

        System.out.println(">>> User: " + user);
        return user;
    }
}
