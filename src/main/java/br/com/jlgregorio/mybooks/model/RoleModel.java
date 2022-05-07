package br.com.jlgregorio.mybooks.model;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class RoleModel implements GrantedAuthority   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String authority;


    public RoleModel() {
    }

    public RoleModel(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return authority;
    }
}
