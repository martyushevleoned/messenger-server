package ru.urfu.model.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chatName;

    @ManyToOne
    private User creator;

    @Column(nullable = false)
    private Instant creactionTime = Instant.now();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_chats",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members;

    public Chat() {
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Instant getCreactionTime() {
        return creactionTime;
    }

    public Long getId() {
        return id;
    }

    public String getChatName() {
        return chatName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
