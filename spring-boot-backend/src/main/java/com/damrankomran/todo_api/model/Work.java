package com.damrankomran.todo_api.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Work")
public class Work {

    @Id
    private Long workID;

    private String name;

    private String status;

    private int index;

}
