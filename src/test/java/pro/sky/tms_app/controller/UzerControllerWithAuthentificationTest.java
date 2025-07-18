package pro.sky.tms_app.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.tms_app.authentification.WithCustomUser;
import pro.sky.tms_app.entity.CommEntity;
import pro.sky.tms_app.repository.CommRepository;
import pro.sky.tms_app.repository.TaskRepository;
import pro.sky.tms_app.repository.UzerRepository;
import pro.sky.tms_app.service.impl.UzerServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.tms_app.constants.AdminServiceImplTestConstants.*;

@WebMvcTest(UzerController.class)
public class UzerControllerWithAuthentificationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UzerRepository uzerRepository;
    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private CommRepository commRepository;

    @SpyBean
    private UzerServiceImpl uzerServiceImpl;

    public UzerControllerWithAuthentificationTest() {
    }

    @Test
    @WithCustomUser(username = "user1@tms.ru")
    public void uzerFindAllUserTasksTest() throws Exception {
//                                                              Подготовка
        when(uzerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(ADMIN1_UZER1_ENTITY));
        when(taskRepository.findAll()).thenReturn(ADMIN1_TASK_ENTITY_LIST);

        JSONObject taskDtoJsonObject1 = new JSONObject();
        taskDtoJsonObject1.put("id", ADMIN1_TASK1_ID);
        taskDtoJsonObject1.put("header", ADMIN1_TASK1_HEADER);
        taskDtoJsonObject1.put("status", ADMIN1_TASK1_STATUS);
        taskDtoJsonObject1.put("priority", ADMIN1_TASK1_PRIORITY);
        taskDtoJsonObject1.put("authorId", ADMIN1_TASK1_AUTHOR_ID);
        taskDtoJsonObject1.put("executorId", ADMIN1_TASK1_EXECUTOR_ID);
        taskDtoJsonObject1.put("description", ADMIN1_TASK1_DESCRIPTION);

        JSONObject taskDtoJsonObject2 = new JSONObject();
        taskDtoJsonObject2.put("id", ADMIN1_TASK2_ID);
        taskDtoJsonObject2.put("header", ADMIN1_TASK2_HEADER);
        taskDtoJsonObject2.put("status", ADMIN1_TASK2_STATUS);
        taskDtoJsonObject2.put("priority", ADMIN1_TASK2_PRIORITY);
        taskDtoJsonObject2.put("authorId", ADMIN1_TASK2_AUTHOR_ID);
        taskDtoJsonObject2.put("executorId", ADMIN1_TASK2_EXECUTOR_ID);
        taskDtoJsonObject2.put("description", ADMIN1_TASK2_DESCRIPTION);

        JSONObject taskDtoJsonObject3 = new JSONObject();
        taskDtoJsonObject3.put("id", ADMIN1_TASK3_ID);
        taskDtoJsonObject3.put("header", ADMIN1_TASK3_HEADER);
        taskDtoJsonObject3.put("status", ADMIN1_TASK3_STATUS);
        taskDtoJsonObject3.put("priority", ADMIN1_TASK3_PRIORITY);
        taskDtoJsonObject3.put("authorId", ADMIN1_TASK3_AUTHOR_ID);
        taskDtoJsonObject3.put("executorId", ADMIN1_TASK3_EXECUTOR_ID);
        taskDtoJsonObject3.put("description", ADMIN1_TASK3_DESCRIPTION);

        JSONArray taskDtoJsonArray = new JSONArray();
        taskDtoJsonArray.put(taskDtoJsonObject1);
        taskDtoJsonArray.put(taskDtoJsonObject2);
        taskDtoJsonArray.put(taskDtoJsonObject3);
//                                                              Выполнение
        mockMvc.perform(MockMvcRequestBuilders
                        .get(uzerFindAllUserTasksTest_url
                                + "?userName=" + ADMIN1_USER1_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(taskDtoJsonArray.toString()));
    }

    @Test
    @WithCustomUser(username = "admin@tms.ru")
    public void uzerFindAllUserTasksByPagesTest() throws Exception {
//                                                              Подготовка
        when(uzerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(ADMIN1_UZER1_ENTITY));

        PageRequest taskPages = PageRequest.of(ADMIN1_PAGE_NUMBER - 1, ADMIN1_PAGE_AMOUNT);
        when(taskRepository.findPages(taskPages)).thenReturn(ADMIN1_TASK_ENTITY_LIST);

        JSONObject taskDtoJsonObject1 = new JSONObject();
        taskDtoJsonObject1.put("id", ADMIN1_TASK1_ID);
        taskDtoJsonObject1.put("header", ADMIN1_TASK1_HEADER);
        taskDtoJsonObject1.put("status", ADMIN1_TASK1_STATUS);
        taskDtoJsonObject1.put("priority", ADMIN1_TASK1_PRIORITY);
        taskDtoJsonObject1.put("authorId", ADMIN1_TASK1_AUTHOR_ID);
        taskDtoJsonObject1.put("executorId", ADMIN1_TASK1_EXECUTOR_ID);
        taskDtoJsonObject1.put("description", ADMIN1_TASK1_DESCRIPTION);

        JSONObject taskDtoJsonObject2 = new JSONObject();
        taskDtoJsonObject2.put("id", ADMIN1_TASK2_ID);
        taskDtoJsonObject2.put("header", ADMIN1_TASK2_HEADER);
        taskDtoJsonObject2.put("status", ADMIN1_TASK2_STATUS);
        taskDtoJsonObject2.put("priority", ADMIN1_TASK2_PRIORITY);
        taskDtoJsonObject2.put("authorId", ADMIN1_TASK2_AUTHOR_ID);
        taskDtoJsonObject2.put("executorId", ADMIN1_TASK2_EXECUTOR_ID);
        taskDtoJsonObject2.put("description", ADMIN1_TASK2_DESCRIPTION);

        JSONObject taskDtoJsonObject3 = new JSONObject();
        taskDtoJsonObject3.put("id", ADMIN1_TASK3_ID);
        taskDtoJsonObject3.put("header", ADMIN1_TASK3_HEADER);
        taskDtoJsonObject3.put("status", ADMIN1_TASK3_STATUS);
        taskDtoJsonObject3.put("priority", ADMIN1_TASK3_PRIORITY);
        taskDtoJsonObject3.put("authorId", ADMIN1_TASK3_AUTHOR_ID);
        taskDtoJsonObject3.put("executorId", ADMIN1_TASK3_EXECUTOR_ID);
        taskDtoJsonObject3.put("description", ADMIN1_TASK3_DESCRIPTION);

        JSONArray taskDtoJsonArray = new JSONArray();
        taskDtoJsonArray.put(taskDtoJsonObject1);
        taskDtoJsonArray.put(taskDtoJsonObject2);
        taskDtoJsonArray.put(taskDtoJsonObject3);
//                                                              Выполнение
        mockMvc.perform(MockMvcRequestBuilders
                        .get(uzerFindAllUserTasksByPagesTest_url
                                + "?userName=" + ADMIN1_USER1_EMAIL
                                + "&pageNumber=" + ADMIN1_PAGE_NUMBER
                                + "&pageAmount=" + ADMIN1_PAGE_AMOUNT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(taskDtoJsonArray.toString()));
    }

    @Test
    @WithCustomUser(username = "admin@tms.ru")
    public void uzerFindUserTaskWithCommentsTest() throws Exception {
//                                                              Подготовка
        when(uzerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(ADMIN1_UZER1_ENTITY));
        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(ADMIN1_TASK1_ENTITY));
        when(commRepository.findAll()).thenReturn(ADMIN1_COMM1_ENTITY_LIST);

        JSONObject commDtoJsonObject1 = new JSONObject();
        commDtoJsonObject1.put("id", ADMIN1_COMM1_ID);
        commDtoJsonObject1.put("taskId", ADMIN1_COMM1_TASK_ID);
        commDtoJsonObject1.put("description", ADMIN1_COMM1_DESCRIPTION);

        JSONObject commDtoJsonObject2 = new JSONObject();
        commDtoJsonObject2.put("id", ADMIN1_COMM2_ID);
        commDtoJsonObject2.put("taskId", ADMIN1_TASK1_ID);
        commDtoJsonObject2.put("description", ADMIN1_COMM2_DESCRIPTION);

        JSONObject commDtoJsonObject3 = new JSONObject();
        commDtoJsonObject3.put("id", ADMIN1_COMM3_ID);
        commDtoJsonObject3.put("taskId", ADMIN1_TASK1_ID);
        commDtoJsonObject3.put("description", ADMIN1_COMM3_DESCRIPTION);

        JSONArray commDtoJsonArray = new JSONArray();
        commDtoJsonArray.put(commDtoJsonObject1);
        commDtoJsonArray.put(commDtoJsonObject2);
        commDtoJsonArray.put(commDtoJsonObject3);

        JSONObject taskWithCommDTOJsonObject = new JSONObject();
        taskWithCommDTOJsonObject.put("id", ADMIN1_TASK1_ID);
        taskWithCommDTOJsonObject.put("header", ADMIN1_TASK1_HEADER);
        taskWithCommDTOJsonObject.put("status", ADMIN1_TASK1_STATUS);
        taskWithCommDTOJsonObject.put("priority", ADMIN1_TASK1_PRIORITY);
        taskWithCommDTOJsonObject.put("authorId", ADMIN1_TASK1_AUTHOR_ID);
        taskWithCommDTOJsonObject.put("executorId", ADMIN1_TASK1_EXECUTOR_ID);
        taskWithCommDTOJsonObject.put("description", ADMIN1_TASK1_DESCRIPTION);
        taskWithCommDTOJsonObject.put("commDtoList", commDtoJsonArray);
//                                                              Выполнение
        mockMvc.perform(MockMvcRequestBuilders
                        .get(uzerFindUserTaskWithCommentsTest_url + "?taskId=" + ADMIN1_TASK1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(taskWithCommDTOJsonObject.toString()));
    }

    @Test
    @WithCustomUser(username = "admin@tms.ru")
    public void uzerAddCommentToTaskTest() throws Exception {
//                                                              Подготовка
        when(uzerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(ADMIN1_UZER1_ENTITY));
        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(ADMIN1_TASK1_ENTITY));
        when(commRepository.save(any(CommEntity.class))).thenReturn(ADMIN1_COMM1_ENTITY);

        JSONObject commDtoJsonObject = new JSONObject();
        commDtoJsonObject.put("id", ADMIN1_COMM1_ID);
        commDtoJsonObject.put("taskId", ADMIN1_COMM1_TASK_ID);
        commDtoJsonObject.put("description", ADMIN1_COMM1_DESCRIPTION);
//                                                              Выполнение
        mockMvc.perform(MockMvcRequestBuilders
                        .post(uzerAddCommentToTaskTest_url)
                        .with(csrf())
                        .content(commDtoJsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(commDtoJsonObject.toString()));
    }

}
