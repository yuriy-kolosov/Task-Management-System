package pro.sky.tms_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pro.sky.tms_app.dto.TaskDTO;
import pro.sky.tms_app.dto.UzerDTO;
import pro.sky.tms_app.service.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Operation(summary = "Получить информацию о любом пользователе : доступно администратору", tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UzerDTO.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            })
    @GetMapping("/user")
    public ResponseEntity<UzerDTO> userGetInfo(@RequestParam String userName) {

        UzerDTO uzerDTO = adminService.findUserInfo(userName);

        return ResponseEntity.status(HttpStatus.OK).body(uzerDTO);

    }

    @Operation(summary = "Получить информацию о всех задачах в системе : доступно администратору", tags = "Задачи",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @GetMapping("/user/tasks/all-by-pages")
    public ResponseEntity<List<TaskDTO>> getAllTasksByPages(@RequestParam int pageNumber, @RequestParam int pageAmount
            , Authentication authentication) {

        List<TaskDTO> taskDTOList = adminService.findAllTasksByPages(pageNumber, pageAmount, authentication);

        return ResponseEntity.status(HttpStatus.OK).body(taskDTOList);

    }

    @Operation(summary = "Создать новую задачу в системе : доступно администратору", tags = "Задачи",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемая задача",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            })
    @PostMapping("/user/task")
    public ResponseEntity<TaskDTO> addTask(@Valid @RequestBody TaskDTO taskDTO, Authentication authentication) {
        logger.debug("\"POST\" addTask method was invoke...");
        return ResponseEntity.status(HttpStatus.OK).body(adminService.saveTask(taskDTO, authentication));
    }

    @Operation(summary = "Редактировать задачу в системе : доступно администратору (заголовок | статус | приоритет | исполнитель | описание)",
            tags = "Задачи",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемая задача",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TaskDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            })
    @PatchMapping("/user/task")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO, Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateTask(taskDTO, authentication));

    }

    @Operation(summary = "Удалить задачу в системе : доступно администратору", tags = "Задачи",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            })
    @DeleteMapping("/user/task")
    public ResponseEntity<TaskDTO> deleteTask(@RequestParam Long taskId, Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(adminService.deleteTask(taskId, authentication));

    }

}
