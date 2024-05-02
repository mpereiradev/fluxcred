package dev.matheuspereira.fluxcred.core.infrastructure.repository;

import dev.matheuspereira.fluxcred.core.domain.model.User;
import dev.matheuspereira.fluxcred.core.domain.ports.driven.IUserRepository;
import dev.matheuspereira.fluxcred.core.infrastructure.jpa.UserJpaRepository;
import dev.matheuspereira.fluxcred.core.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
  private final UserJpaRepository userJpaRepository;
  private final ModelMapper modelMapper;

  @Override
  public User save(User user) {
    UserEntity userEntity = userJpaRepository.save(modelMapper.map(user, UserEntity.class));
    return modelMapper.map(userEntity, User.class);
  }
}
