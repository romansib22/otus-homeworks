package ru.romansib.otus.serialization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class Result {
    @JsonProperty("chat_identifier")
    private String chatIdentifier;
    private String last;
    @JsonProperty("belong_number")
    private String belongNumber;
    @JsonProperty("send_date")
    private String sendDate;
    private String text;
    private transient Long date;
}
