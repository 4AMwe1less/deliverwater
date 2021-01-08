package cn.deliver.water.entity;

public class UserInfo {

    private String ID;

    private String username;

    private String password;

    private String phone;

    private String gender;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserInfo() {
    }

    public UserInfo(String ID, String username, String password, String phone, String gender) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "ID='" + ID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
