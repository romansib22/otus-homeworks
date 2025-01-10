package ru.romansib.otus.serialization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Sms {
    @JsonProperty("chat_sessions")
    List<ChatSession> chatSessions;
}
