package dev.matheuspereira.fluxcred.core.infrastructure.security;

import dev.matheuspereira.fluxcred.core.infrastructure.jpa.UserJpaRepository;
import dev.matheuspereira.fluxcred.core.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserService implements UserDetailsService {
  private final UserJpaRepository userJpaRepository;

  @Override
  public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
    return userJpaRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
