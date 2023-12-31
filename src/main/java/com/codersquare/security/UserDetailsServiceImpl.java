package com.codersquare.security;

import com.codersquare.user.User;
import com.codersquare.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsernameOrEmail(username,username)
                .map(UserAdapter::new).
                orElseThrow(() -> new UsernameNotFoundException("Email or Password are Wrong!"));
    }

    private static class UserAdapter extends User implements UserDetails {

        private UserAdapter(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
            return Collections.singletonList(authority);
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return !isLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

    }
}
