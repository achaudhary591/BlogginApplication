package com.akshay.blog.services.implementations;

import com.akshay.blog.entities.User;
import com.akshay.blog.payloads.UserDTO;
import com.akshay.blog.reporsitories.UserRepository;
import com.akshay.blog.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImplementation implements UserServices {

    @Autowired
    private UserRepository userRepository;
    /**
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = this.userDTOTouser(userDTO);
        User savedUser = this.userRepository.save(user);
        return this.userToUserDTO(savedUser);
    }

    /**
     * @param user
     * @param userId
     * @return
     */
    @Override
    public UserDTO updateUser(UserDTO user, Integer userId) {


        return null;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public UserDTO getuserById(Integer userId) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return null;
    }

    /**
     * @param userId
     */
    @Override
    public void deleteUser(Integer userId) {

    }

    public User userDTOTouser(UserDTO userDTO){

        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        return user;
    }

    public UserDTO userToUserDTO(User user){

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());

        return userDTO;
    }
}
