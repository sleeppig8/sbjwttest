package org.example.sbjwttest.Controller;

import io.jsonwebtoken.JwtException;
import org.example.sbjwttest.Model.LoginResponse;
import org.example.sbjwttest.Model.Member;
import org.example.sbjwttest.Repository.MemberRepository;
import org.example.sbjwttest.Service.JwtService;
import org.example.sbjwttest.Service.MemberUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class api {
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @RequestMapping("/register")
    public String createMember(@RequestBody Member member) {
        // 加密密碼
        String encodedPwd = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPwd);
        member.setId(null);
        memberRepository.save(member);
        UUID id = member.getId();
        return "新增成功：" + id;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody Member request) {
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Authentication fails because of incorrect password.");
        }

        String jwt = jwtService.createLoginAccessToken(user);
        return LoginResponse.of(jwt, (MemberUserDetails) user);
    }

    @GetMapping("/who-am-i")
    public Map<String, Object> whoAmI(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String jwt = authorization.substring(BEARER_PREFIX.length());
        try {
            return jwtService.parseToken(jwt);
        } catch (JwtException e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }

    @GetMapping("/members")
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/home")
    public String home(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object principal = auth.getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return "你尚未經過身份認證";
        }
        UserDetails userDetails = (UserDetails) principal;
        return String.format(
                "你好，%s，你的權限是：%s",
                userDetails.getUsername(),
                userDetails.getAuthorities()
        );
    }

    @GetMapping("/courses")
    public String getCourses() {
        return "課程列表";
    }

    @PutMapping("/courses")
    public String updateCourse() {
        return "更新課程成功";
    }

    @PostMapping("/select-course")
    public String selectCourse() {
        return "選課成功";
    }

    @GetMapping("/course-feedback")
    public String courseFeedback() {
        return "課程回饋";
    }
}
