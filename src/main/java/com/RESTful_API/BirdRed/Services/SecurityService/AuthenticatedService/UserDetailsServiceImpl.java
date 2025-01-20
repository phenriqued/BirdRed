package com.RESTful_API.BirdRed.Services.SecurityService.AuthenticatedService;

import com.RESTful_API.BirdRed.Infra.SecurityConfig.UserAuthentication.UserAuthentication;
import com.RESTful_API.BirdRed.Repositories.UserRepository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        if(identifier.contains("@")){
            return userRepository.findByEmail(identifier).map(UserAuthentication::new)
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User Not Found with email: " + identifier)
                    );
        }else{
            return userRepository.findByNickname(identifier).map(UserAuthentication::new)
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User Not Found with nickname: " + identifier)
                    );
        }
    }



}
