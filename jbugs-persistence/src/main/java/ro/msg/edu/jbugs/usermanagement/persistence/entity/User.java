package ro.msg.edu.jbugs.usermanagement.persistence.entity;


import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NamedQueries(
        {
                @NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u FROM User u"),
                @NamedQuery(name = User.GET_USER_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username=:username"),
                @NamedQuery(name= User.GET_USER_BY_EMAIL, query = "SELECT u from User u where u.email = :email "),
                @NamedQuery(name = User.GET_USER_BY_ID, query = "SELECT u from User u where u.id=:id"),
                @NamedQuery(name = User.GET_NOTIFICATIONS_BY_USERID, query = "SELECT u.notifications from User u where u.id=:id"),
        }
)
public class User extends BaseEntity {

    @Transient
    private static final int MAX_STRING_LENGTH = 40;
    public static final String GET_ALL_USERS = "get_All_Users";
    public static final String GET_USER_BY_USERNAME = "get_User_By_Username";
    public static final String GET_USER_BY_EMAIL = "get_User_By_Email";
    public static final String GET_USER_BY_ID="get_User_By_Id";
    public static final String GET_NOTIFICATIONS_BY_USERID = "get_Notifications_By_UserID";

    @Column(name = "firstName", length = MAX_STRING_LENGTH, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = MAX_STRING_LENGTH, nullable = false)
    private String lastName;

    @Column(name = "phoneNumber", length = MAX_STRING_LENGTH, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = MAX_STRING_LENGTH, nullable = false, unique = true)
    private String email;

    @Column(name = "username", length = MAX_STRING_LENGTH, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = MAX_STRING_LENGTH, nullable = false)
    private String password;

    @Column(name = "isActive", length = MAX_STRING_LENGTH, nullable = false)
    private Boolean isActive;

    @Column
    private Integer failedAttempts;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> roles;

    @OneToMany(targetEntity = Notification.class, cascade = CascadeType.PERSIST)
    private List<Notification> notifications;


    public User() {
        //Empty constructor needed for Entity
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List notifications) {
        this.notifications = notifications;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(isActive, user.isActive);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), firstName, lastName, phoneNumber, email, username, password, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", status='" + isActive + '\'' +
                '}';
    }

}
