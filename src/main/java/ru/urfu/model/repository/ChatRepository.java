package ru.urfu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.urfu.model.entity.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("""
            Select u.chats
            FROM Chat c, User u
            Where u.username = ?1
            """)
    List<Chat> findAllChatsByUsername(String username);
}
