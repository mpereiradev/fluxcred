package dev.matheuspereira.fluxcred.infrastructure.repository;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import dev.matheuspereira.fluxcred.domain.model.User;
import dev.matheuspereira.fluxcred.infrastructure.entity.UserEntity;
import dev.matheuspereira.fluxcred.infrastructure.jpa.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

  @Mock
  private UserJpaRepository userJpaRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private UserRepository userRepository;

  @Test
  void testSave() {
    User user = new User();
    UserEntity userEntity = new UserEntity();
    when(modelMapper.map(any(User.class), eq(UserEntity.class))).thenReturn(userEntity);
    when(userJpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);
    when(modelMapper.map(any(UserEntity.class), eq(User.class))).thenReturn(user);

    User savedUser = userRepository.save(user);

    verify(userJpaRepository).save(userEntity);
    verify(modelMapper).map(user, UserEntity.class);
    verify(modelMapper).map(userEntity, User.class);
    assertNotNull(savedUser);
  }

  @Test
  void testSaveThrowsException() {
    User user = new User();
    when(modelMapper.map(any(User.class), eq(UserEntity.class))).thenThrow(new RuntimeException("Mapping failure"));

    assertThrows(RuntimeException.class, () -> userRepository.save(user));
    verify(userJpaRepository, never()).save(any(UserEntity.class));
  }
}
