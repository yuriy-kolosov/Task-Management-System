package pro.sky.tms_app.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.tms_app.authentification.WithCustomUser;
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
public class UzerControllerWithAuthentificationUpdateTest {

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

    public UzerControllerWithAuthentificationUpdateTest() {
    }

    @Test
    @WithCustomUser(username = "admin@tms.ru")
    public void uzerUpdateTaskStatusTest() throws Exception {
//                                                              Подготовка
        when(uzerRepository.findByEmail(any(String.class))).thenReturn(Optional.of(ADMIN1_UZER1_ENTITY));
        when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(ADMIN1_TASK2_ENTITY));

        JSONObject taskDtoJsonObject = new JSONObject();
        taskDtoJsonObject.put("id", ADMIN1_TASK2_ID);
        taskDtoJsonObject.put("header", ADMIN1_TASK2_HEADER);
        taskDtoJsonObject.put("status", ADMIN1_TASK3_STATUS);
        taskDtoJsonObject.put("priority", ADMIN1_TASK2_PRIORITY);
        taskDtoJsonObject.put("authorId", ADMIN1_TASK2_AUTHOR_ID);
        taskDtoJsonObject.put("executorId", ADMIN1_TASK2_EXECUTOR_ID);
        taskDtoJsonObject.put("description", ADMIN1_TASK2_DESCRIPTION);
//                                                              Выполнение
        mockMvc.perform(MockMvcRequestBuilders
                        .patch(uzerUpdateTaskStatusTest_url
                                + "?taskId=" + ADMIN1_TASK2_ID
                                + "&taskStatus=" + ADMIN1_TASK3_STATUS)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(taskDtoJsonObject.toString()));
    }

}
