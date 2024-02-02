package com.akshay.blog.security;

import com.akshay.blog.entities.User;
import com.akshay.blog.exceptions.ResourceNotFoundException;
import com.akshay.blog.reporsitories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param username 
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from database using username

        return this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User"," email_id: "+username,0));
    }
}
