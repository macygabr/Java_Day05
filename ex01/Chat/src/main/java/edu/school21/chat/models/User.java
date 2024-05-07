package edu.school21.chat.models;

import java.util.Objects;
import java.util.List;

public class User {
    private Long id;
    private String Login;
    private String password;
    private List<Chatroom> created_rooms;
    private List<Chatroom> use_chatroom;

    public User(Long id, String login, String password) {
        this.id = id;
        this.Login = login;
        this.password = password;
    }

    public Long GetId() {
        return this.id;
    }

    public String Login() {
        return this.Login;
    }

    public String Getpassword() {
        return this.password;
    }

    public List<Chatroom> GetCreatedRooms() {
        return this.created_rooms;
    }

    public List<Chatroom> GetUseChatroom() {
        return this.use_chatroom;
    }

    public void SetId(Long id) {
        this.id = id;
    }

    public void SetLogin(String login) {
        this.Login = login;
    }

    public void Setpassword(String password) {
        this.password = password;
    }

    public void SetCreatedRooms(List<Chatroom> created_rooms) {
        this.created_rooms = created_rooms;
    }

    public void SetUseChatroom(List<Chatroom> use_chatroom) {
        this.use_chatroom = use_chatroom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id) && Objects.equals(Login, user.Login) && Objects.equals(password, user.password) && Objects.equals(created_rooms, user.created_rooms) && Objects.equals(use_chatroom, user.use_chatroom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Login, password, created_rooms, use_chatroom);
    }

    @Override
    public String toString() {
        return "User{" +
                "id= " + id +
                ", Login= " + Login +
                ", password= " + password +
                ", created_rooms= " + created_rooms +
                ", use_chatroom= " + use_chatroom +
                '}';
    }
}
