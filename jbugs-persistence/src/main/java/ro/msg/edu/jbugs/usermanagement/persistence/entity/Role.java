package ro.msg.edu.jbugs.usermanagement.persistence.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
@NamedQueries(
        {
              @NamedQuery(name = Role.GET_ALL_ROLES,query = "SELECT r FROM Role r"),
              @NamedQuery(name = Role.GET_ROLE_BY_TYPE,query = "SELECT r FROM Role r where r.type = :type")
        }
)
public class Role extends BaseEntity {

    @Transient
    private static final int MAX_STRING_LENGTH = 20;
    public static final String GET_ALL_ROLES = "get_all_roles";
    public static final String GET_ROLE_BY_TYPE = "get_role_by_type";

    @Column(name = "type", length = MAX_STRING_LENGTH)
    private String type;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Permission> permissions;


    public Role() {
        //Empty constructor needed for Entity
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(type, role.type) &&
                Objects.equals(id, role.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }


}
