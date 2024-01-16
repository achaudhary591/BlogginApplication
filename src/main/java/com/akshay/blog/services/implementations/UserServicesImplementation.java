package com.akshay.blog.services.implementations;

import com.akshay.blog.entities.User;
import com.akshay.blog.exceptions.ResourceNotFoundException;
import com.akshay.blog.payloads.UserDTO;
import com.akshay.blog.reporsitories.UserRepository;
import com.akshay.blog.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

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

        User user = this.userDTOToUser(userDTO);
        User savedUser = this.userRepository.save(user);
        return this.userToUserDTO(savedUser);
    }

    /**
     * @param userDTO
     * @param userId
     * @return
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " ID ",userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepository.save(user);

        UserDTO updateUserDTO = this.userToUserDTO(updatedUser);

        return updateUserDTO;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public UserDTO getUserById(Integer userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " ID ",userId));
        return this.userToUserDTO(user);
    }

    /**
     * @return
     */
    @Override
    public List<UserDTO> getAllUsers() {

        List<User> allUsers = this.userRepository.findAll();

        List<UserDTO> allUserDTO = allUsers.stream().map(user -> this.userToUserDTO(user)).collect(Collectors.toList());
        return allUserDTO;
    }

    /**
     * @param userId
     */
    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " ID ",userId));

        this.userRepository.delete(user);
        System.out.println(user.getName()+" deleted successfully");
    }

    /**
     * @param users 
     * @return
     */
    @Override
    public List<UserDTO> createMultipleUsers(List<UserDTO> users) {
        return null;
    }

    public User userDTOToUser(UserDTO userDTO){

        User user = new User();

        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        return user;
    }

    public UserDTO userToUserDTO(User user){

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());

        return userDTO;
    }
}
