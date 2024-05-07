package edu.school21.chat.models;

import java.util.Objects;
import java.util.List;

public class Chatroom {
    private Long id;
    private User name;
    private User owner;
    private List<Message> message;

    public Chatroom(Long id, User name, User owner, List<Message> message) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.message = message;
    }

    public Chatroom(Long id, String name, String owner) {
        this.id = id;
    }

    public Long GetId(){
        return this.id;
    }

    public User GetName(){
        return this.name;
    }

    public User GetOwner(){
        return this.owner;
    }

    public List<Message> GetMessage(){
        return this.message;
    }

    public void SetMessage(List<Message> message){
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Chatroom chatroom = (Chatroom) obj;
        return Objects.equals(id, chatroom.id) && Objects.equals(name, chatroom.name) && Objects.equals(owner, chatroom.owner) && Objects.equals(message, chatroom.message);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, message);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name=" + name +
                ", owner=" + owner +
                ", message=" + message +
                '}';
    }
}
 