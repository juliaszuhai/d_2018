package ro.msg.edu.jbugs.usermanagement.business.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugmanagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.notificationmanagement.business.service.NotificationManagementService;
import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.TypeNotification;
import ro.msg.edu.jbugs.usermanagement.business.control.AuthenticationManager;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.usermanagement.business.utils.Encryptor;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.PermissionPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.Role;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class UserManagementService {
	public static final int MIN_USERNAME_LENGTH = 6;
	private static final int MIN_LAST_NAME_LENGTH = 5;
	private static final int MAX_FAILED_LOGN_ATTEMPTS = 5;
	static Logger log = LogManager.getLogger(AuthenticationManager.class.getName());
	@EJB
	private UserPersistenceManager userPersistenceManager;
	@EJB
	private BugPersistenceManager bugPersistenceManager;
	@EJB
	private PermissionPersistenceManager permissionPersistenceManager;
	@EJB
	private NotificationManagementService notificationManagementService;

	/**
	 * Creates a user entity using a user DTO.
	 * Also creates the notification message and type to be sent to the new user.
	 *
	 * @param userDTO user information
	 * @return : the user DTO of the created entity
	 * @throws BusinessException
	 */
	public UserDTO createUser(UserDTO userDTO) throws BusinessException {
		List<User> receivers = new ArrayList<>();
		normalizeUserDTO(userDTO);
		validateUserForCreation(userDTO);
		User user = UserDTOHelper.toEntity(userDTO);
		user.setUsername(generateUsername(userDTO.getFirstName(), userDTO.getLastName()));
		user.setActive(true);
		user.setPassword(Encryptor.encrypt(userDTO.getPassword()));


		userPersistenceManager.createUser(user);

		receivers.add(user);
		notificationManagementService.sendNotification(TypeNotification.WELCOME_NEW_USER, user, null, receivers);

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


	private boolean validateFields(UserDTO userDTO) {
		return userDTO.getFirstName() != null
				&& userDTO.getLastName() != null
				&& userDTO.getEmail() != null
				&& userDTO.getPassword() != null
				&& isValidEmail(userDTO.getEmail())
				&& isValidPhoneNumber(userDTO.getPhoneNumber());
	}

	private boolean isValidForCreation(UserDTO userDTO) {
		return validateFields(userDTO)
				&& userDTO.getPassword() != null;
	}

	private boolean isValidEmail(String email) {
		final Pattern validEmailAddressRegex =
				Pattern.compile("^[A-Z0-9._%+-]+@msggroup.com$", Pattern.CASE_INSENSITIVE);

		Matcher matcher = validEmailAddressRegex.matcher(email);
		return matcher.find();
	}

	public List<User> getAllUsersWithRole(Role role) {
		return userPersistenceManager.getAllUsers()
				.stream()
				.filter(user -> user.getRoles().contains(role))
				.collect(Collectors.toList());
	}

	/**
	 * Deactivates a user, removing them the ability to login, but keeping their bugs, comments, etc.
	 * Additionally creates the specific notification.
	 *
	 * @param username
	 */
	public void deactivateUser(String username) throws BusinessException {
		checkForOpenBugs(username);
		Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
		User user = userOptional.orElseThrow(() -> new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
		user.setActive(false);


		/*NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setTypeNotification(TypeNotification.USER_DEACTIVATED);
		notificationDTO.setNewData((new Gson().toJson(user)));*/
		userPersistenceManager.updateUser(user);


		notificationManagementService.sendNotification(TypeNotification.USER_DEACTIVATED, user, null, getAllUsersWithRole(permissionPersistenceManager.getRoleByType("ADM").get()));
	}




	private void checkForOpenBugs(String username) throws BusinessException {
		Optional<Status> found = getBugsAssignedToUser(username)
				.stream()
				.map(BugDTO::getStatus)
				.filter(status -> !Status.CLOSED.equals(status) || !Status.FIXED.equals(status))
				.findAny();
		if (found.isPresent()) {
			throw new BusinessException(ExceptionCode.USER_HAS_ASSIGNED_BUGS);
		}
	}

	/**
	 * Activates a user, granting them the ability to login. asdf
	 *
	 * @param username
	 */
	public void activateUser(String username) throws BusinessException {
		Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
		User user = userOptional.orElseThrow(() -> new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
		user.setActive(true);
		userPersistenceManager.updateUser(user);
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


	public long getUnifinishedBugsAssignedToUser(String username) {
		return getBugsAssignedToUser(username)
				.stream()
				.filter(bug -> !bug.getStatus().equals(Status.CLOSED) ||
						!bug.getStatus().equals(Status.FIXED)
				)
				.count();
	}

	/**
	 * @param username
	 * @throws BusinessException
	 * @return: A list og Bugs assigned to a user, given it's username
	 */
	public List<BugDTO> getBugsAssignedToUser(String username) {
		return bugPersistenceManager.getAllBugs()
				.stream()
				.filter(bug -> bug.getAssignedTo().getUsername().equals(username))
				.map(BugDTOHelper::fromEntity)
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
			int failedAttempts = user.getFailedAttempts() == null ? 1 : user.getFailedAttempts() + 1;
			user.setFailedAttempts(failedAttempts);
			if (userOptional.get().getFailedAttempts() > MAX_FAILED_LOGN_ATTEMPTS) {
				deactivateUser(username);
			}
			throw new BusinessException(ExceptionCode.PASSWORD_NOT_VALID);
		}
		if (!userOptional.get().getActive()) {
			throw new BusinessException(ExceptionCode.USER_DEACTIVATED);
		}

		User user = userOptional.get();
		user.setFailedAttempts(0);
		return UserDTOHelper.fromEntity(userOptional.get());
	}


	/**
	 * Generates a username from the first and last name.
	 *
	 * @param firstName :
	 * @param lastName  :
	 * @return : the generated username
	 */
	protected String generateUsername(@NotNull String firstName, @NotNull String lastName) {
		String username;
		if (lastName.length() >= MIN_LAST_NAME_LENGTH) {
			username = lastName.substring(0, MIN_LAST_NAME_LENGTH) + firstName.substring(0, 1);
			username = rebuildIfUsernameExists(firstName, lastName, username.toLowerCase());
		} else if (firstName.length() >= MIN_LAST_NAME_LENGTH) {
			username = lastName + firstName.substring(0, MIN_LAST_NAME_LENGTH - lastName.length() + 1);
			username = rebuildIfUsernameExists(firstName, lastName, username.toLowerCase());
		} else {
			username = buildUsernameForShortNames(firstName, lastName);
		}
		return username.toLowerCase();
	}

	private String buildUsernameForShortNames(@NotNull String firstName, @NotNull String lastName) {

		StringBuilder sb = new StringBuilder(lastName + firstName);
		while (sb.length() < 6) {
			sb.append('0');
		}
		Optional<User> userOptional = userPersistenceManager.getUserByUsername(sb.toString());
		Integer endNumber = 0;
		while (userOptional.isPresent()) {
			sb.append(endNumber.toString());
			userOptional = userPersistenceManager.getUserByUsername(sb.toString());
			if (userOptional.isPresent()) {
				sb.deleteCharAt(sb.length() - 1);
			}
			endNumber++;
		}

		return sb.toString();
	}


	private String rebuildIfUsernameExists(@NotNull String firstName, @NotNull String lastName, String username) {
		Optional<User> exists = userPersistenceManager.getUserByUsername(username.toLowerCase());

		int stringCutter = 0;
		while (exists.isPresent()) {
			lastName = lastName.substring(0, MIN_LAST_NAME_LENGTH - stringCutter);
			username = lastName +
					firstName.substring(0, MIN_LAST_NAME_LENGTH - lastName.length() + 1);
			exists = userPersistenceManager.getUserByUsername(username.toLowerCase());
			stringCutter++;
		}
		return username;
	}

	private boolean isValidPhoneNumber(String phonenumber) {
		final Pattern validPhoneAddressRegex =
				Pattern.compile("(^\\+49)|(^\\+40)|(^0049)|(^0040)|(^0[1-9][1-9])", Pattern.CASE_INSENSITIVE);

		Matcher matcher = validPhoneAddressRegex.matcher(phonenumber);
		return matcher.find();
	}


	/**
	 * Returns the user entity with the given username.
	 *
	 * @param username
	 * @return
	 * @throws BusinessException
	 */
	public User getUserForUsername(String username) throws BusinessException {
		return userPersistenceManager
				.getUserByUsername(username)
				.orElseThrow(() -> new BusinessException(ExceptionCode.USERNAME_NOT_VALID));
	}

	/**
	 * Returns the user with the given ID.
	 *
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public User getUserForId(Long id) throws BusinessException {
		Optional<User> userOptional = userPersistenceManager.getUserById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
		}
	}

	/**
	 * Updates the user with the contents of the given DTO. If the name of the user has changed it will also
	 * change the username.
	 * Also creates the notification message and type to be sent to both users.
	 *
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	public UserDTO updateUser(UserDTO userDTO, String requester) throws BusinessException {

		Optional<User> userBeforeOptional = userPersistenceManager.getUserByUsername(userDTO.getUsername());
		Optional<User> userRequesterOptional = userPersistenceManager.getUserByUsername(requester);
		List<User> receivers = new ArrayList<>();
		if (userBeforeOptional.isPresent()) {
			User userBefore = userBeforeOptional.get();
			normalizeUserDTO(userDTO);
			validateUserForUpdate(userDTO);
			User userRequester = userRequesterOptional.get();



			if (usernameShouldChange(userBefore, userDTO)) {
				userDTO.setUsername(generateUsername(userDTO.getFirstName(), userDTO.getLastName()));
			}
			User userAfter = UserDTOHelper.updateEntityWithDTO(userBefore, userDTO);


			userPersistenceManager.updateUser(userAfter);


			receivers.add(userAfter);
			receivers.add(userRequester);
			notificationManagementService.sendNotification(TypeNotification.USER_UPDATED, userAfter, userBefore, receivers);



			/*NotificationDTO notificationDTO = new NotificationDTO();
			notificationDTO.setTypeNotification(TypeNotification.USER_UPDATED);
			notificationDTO.setOldData((new Gson().toJson(userBefore)));
			notificationDTO.setNewData((new Gson().toJson(userAfter)));

			try {
				userRequester.getNotifications().add(NotificationDTOHelper.toEntity(notificationDTO));
				userAfter.getNotifications().add(NotificationDTOHelper.toEntity(notificationDTO));
			} catch (ParseException | NullPointerException e) {
				log.catching(e);
			}*/
			return userDTO;
		} else {
			throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
		}
	}

	private boolean usernameShouldChange(User user, UserDTO userDTO) {
		return (user.getFirstName().equals(userDTO.getFirstName()) &&
				user.getLastName().equals(userDTO.getLastName()));

	}

	private void validateUserForUpdate(UserDTO userDTO) throws BusinessException {
		if (!validateFields(userDTO)) {
			throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
		}
	}
}









