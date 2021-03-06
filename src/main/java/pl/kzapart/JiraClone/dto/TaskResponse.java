package pl.kzapart.JiraClone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kzapart.JiraClone.model.dict.TaskPriority;
import pl.kzapart.JiraClone.model.dict.TaskStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private java.lang.String taskName;
    private java.lang.String url;
    private java.lang.String description;
    private java.lang.String userName;
    private java.lang.String teamName;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private String createdBy;
}
