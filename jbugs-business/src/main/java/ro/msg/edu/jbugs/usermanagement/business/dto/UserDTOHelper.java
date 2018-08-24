package ro.msg.edu.jbugs.usermanagement.business.dto;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

public class UserDTOHelper {


    public static UserDTO fromEntity(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        userDTO.setEmail(user.getEmail());

        userDTO.setUsername(user.getUsername());

        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setActive(user.getActive());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO){
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setActive(userDTO.getActive());
        return user;

    }


    public static User updateEntityWithDTO(User user, UserDTO userDTO){
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setActive(userDTO.getActive());
        return user;
    }
}

