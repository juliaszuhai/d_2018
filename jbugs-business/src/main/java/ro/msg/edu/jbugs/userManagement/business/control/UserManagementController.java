package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.utils.Encryptor;
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
public class UserManagementController {
    //TODO rename;
    private final static int MAX_LAST_NAME_LENGTH = 5;
    private final static int MIN_USERNAME_LENGTH = 6;
    public static final int MIN_LAST_NAME_LENGTH = 5;
    public static final int MAX_FAILED_LOGN_ATTEMPTS = 5;
//    private static final Logger logger = LogManager.getLogger(UserManagementController.class);

    @EJB
    private UserPersistenceManager userPersistenceManager;

    /**
     * Creates a user entity using a user DTO.
     *
     * @param userDTO user information
     * @return : the user DTO of the created entity
     * @throws BusinessException
     */
    public UserDTO createUser(UserDTO userDTO) throws BusinessException {

        //   logger.log(Level.INFO, "In createUser method");

        normalizeUserDTO(userDTO);
        validateUserForCreation(userDTO);
        User user = UserDTOHelper.toEntity(userDTO);
        user.setUsername(generateUsername(userDTO.getFirstName(), userDTO.getLastName()));
        user.setIsActive(true);
        user.setPassword(Encryptor.encrypt(userDTO.getPassword()));
        userPersistenceManager.createUser(user);

        return UserDTOHelper.fromEntity(user);
    }


    /**
     * Validates the DTO. To use before sending it further.
     *
     * @param userDTO
     * @throws BusinessException
     */
    private void validateUserForCreation(UserDTO userDTO) throws BusinessException {
        if (!isValidForCreation(userDTO)) {
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }
        //validate if email already exists
        if (userPersistenceManager.getUserByEmail(userDTO.getEmail()).isPresent()) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS_ALREADY);
        }


    }

    /**
     * Trims stuff (first and last name)
     *
     * @param userDTO
     */
    private void normalizeUserDTO(UserDTO userDTO) {
        userDTO.setFirstName(userDTO.getFirstName().trim());
        userDTO.setLastName(userDTO.getLastName().trim());
    }

    /**
     * Creates a suffix for the username, if the username already exists. The suffix consists
     * of a number.
     * TODO : Change this. Probably won't be needed.
     *
     * @param username
     * @return
     */
    protected String createSuffix(String username) {

        Optional<Integer> max = userPersistenceManager.getUsernamesLike(username)
                .stream()
                .map(x -> x.substring(MIN_USERNAME_LENGTH, x.length()))
                .map(x -> x.equals("") ? 0 : Integer.parseInt(x))
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
     * Deactivates a user, removing them the ability to login, but keeping their bugs, comments, etc.
     *
     * @param username
     */
    public void deactivateUser(String username) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userPersistenceManager.getUserByUsername(username).get();
            user.setIsActive(false);
            userPersistenceManager.updateUser(user);
        } else {
            throw (new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
        }

    }

    /**
     * Activates a user, granting them the ability to login. asdf
     *
     * @param username
     */
    public void activateUser(String username) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(true);
            userPersistenceManager.updateUser(user);
        } else {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
    }

    /**
     * Get a list of all Users that are registered.
     *
     * @return
     */
    public List<UserDTO> getAllUsers() {
        return userPersistenceManager.getAllUsers()
                .stream()
                .map(UserDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Takes the username and password of a user and if they are correct, it returns the
     * corresponding DTO. Otherwise it will throw an exception.
     * If there were more than 5 failed login attempts for the user, it will deactivate the user.
     *
     * @param username
     * @param password
     * @return a user DTO if it succeeds.
     * @throws BusinessException
     */
    public UserDTO login(String username, String password) throws BusinessException {
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (!userOptional.isPresent()) {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
        if (!Encryptor.encrypt(password).equals(userOptional.get().getPassword())) {
            User user = userOptional.get();
            if(user.getFailedAttempts()==null){
                user.setFailedAttempts(1);
            } else{
                user.setFailedAttempts(user.getFailedAttempts()+1);
            }
            if(userOptional.get().getFailedAttempts() > MAX_FAILED_LOGN_ATTEMPTS){
                deactivateUser(username);
            }
            throw new BusinessException(ExceptionCode.PASSWORD_NOT_VALID);
        } if(!userOptional.get().getActive()){
            throw new BusinessException(ExceptionCode.USER_DEACTIVATED);
        }

        User user = userOptional.get();
        user.setFailedAttempts(0);
        return UserDTOHelper.fromEntity(userOptional.get());
    }



    /**
     * TODO cazul in care nume + prenume < 6 si deja exista cineva cu acelasi nume.
     * @param firstName
     * @param lastName
     * @return
     */
    protected String generateUsername(@NotNull String firstName,@NotNull String lastName){

        String username;

        if(lastName.length()>= MIN_LAST_NAME_LENGTH){
            username =  lastName.substring(0,MIN_LAST_NAME_LENGTH)+firstName.substring(0,1);
        } else if(firstName.length()>=5)  {
            username = lastName+firstName.substring(0,MIN_LAST_NAME_LENGTH-lastName.length()+1);
        } else{
            username = lastName+firstName;
            while(username.length()<6){
                username+='0';
            }
        }
        username=username.toLowerCase();
        Optional<User> exists = userPersistenceManager.getUserByUsername(username.toLowerCase());

        while(exists.isPresent()){
            int stringCutter = 0;
            lastName = lastName.substring(0,MIN_LAST_NAME_LENGTH-++stringCutter);
            username = lastName+firstName.substring(0,MIN_LAST_NAME_LENGTH-lastName.length()+1).toLowerCase();
            exists = userPersistenceManager.getUserByUsername(username.toLowerCase());
        }

        return username.toLowerCase();
    }

    private boolean isValidPhoneNumber(String phonenumber) {
        //TODO Nu merge
        final Pattern VALID_PHONE_ADDRESS_REGEX =
                Pattern.compile("(^\\+49)|(^01[5-7][1-9])", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_PHONE_ADDRESS_REGEX.matcher(phonenumber);
        return matcher.find();
    }

    private Integer getFailedAttempts(String username) throws BusinessException{
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFailedAttempts();
        } else {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
    }

    public User getUserForUsername(String username) throws BusinessException {
        Optional<User> userOptional=userPersistenceManager.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        } else {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        }
    }
    public User getUserForId(Long id) throws BusinessException {
        User user=userPersistenceManager.getUserById(id);
        if(user==null)
            return null;
        return user;
        }
    }



