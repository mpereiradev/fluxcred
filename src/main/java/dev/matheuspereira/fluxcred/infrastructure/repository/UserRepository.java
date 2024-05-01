package dev.matheuspereira.fluxcred.infrastructure.repository;

import dev.matheuspereira.fluxcred.domain.model.User;
import dev.matheuspereira.fluxcred.domain.ports.driven.IUserRepository;
import dev.matheuspereira.fluxcred.infrastructure.entity.UserEntity;
import dev.matheuspereira.fluxcred.infrastructure.jpa.UserJpaRepository;
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
