
package info.rateme.rateme.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "Must enter password")
    @Size(min = 4,message = "Password must be 4 or more characters")
    private String passWord;

    @NotBlank(message = "Must enter first name")
    @Size(min = 2,message = "First name must be 2 or more characters")
    private String userFirstName;

    @NotBlank(message = "Must enter last name")
    @Size(min = 2,message = "Last name must be 2 or more characters")
    private String userLastName;

    @NotBlank(message = "Must enter user name")
    @Size(min = 4,message = "Username must be 4 or more characters")
    private String userName;

    @NotBlank(message = "Must enter full email address")
    @Size(min = 6,message = "Must be real email")
    private String email;

    private LocalDateTime modified;
    private LocalDateTime created;


    public User() {
        this.userFirstName = "";
        this.userLastName = "";
        this.userName = "";
        this.email = "";
        this.passWord = "";

    }

    public User(String userFirstName, String userLastName, String userName, String email, String passWord) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;

    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
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

    @PrePersist
    public void onCreate(){
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(){
        this.setModified(LocalDateTime.now());
    }

    public String toString() {return this.userFirstName + " " + this.userLastName;}
}




