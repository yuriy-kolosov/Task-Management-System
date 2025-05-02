package pro.sky.tms_app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class CommEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @Column(name = "description")
    private String description;

    public CommEntity() {
    }

    public CommEntity(Long id, TaskEntity task, String description) {
        this.id = id;
        this.task = task;
        this.description = description;
    }

}
