package ru.romansib.otus.serialization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatSession {
    @JsonProperty("chat_id")
    Integer chatId;
    @JsonProperty("chat_identifier")
    String chatIdentifier;
    @JsonProperty("display_name")
    String displayName;
    @JsonProperty("is_deleted")
    Integer isDeleted;
    List<Member> members;
    List<Message> messages;
}
