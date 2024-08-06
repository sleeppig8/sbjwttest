package org.example.sbjwttest.Service;

import org.example.sbjwttest.Model.Member;
import org.example.sbjwttest.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("無法找到使用者: " + username);
        }
        List<SimpleGrantedAuthority> authorities = member.getAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.name()))
                .toList();
        return
//                User
//                .withUsername(username)
//                .password(member.getPassword())
//                .authorities(authorities)
//                .build();
                new MemberUserDetails(member);
    }
}
