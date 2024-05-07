package edu.school21.chat.models;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime localDateTime;
    

    public Message(Long id, User author, Chatroom room, String text, LocalDateTime localDateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public Long GetId(){
        return this.id;
    }

    public void SetId(Long id){
        this.id = id;
    }

    public User GetAuthor(){
        return this.author;
    }

    public String GetText(){
        return this.text;
    }

    public Chatroom GetRoom(){
        return this.room;
    }

    public LocalDateTime GetDateTime(){
        return this.localDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Message message = (Message) obj;
        return Objects.equals(id, message.id) && Objects.equals(author, message.author) && Objects.equals(room, message.room) && Objects.equals(text, message.text) && Objects.equals(localDateTime, message.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, localDateTime);
    }
    
    @Override
    public String toString() {
        return "Message{\n" +
                "\tid=" + id +
                ", \n\tauthor=" + author +
                ", \n\troom=" + room +
                ", \n\ttext='" + text + '\'' +
                ", \n\tdateTime=" + localDateTime +
                "\n}";
    }
 }
 