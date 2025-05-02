package pro.sky.tms_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.tms_app.repository.TaskRepository;
import pro.sky.tms_app.repository.UzerRepository;
import pro.sky.tms_app.service.impl.AdminServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.tms_app.constants.AdminServiceImplTestConstants.*;
import static pro.sky.tms_app.constants.AdminServiceImplTestConstants.ADMIN1_USER1_EMAIL;
import static pro.sky.tms_app.entity.UzerRole.ROLE_ADMIN;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SecurityFilterChain securityFilterChain;
    @MockBean
    HttpServletRequest httpServletRequest;

    @MockBean
    private UzerRepository uzerRepository;
    @MockBean
    private TaskRepository taskRepository;
    @SpyBean
    private AdminServiceImpl adminServiceImpl;

    private AdminControllerTest() {
    }

    @Test
    public void adminFindUserInfoTest() throws Exception {
//                                                              Подготовка
        when(securityFilterChain.matches(httpServletRequest)).thenReturn(true);
        when(uzerRepository.findByEmail(ADMIN1_USER1_EMAIL)).thenReturn(Optional.of(ADMIN1_UZER1_ENTITY));

        JSONObject uzerDtoJsonObject = new JSONObject();
        uzerDtoJsonObject.put("id", ADMIN1_USER1_ID);
        uzerDtoJsonObject.put("email", ADMIN1_USER1_EMAIL);
        uzerDtoJsonObject.put("role", ROLE_ADMIN);
//                                                              Выполнение
        mockMvc.perform(MockMvcRequestBuilders
                        .get(adminFindUserInfoTest_url + "?userName=" + ADMIN1_USER1_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(uzerDtoJsonObject.toString()));
    }

}
