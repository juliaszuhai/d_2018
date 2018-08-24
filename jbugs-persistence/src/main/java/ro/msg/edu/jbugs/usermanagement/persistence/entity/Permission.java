package ro.msg.edu.jbugs.usermanagement.persistence.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "permissions")
@NamedQueries(
        {
                @NamedQuery(name = Permission.GET_PERMISSION_BY_TYPE, query = "SELECT p FROM Permission p where p.type = :type")
        }
)
public class Permission extends BaseEntity {

    @Transient
    private static final int MAX_STRING_LENGTH = 20;
    public static final String GET_PERMISSION_BY_TYPE = "get_permission_by_type";

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
