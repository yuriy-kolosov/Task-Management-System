package pro.sky.tms_app.constants;

import pro.sky.tms_app.entity.*;

import java.util.List;

import static pro.sky.tms_app.entity.UzerRole.ROLE_ADMIN;
import static pro.sky.tms_app.entity.UzerRole.ROLE_USER;

public class AdminServiceImplTestConstants {

    private AdminServiceImplTestConstants() {
    }

    public static final String adminFindUserInfoTest_url = "http://localhost:9090/admin/user";
    public static final String adminFindAllTasksByPagesTest_url = "http://localhost:9090/admin/user/tasks/all-by-pages";
    public static final String adminAddTaskTest_url = "http://localhost:9090/admin/user/task";
    public static final String adminUpdateTaskTest_url = "http://localhost:9090/admin/user/task";

    public static final String uzerFindAllUserTasksTest_url = "http://localhost:9090/user/tasks/all";
    public static final String uzerFindAllUserTasksByPagesTest_url = "http://localhost:9090/user/tasks/all-by-pages";
    public static final String uzerFindUserTaskWithCommentsTest_url = "http://localhost:9090/user/task-with-comments";
    public static final String uzerAddCommentToTaskTest_url = "http://localhost:9090/user/task/comment";
    public static final String uzerUpdateTaskStatusTest_url = "http://localhost:9090/user//task/status";

    public static final Long ADMIN1_USER1_ID = 1L;
    public static final Long ADMIN1_USER2_ID = 2L;
    public static final Long ADMIN1_USER3_ID = 3L;

    public static final String ADMIN1_USER1_EMAIL = "admin@tms.ru";
    public static final String ADMIN1_USER2_EMAIL = "user1@tms.ru";
    public static final String ADMIN1_USER3_EMAIL = "user2@tms.ru";

    public static final String ADMIN1_USER1_PASSWORD = "admin";
    public static final String ADMIN1_USER2_PASSWORD = "user1";
    public static final String ADMIN1_USER3_PASSWORD = "user2";

    public static final UzerRole ADMIN1_UZER1_ROLE = ROLE_ADMIN;
    public static final UzerRole ADMIN1_UZER2_ROLE = ROLE_USER;
    public static final UzerRole ADMIN1_UZER3_ROLE = ROLE_USER;

    public static final UzerEntity ADMIN1_UZER1_ENTITY = new UzerEntity(ADMIN1_USER1_ID, ADMIN1_USER1_EMAIL
            , ADMIN1_USER1_PASSWORD, ADMIN1_UZER1_ROLE);

    public static final UzerEntity ADMIN1_UZER2_ENTITY = new UzerEntity(ADMIN1_USER2_ID, ADMIN1_USER2_EMAIL
            , ADMIN1_USER2_PASSWORD, ADMIN1_UZER2_ROLE);

    public static final int ADMIN1_PAGE_NUMBER = 1;
    public static final int ADMIN1_PAGE_AMOUNT = 3;

    public static final Long ADMIN1_TASK1_ID = 1L;
    public static final Long ADMIN1_TASK2_ID = 2L;
    public static final Long ADMIN1_TASK3_ID = 3L;

    public static final String ADMIN1_TASK1_HEADER = "Task1 header";
    public static final String ADMIN1_TASK2_HEADER = "Task2 header";
    public static final String ADMIN1_TASK3_HEADER = "Task3 header";

    public static final TaskStatus ADMIN1_TASK1_STATUS = TaskStatus.WAITING;
    public static final TaskStatus ADMIN1_TASK2_STATUS = TaskStatus.WORKING;
    public static final TaskStatus ADMIN1_TASK3_STATUS = TaskStatus.COMPLETED;

    public static final TaskPriority ADMIN1_TASK1_PRIORITY = TaskPriority.MIDDLE;
    public static final TaskPriority ADMIN1_TASK2_PRIORITY = TaskPriority.HIGH;
    public static final TaskPriority ADMIN1_TASK3_PRIORITY = TaskPriority.LOW;

    public static final Long ADMIN1_TASK1_AUTHOR_ID = 1L;
    public static final Long ADMIN1_TASK2_AUTHOR_ID = 1L;
    public static final Long ADMIN1_TASK3_AUTHOR_ID = 1L;

    public static final Long ADMIN1_TASK1_EXECUTOR_ID = 2L;
    public static final Long ADMIN1_TASK2_EXECUTOR_ID = 3L;
    public static final Long ADMIN1_TASK3_EXECUTOR_ID = 2L;

    public static final String ADMIN1_TASK1_DESCRIPTION = "Task1 description";
    public static final String ADMIN1_TASK2_DESCRIPTION = "Task2 description";
    public static final String ADMIN1_TASK3_DESCRIPTION = "Task3 description";

    public static final Long ADMIN1_COMM1_ID = 1L;
    public static final Long ADMIN1_COMM2_ID = 2L;
    public static final Long ADMIN1_COMM3_ID = 3L;

    public static final Long ADMIN1_COMM1_TASK_ID = 1L;

    public static final String ADMIN1_COMM1_DESCRIPTION = "Task1 Comment 1";
    public static final String ADMIN1_COMM2_DESCRIPTION = "Task1 Comment 2";
    public static final String ADMIN1_COMM3_DESCRIPTION = "Task1 Comment 3";

    public static final TaskEntity ADMIN1_TASK1_ENTITY = new TaskEntity(ADMIN1_TASK1_ID, ADMIN1_TASK1_HEADER
            , ADMIN1_TASK1_STATUS, ADMIN1_TASK1_PRIORITY, ADMIN1_UZER1_ENTITY, ADMIN1_TASK1_EXECUTOR_ID
            , ADMIN1_TASK1_DESCRIPTION);

    public static final TaskEntity ADMIN1_TASK2_ENTITY = new TaskEntity(ADMIN1_TASK2_ID, ADMIN1_TASK2_HEADER
            , ADMIN1_TASK2_STATUS, ADMIN1_TASK2_PRIORITY, ADMIN1_UZER1_ENTITY, ADMIN1_TASK2_EXECUTOR_ID
            , ADMIN1_TASK2_DESCRIPTION);

    public static final TaskEntity ADMIN1_TASK3_ENTITY = new TaskEntity(ADMIN1_TASK3_ID, ADMIN1_TASK3_HEADER
            , ADMIN1_TASK3_STATUS, ADMIN1_TASK3_PRIORITY, ADMIN1_UZER1_ENTITY, ADMIN1_TASK3_EXECUTOR_ID
            , ADMIN1_TASK3_DESCRIPTION);

    public static final List<TaskEntity> ADMIN1_TASK_ENTITY_LIST = List.of(ADMIN1_TASK1_ENTITY, ADMIN1_TASK2_ENTITY
            , ADMIN1_TASK3_ENTITY);

    public static final CommEntity ADMIN1_COMM1_ENTITY = new CommEntity(ADMIN1_COMM1_ID, ADMIN1_TASK1_ENTITY
            , ADMIN1_COMM1_DESCRIPTION);

    public static final CommEntity ADMIN1_COMM2_ENTITY = new CommEntity(ADMIN1_COMM2_ID, ADMIN1_TASK1_ENTITY
            , ADMIN1_COMM2_DESCRIPTION);

    public static final CommEntity ADMIN1_COMM3_ENTITY = new CommEntity(ADMIN1_COMM3_ID, ADMIN1_TASK1_ENTITY
            , ADMIN1_COMM3_DESCRIPTION);

    public static final List<CommEntity> ADMIN1_COMM1_ENTITY_LIST = List.of(ADMIN1_COMM1_ENTITY, ADMIN1_COMM2_ENTITY
            , ADMIN1_COMM3_ENTITY);

}
