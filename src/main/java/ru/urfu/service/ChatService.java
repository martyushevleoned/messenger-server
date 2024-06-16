package ru.urfu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urfu.model.dto.ChatDto;
import ru.urfu.model.entity.Chat;
import ru.urfu.model.entity.User;
import ru.urfu.model.repository.ChatRepository;
import ru.urfu.model.repository.UserRepository;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public void crateChat(Principal principal, String chatName) throws RuntimeException {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Chat chat = new Chat();
        chat.setChatName(chatName);
        chat.setCreator(user);
        chat.setMembers(Set.of(user));
        chatRepository.save(chat);
    }

    public List<ChatDto> getChats(Principal principal) throws RuntimeException {
        return chatRepository.findAllChatsByUsername(principal.getName()).stream()
                .sorted(Comparator.comparing(Chat::getCreactionTime))
                .map(chat -> new ChatDto(chat.getId(), chat.getChatName())).toList();
    }
}
