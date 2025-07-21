package org.finmate.auth.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    private final UserVO user;

    public CustomUser(UserVO user, Collection<? extends GrantedAuthority> authorities){
        super(user.getAccountId(), user.getPassword(), authorities);
        this.user = user;
    }
}
