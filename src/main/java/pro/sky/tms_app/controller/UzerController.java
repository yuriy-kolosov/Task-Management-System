package pro.sky.tms_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pro.sky.tms_app.dto.CommDTO;
import pro.sky.tms_app.dto.TaskDTO;
import pro.sky.tms_app.dto.TaskWithCommDTO;
import pro.sky.tms_app.entity.TaskStatus;
import pro.sky.tms_app.service.UzerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UzerController {

    private final UzerService uzerService;

    @Operation(summary = "Получить информацию о всех задачах пользователя : доступно администратору или авторизованному пользователю",
            tags = "Задачи",
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
    @GetMapping("/tasks/all")
    public ResponseEntity<List<TaskDTO>> getAllUserTasks(@RequestParam String userName, Authentication authentication) {

        List<TaskDTO> taskDTOList = uzerService.findAllUserTasks(userName, authentication);

        return ResponseEntity.status(HttpStatus.OK).body(taskDTOList);

    }

    @Operation(summary = "Получить постраничную информацию о всех задачах пользователя : доступно администратору или авторизованному пользователю",
            tags = "Задачи",
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
    @GetMapping("/tasks/all-by-pages")
    public ResponseEntity<List<TaskDTO>> getAllUserTasksByPages(@RequestParam String userName
            , @RequestParam int pageNumber
            , @RequestParam int pageAmount
            , Authentication authentication) {

        List<TaskDTO> taskDTOList = uzerService.findAllUserTasksByPages(userName, pageNumber, pageAmount, authentication);

        return ResponseEntity.status(HttpStatus.OK).body(taskDTOList);

    }

    @Operation(summary = "Получить информацию о задаче пользователя и комментариях к ней : доступно администратору или авторизованному пользователю",
            tags = "Задачи",
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
    @GetMapping("/task-with-comments")
    public ResponseEntity<TaskWithCommDTO> userGetTaskWithComments(@RequestParam Long taskId
            , Authentication authentication) {

        TaskWithCommDTO taskWithComments = uzerService.findUserTaskWithComments(taskId, authentication);

        return ResponseEntity.status(HttpStatus.OK).body(taskWithComments);
    }

    @Operation(summary = "Создать комментарий к задаче пользователя : доступно администратору или авторизованному пользователю",
            tags = "Задачи",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Создаваемый комментарий к задаче",
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
                                    schema = @Schema(implementation = CommDTO.class)
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
    @PostMapping("/task/comment")
    public ResponseEntity<CommDTO> userAddCommentToTask(@Valid @RequestBody CommDTO commDto, Authentication authentication) {

        CommDTO commDTO = uzerService.addCommentToTask(commDto, authentication);

        return ResponseEntity.status(HttpStatus.OK).body(commDTO);

    }

    @Operation(summary = "Изменить статус задачи пользователя : доступно администратору или авторизованному пользователю", tags = "Задачи",
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
    @PatchMapping("/task/status")
    public ResponseEntity<TaskDTO> userUpdateTaskStatus(@RequestParam Long taskId
            , @RequestParam TaskStatus taskStatus
            , Authentication authentication) {

        TaskDTO taskDTO = uzerService.updateTaskStatus(taskId, taskStatus, authentication);

        return ResponseEntity.status(HttpStatus.OK).body(taskDTO);

    }

}
