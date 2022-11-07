package io.jay.springbootsnslogin.auth.store;

import io.jay.springbootsnslogin.auth.domain.RoleType;
import io.jay.springbootsnslogin.auth.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;
    private String username;
    private String provider;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles;

    @CreationTimestamp
    private Timestamp creationTimestamp;

    public UserEntity(User newUser) {
        this.id = newUser.getId();
        this.userId = newUser.getUserId();
        this.username = newUser.getUsername();
        this.provider = newUser.getProvider();
        this.roles = newUser.getRoles();
    }

    public User toDomain() {
        User user = new User();
        user.setId(id);
        user.setUserId(userId);
        user.setUsername(username);
        user.setProvider(provider);
        user.setRoles(roles);
        user.setCreationTimestamp(creationTimestamp);
        return user;
    }
}
