package info.rateme.rateme.models;

public class UserInfo {

    private String userFirstName;
    private String userLastName;
    private String userName;
    private String email;
    private String passWord;

    public UserInfo() {
        this.userFirstName = "";
        this.userLastName = "";
        this.userName = "";
        this.email = "";
        this.passWord = "";

    }

    public UserInfo(String userFirstName, String userLastName, String userName, String email, String passWord) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
    }

    public UserInfo(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }


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


}



