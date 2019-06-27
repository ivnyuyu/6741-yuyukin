package ru.cft.focusstart.yuyukin;

public class Message {
    private String status;
    private String userName;
    private String type;
    private String data;
    private String time;

    public Message(){
        status="NON";
        userName="NON";
        type="NON";
        data="NON";
        time="NON";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%s %s: %s",time,userName,data);
    }
}
