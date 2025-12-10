package com.springboot.bankingapp.domain.entities;

import com.springboot.bankingapp.domain.entities.types.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_table")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate dateOfBirth;
    private Boolean enabled = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    private List<AccountEntity> accounts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.USER;

    public UserEntity(String name, String email, String password, String phone, LocalDate dateOfBirth, Boolean enabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.enabled = enabled;
    }

    public void addAccount(AccountEntity account) {
        accounts.add(account);
        account.setUser(this);
    }

    public void removeAccount(AccountEntity account) {
        accounts.remove(account);
        account.setUser(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword(){
        return password;
    }
}
