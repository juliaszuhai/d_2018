package edu.msg.ro.service;

import edu.msg.ro.boundary.UserManagement;
import edu.msg.ro.exceptions.BusinessException;
import edu.msg.ro.exceptions.ExceptionCode;
import edu.msg.ro.persistence.user.dao.UserPersistenceManager;
import edu.msg.ro.persistence.user.entity.User;
import edu.msg.ro.transfer.UserDTO;
import edu.msg.ro.transfer.UserDTOHelper;
import edu.msg.ro.utils.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class UserManagementBean implements UserManagement {

    private final static int MAX_LAST_NAME_LENGTH = 5;
    private final static int MIN_USERNAME_LENGTH = 6;
    private static final Logger logger = LogManager.getLogger(UserManagementBean.class);

    @EJB
    private UserPersistenceManager userPersistenceManager;

    @Override
    public UserDTO createUser(UserDTO userDTO) throws BusinessException {

        logger.log(Level.INFO, "In createUser method");
        //TODO : Trim first name and last name
        if (!isValidForCreation(userDTO)) {
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }

        if (!userPersistenceManager.getUserByEmail(userDTO.getEmail()).isEmpty()) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }

        User user = UserDTOHelper.toEntity(userDTO);

        String userName = generateUsername(userDTO.getFirstName(), userDTO.getLastName());
        user.setUsername(userName + createSuffix(userName));
        user.setIsActive(true);
        user.setPassword(Encryptor.encrypt(userDTO.getPassword()));
        userPersistenceManager.addUser(user);

        return UserDTOHelper.fromEntity(user);
    }


    /**
     * Creates a suffix for the username, if the username already exists. The suffix consists
     * of a number.
     *
     * @param username
     * @return
     */
    protected String createSuffix(String username) {

        Optional<Integer> max = userPersistenceManager.findUsersNameStartingWith(username)
                .stream()
                .map(x -> x.substring(MIN_USERNAME_LENGTH, x.length()))
                .map(x -> x.equals("")? 0 : Integer.parseInt(x))
                .max(Comparator.naturalOrder())
                .map(x -> x + 1);
        return max.map(Object::toString).orElse("");
    }

    private boolean isValidForCreation(UserDTO user) {
        return user.getEmail() != null
                && user.getLastName() != null
                && user.getEmail() != null
                && user.getPassword() != null
                && isValidEmail(user.getEmail());
    }

    private boolean isValidEmail(String email) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@msggroup.com$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }


    /**
     * Generates a username, taking the first 5 letters of the last name and the first
     * letter of the first name.
     * If the user's last name is not long enough it will try
     * to add the first name's letters to the username until it has 6 characters.
     * If the username already exists it will append a number to the username.
     *
     * @param firstName
     * @param lastName
     * @return generated username
     */
    protected String generateUsername(@NotNull final String firstName, @NotNull final String lastName) {
        StringBuilder username = new StringBuilder();


        if (lastName.length() >= MAX_LAST_NAME_LENGTH) {
            username.append(lastName.substring(0, MAX_LAST_NAME_LENGTH) + firstName.charAt(0));

        } else if (lastName.length() + firstName.length() >= MIN_USERNAME_LENGTH) {
            username.append(lastName + firstName.substring(0, MIN_USERNAME_LENGTH - lastName.length()));
        } else {
            username.append(lastName + firstName);
            int usernameLength = username.length();
            for (int i = 0; i < MIN_USERNAME_LENGTH - usernameLength; i++) {
                username.append("0");
            }
        }


        return username.toString().toLowerCase();

    }

    @Override
    public void deactivateUser(String username) {
        User user = userPersistenceManager.getUserForUsername(username);
        user.setIsActive(false);
        userPersistenceManager.updateUser(user);
    }

    @Override
    public void activateUser(String username) {
        User user = userPersistenceManager.getUserForUsername(username);
        user.setIsActive(true);
        userPersistenceManager.updateUser(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userPersistenceManager.getAllUsers()
                .stream()
                .map(UserDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO login(String username, String password) throws BusinessException {
        User user = userPersistenceManager.getUserForUsername(username);
        if (user == null) {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
        if (!Encryptor.encrypt(password).equals(user.getPassword())) {
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_VALID);
        }

        return UserDTOHelper.fromEntity(user);
    }
}
