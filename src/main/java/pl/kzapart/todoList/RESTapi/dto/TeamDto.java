package pl.kzapart.todoList.RESTapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto {
    private Long Id;
    private String name;
    private String description;
    private Integer numberOfTasks;

}
