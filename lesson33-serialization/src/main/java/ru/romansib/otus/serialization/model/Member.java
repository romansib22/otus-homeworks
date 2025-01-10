package ru.romansib.otus.serialization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Member {
    String first;
    @JsonProperty("handle_id")
    Integer handleId;
    @JsonProperty("image_path")
    String imagePath;
    String last;
    String middle;
    @JsonProperty("phone_number")
    String phoneNumber;
    String service;
    @JsonProperty("thumb_path")
    String thumbPath;
}
