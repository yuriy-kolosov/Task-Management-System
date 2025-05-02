package pro.sky.tms_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "header")
    private String header;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Column(name = "priority")
    @Enumerated(value = EnumType.STRING)
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UzerEntity author;

    @Column(name = "executor_id")
    private Long executorId;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "task")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CommEntity> commList;

    public TaskEntity() {
    }

    public TaskEntity(Long id, String header, TaskStatus status, TaskPriority priority
            , UzerEntity author, Long executorId, String description) {
        this.id = id;
        this.header = header;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executorId = executorId;
        this.description = description;
    }

    public TaskEntity(Long id, String header, TaskStatus status, TaskPriority priority
            , UzerEntity author, Long executorId, String description, List<CommEntity> commList) {
        this.id = id;
        this.header = header;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executorId = executorId;
        this.description = description;
        this.commList = commList;
    }

}
