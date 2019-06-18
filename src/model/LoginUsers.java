package model;

public class LoginUsers {
    private String loginUsers;

    @Override
    public String toString() {
        return "LoginUsers{" +
                "loginUsers='" + loginUsers + '\'' +
                '}';
    }

    public String getLoginUsers() {
        return loginUsers;
    }

    public void setLoginUsers(String loginUsers) {
        this.loginUsers = loginUsers;
    }

    public LoginUsers() {
    }

    public LoginUsers(String loginUsers) {
        this.loginUsers = loginUsers;
    }
}
