package org.example.sbjwttest.Model;

import lombok.Getter;
import org.example.sbjwttest.Authority.MemberAuthority;
import org.example.sbjwttest.Service.MemberUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public class LoginResponse {

    private String jwt;
    private UUID id;
    private String username;
    private List<MemberAuthority> authorities;

    public static LoginResponse of(String jwt, MemberUserDetails user) {
        var res = new LoginResponse();
        res.jwt = jwt;
        res.id = user.getId();
        res.username = user.getUsername();
        res.authorities = user.getMemberAuthorities();

        return res;
    }

    public String getJwt() {
        return jwt;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<MemberAuthority> getAuthorities() {
        return authorities;
    }
}
