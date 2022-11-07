package io.jay.springbootsnslogin.auth.store;

import io.jay.springbootsnslogin.auth.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJpaStore implements UserStore {

    private final UserRepository userRepository;

    public UserJpaStore(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User retrieveUser(Long id) {
        Optional<UserEntity> result = userRepository.findById(id);
        return result.isPresent() ? result.get().toDomain() : null;
    }

    @Override
    public User retrieveUserByUserIdAndProvider(String username, String provider) {
        Optional<UserEntity> result = userRepository.findByUserIdAndProvider(username, provider);
        return result.isPresent() ? result.get().toDomain() : null;
    }

    @Override
    public User addNewUser(User newUser) {
        return userRepository.save(new UserEntity(newUser)).toDomain();
    }
}
