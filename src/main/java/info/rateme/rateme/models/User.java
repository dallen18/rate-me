
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
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Must enter password")
    @Size(min = 4,message = "Password must be 4 or more characters")
    private String passWord;

    @NotBlank(message = "Must enter first name")
    @Size(min = 2,message = "First name must be 2 or more characters")
    private String userFirstName;

    @NotBlank(message = "Must enter last name")
    @Size(min = 2,message = "Last name must be 2 or more characters")
    private String userLastName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Must enter user name")
    @Size(min = 4,message = "Username must be 4 or more characters")
    private String userName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Must enter full email address")
    @Email(message = "Must be real email")
    private String email;

    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean creditialsNonExpired;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;


    private LocalDateTime modified;
    private LocalDateTime created;


    public User() {
        this.userFirstName = "";
        this.userLastName = "";
        this.userName = "";
        this.email = "";
        this.passWord = "";
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.accountNonExpired = true;


    }

    public User(String userFirstName, String userLastName, String userName, String email, String passWord) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.accountNonExpired = true;

    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.accountNonExpired = true;
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

    public void setCreditialsNonExpired(boolean creditialsNonExpired) {
        this.creditialsNonExpired = creditialsNonExpired;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreated() { return created; }

    public void setCreated(LocalDateTime created) { this.created = created; }

    public LocalDateTime getModified() { return modified; }

    public void setModified(LocalDateTime modified) { this.modified = modified; }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
    public String toString() {return this.userFirstName + " " + this.userLastName;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role: roles){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roles.toString());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return this.creditialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}




