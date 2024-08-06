package org.example.sbjwttest.Service;

import lombok.Getter;
import org.example.sbjwttest.Authority.MemberAuthority;
import org.example.sbjwttest.Model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MemberUserDetails implements UserDetails {
    private UUID id;
    private String username;
    private String password;
    private List<MemberAuthority> memberAuthorities;

    public MemberUserDetails() {
    }

    public MemberUserDetails(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.memberAuthorities = member.getAuthorities();
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public List<MemberAuthority> getMemberAuthorities() {
        return this.memberAuthorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.memberAuthorities
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
