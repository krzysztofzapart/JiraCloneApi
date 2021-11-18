package pl.kzapart.todoList.RESTapi.model.dict;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum TaskPriority {

    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");

    private String status;

    private TaskPriority(String code) {
        this.status =code;
    }

    @JsonCreator
    public static TaskPriority decode(final String code) {
        return Stream.of(TaskPriority.values()).filter(targetEnum -> targetEnum.getStatus().equals(code)).findFirst().orElse(null);
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

}
