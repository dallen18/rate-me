
package info.rateme.rateme.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Must enter password")
    @Size(min = 4,message = "Password must be 4 or more characters")
    private String password;

    @NotBlank(message = "Must enter first name")
    @Size(min = 2,message = "First name must be 2 or more characters")
    private String userFirstname;

    @NotBlank(message = "Must enter last name")
    @Size(min = 2,message = "Last name must be 2 or more characters")
    private String userLastname;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Must enter user name")
    @Size(min = 4,message = "Username must be 4 or more characters")
    private String username;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Must enter full email address")
    @Email(message = "Must be real email")
    private String email;

    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;


    private LocalDateTime modified;
    private LocalDateTime created;

    public User() {
        this.userFirstname = "";
        this.userLastname = "";
        this.username = "";
        this.email = "";
        this.password = "";
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;

    }

    public User(String userFirstname, String userLastname, String username, String email, String password) {
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
    }

    public Long getId() {
        return id;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreated() { return created; }

    public void setCreated(LocalDateTime created) { this.created = created; }

    public LocalDateTime getModified() { return modified; }

    public void setModified(LocalDateTime modified) { this.modified = modified; }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(){this.email = email;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public enum Role{ROLE_ADMIN,ROLE_USER}

    @PrePersist
    public void onCreate(){
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(){
        this.setModified(LocalDateTime.now());
    }

    @Override
    public String toString() {return this.userFirstname + " " + this.userLastname;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role: roles){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.toString());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }


    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")
    private List<Movie> movies;
}




