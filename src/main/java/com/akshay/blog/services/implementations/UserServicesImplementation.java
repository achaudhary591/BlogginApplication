package com.akshay.blog.services.implementations;

import com.akshay.blog.config.AppConstants;
import com.akshay.blog.entities.Role;
import com.akshay.blog.entities.User;
import com.akshay.blog.exceptions.ResourceNotFoundException;
import com.akshay.blog.payloads.UserDTO;
import com.akshay.blog.reporsitories.RoleRepository;
import com.akshay.blog.reporsitories.UserRepository;
import com.akshay.blog.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static com.akshay.blog.config.AppConstants.NORMAL_USER;

@Service
public class UserServicesImplementation implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @param userDTO 
     * @return
     */
    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {

        User user = this.modelMapper.map(userDTO, User.class);

        ///encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        ///managing roles
        Role role = this.roleRepository.findById(NORMAL_USER).get();

        user.getRoles().add(role);

        User savedUser = this.userRepository.save(user);

        return this.modelMapper.map(savedUser, UserDTO.class);
    }

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
     * @param usersDTO
     * @return
     */
    @Override
    public List<UserDTO> createMultipleUsers(List<UserDTO> usersDTO) {

        List<User> users = usersDTO.stream().map(userDTO -> this.userDTOToUser(userDTO)).collect(Collectors.toList());

        List<User> savedUsers = this.userRepository.saveAll(users);

        return savedUsers.stream().map(user -> this.userToUserDTO(user)).collect(Collectors.toList());
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
