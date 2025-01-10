package ru.romansib.otus.serialization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {
    @JsonProperty("ROWID")
    String rowid;
    String attributedBody;
    @JsonProperty("belong_number")
    String belongNumber;
    Long date;
    @JsonProperty("date_read")
    Long dateRead;
    String guid;
    @JsonProperty("handle_id")
    Integer handleId;
    @JsonProperty("has_dd_results")
    Integer hasDdResults;
    @JsonProperty("is_deleted")
    Integer isDeleted;
    @JsonProperty("is_from_me")
    Integer isFromMe;
    @JsonProperty("send_date")
    String sendDate;
    @JsonProperty("send_status")
    Integer sendStatus;
    String service;
    String text;

}
