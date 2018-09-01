package ro.msg.edu.jbugs.usermanagement.persistence.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "permissions")
@NamedQueries(
        {
                @NamedQuery(name = Permission.GET_PERMISSION_BY_TYPE, query = "SELECT p FROM Permission p where p.type = :type"),
                @NamedQuery(name = Permission.GET_PERMISSION_BY_ID, query = "SELECT p FROM Permission p where p.id = :id"),
                @NamedQuery(name = Permission.GET_PERMISSIONS_FOR_ROLE, query = "SELECT r.permissions FROM Role r WHERE r=:role"),
                @NamedQuery(name = Permission.GET_ALL_PERMISSIONS, query = "SELECT p from Permission p"),

        }
)
public class Permission extends BaseEntity {

    @Transient
    private static final int MAX_STRING_LENGTH = 200;
    public static final String GET_PERMISSION_BY_TYPE = "get_permission_by_type";
    public static final String GET_PERMISSION_BY_ID = "get_permission_by_id";
    public static final String GET_PERMISSIONS_FOR_ROLE = "get_permissions_for_role";
    public static final String GET_ALL_PERMISSIONS = "get_all_permissions";

    @Column(name = "type", nullable = false, length = MAX_STRING_LENGTH, unique = true)
    private String type;

    @Column(name = "description")
    private String description;


    public Permission() {
        //Empty constructor needed for Entity
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Permission that = (Permission) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(description, that.description) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type, description);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
