package ru.urfu.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Chat chat;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Instant sendTime = Instant.now();

    public Message() {
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setText(String text) {
        this.text = text;
    }
}
