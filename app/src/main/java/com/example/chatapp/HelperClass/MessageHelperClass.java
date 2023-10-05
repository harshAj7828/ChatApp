package com.example.chatapp.HelperClass;

public class MessageHelperClass {
    String name,mobile,lastMessage,email,password,profilePic;

    private int unseenMessage;

    public MessageHelperClass(String name, String mobile, String lastMessage, String email,String profilePic, String password, int unseenMessage) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.email = email;
        this.profilePic = profilePic;
        this.password = password;
        this.unseenMessage = unseenMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUnseenMessage() {
        return unseenMessage;
    }

    public void setUnseenMessage(int unseenMessage) {
        this.unseenMessage = unseenMessage;
    }
}
