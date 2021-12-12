package pl.kzapart.JiraClone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto {
    private Long teamId;
    private String name;
    private String description;
    private String teamOwner;
    private Integer numberOfTasks;



}
