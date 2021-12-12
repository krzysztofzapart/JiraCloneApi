package pl.kzapart.JiraClone.model.dict;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum TaskStatus {

    OPEN("OPEN"),
    IN_PROGRES("IN_PROGRES"),
    DONE("DONE");

    private String status;

    private TaskStatus(String code) {
        this.status =code;
    }

    @JsonCreator
    public static TaskStatus decode(final String code) {
        return Stream.of(TaskStatus.values()).filter(targetEnum -> targetEnum.status.equals(code)).findFirst().orElse(null);
    }

    @JsonValue
    public String getStatus() {
        return status;
    }


}
