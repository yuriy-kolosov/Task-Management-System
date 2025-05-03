# Task Management System
## Простая система управления задачами
## Демонстрационный вариант

2025-05-03
____
Автор: Юрий Колосов

Наименование проекта в GitHub:
- Task-Management-System-Auth-Server (Authorization Server Application):
  
  https://github.com/yuriy-kolosov/Task-Management-System-Auth-Server.git
  
- **Task-Management-System (Backend Application):**

  **https://github.com/yuriy-kolosov/Task-Management-System.git**
____
## Общее описание

Простая система управления задачами. Обеспечивает создание, просмотр, редактирование и удаление задач.

Задача содержит заголовок, описание, статус, приоритет и комментарии. Задача имеет автора и исполнителя.

Данный проект является демонстрацией API
____
### Описание сервиса:
- Поддерживается аутентификация и авторизация пользователей по email и паролю
- Аутентификация доступа к API осуществляется с помощью JWT-токена
- Реализована ролевая система администратора и пользователей
- Администратор (ADMIN) имеет возможность управлять всеми задачами:
  
  -- создавать новые задачи
  
  -- назначать исполнителей задачи
  
  -- просматривать задачи
  
  -- редактировать существующие задачи
  
  -- изменять статус задачи
  
  -- изменять приоритет задачи
  
  -- оставлять комментарии к задаче
  
  -- удалять задачи
  
- Пользователь (USER) имеет возможность управлять задачами, в которых он указан как исполнитель:
  
  -- просматривать свои задачи
  
  -- изменять статус своих задач
  
  -- оставлять комментарии к своим задачам
____
## Инструкция для локального запуска проекта

### 1 Клонировать исходный код проекта из репозитория GitHub на локальный компьютер: выполнить команды:
- git clone https://github.com/yuriy-kolosov/Task-Management-System.git <имя_локального_каталога_1>
- git clone https://github.com/yuriy-kolosov/Task-Management-System-Auth-Server.git <имя_локального_каталога_2>

### 2 На локальном компютере создать тестовую базу данных PostgreSQL

### 3 В локальном каталоге <имя_локального_каталога_1>\src\main\resources отредактировать файл application.properties, указав:
- spring.datasource.url=jdbc:postgresql://localhost:5432/<название_базы_данных>
- spring.datasource.username=<имя_пользователя>
- spring.datasource.password=<пароль>

### 4 В локальном каталоге <имя_локального_каталога_2>\src\main\resources отредактировать файл application.properties, указав:
- spring.datasource.url=jdbc:postgresql://localhost:5432/<название_базы_данных>
- spring.datasource.username=<имя_пользователя>
- spring.datasource.password=<пароль>

### 5 В локальном каталоге <имя_локального_каталога_1> выполнить команду:
- mvn clean package

### 6 В локальном каталоге <имя_локального_каталога_2> выполнить команду:
- mvn clean package

### 7 В локальном каталоге <имя_локального_каталога_1> выполнить следующие команды:
- docker build --no-cache -t tms-postgres .
- docker build --no-cache -t tms-auth-server-postgres <путь_к_локальному_каталогу_2>
- docker-compose up --no-build

### 8 Для входа в систему с ролью администратора - в браузере локального компьютера выполнить следующий запрос:
- http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid%20ADMIN&redirect_uri=http://localhost:9090/tms/authorized&code_challenge=tqtVKD0f_jp-O9Z2-iCFgGtussb5Lh55nBsEFz2gPb8&code_challenge_method=S256

### 9 На странице входа ввести email и пароль администратора (используется демонстрационный вариант):
- логин: admin@tms.ru
- пароль: admin

### 10 Выполнить копирование кода авторизации со страницы, на которую произведено перенаправление, - из строки формата:
- http://localhost:9090/tms/authorized?code=<код_авторизации>

### 11 Выполнить запрос токена доступа с использованием Postman и полученного кода авторизации:
- POST http://localhost:8080/oauth2/token?client_id=client&redirect_uri=http://localhost:9090/tms/authorized&grant_type=authorization_code&code=<код_авторизации>&code_verifier=L21TeFEVuOw_lWfi8pkvgqldcjZSDJdVtT4qvJBF7Do

### 12 С использованием полученного токена произвести в Postman проверку функционирования API (роль ADMIN) на основании приведенного ниже описания (файл openapi.yaml) и/или с использованием следующих примеров запросов:
- GET http://localhost:9090/admin/user?userName=admin@tms.ru
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- POST http://localhost:9090/admin/user/task
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==
  - Body: {
   "id": 1,
   "header": "Task01",
   "status": "WAITING",
   "priority": "LOW",
   "authorId": 1,
   "executorId": 2,
   "description": "Task01 description"
	}

- POST http://localhost:9090/admin/user/task
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==
  - Body: {
   "id": 2,
   "header": "Task02",
   "status": "WAITING",
   "priority": "MIDDLE",
   "authorId": 1,
   "executorId": 3,
   "description": "Task02 description"
	}

- POST http://localhost:9090/admin/user/task
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==
  - Body: {
   "id": 3,
   "header": "Task03",
   "status": "WAITING",
   "priority": "HIGH",
   "authorId": 1,
   "executorId": 4,
   "description": "Task03 description"
	}

- GET http://localhost:9090/admin/user/tasks/all-by-pages?pageNumber=1&pageAmount=3
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- PATCH http://localhost:9090/admin/user/task
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==
  - Body: {
   "id": 1,
   "header": "Task01",
   "status": "COMPLETED",
   "priority": "LOW",
   "authorId": 1,
   "executorId": 2,
   "description": "Task01 description updated"
	}

- DELETE http://localhost:9090/admin/user/task?taskId=2
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- GET http://localhost:9090/user/tasks/all?userName=user1@tms.ru
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- GET http://localhost:9090/user/tasks/all-by-pages?userName=user1@tms.ru&pageNumber=1&pageAmount=2
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- POST http://localhost:9090/user/task/comment
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==
  - Body: {
   "id": 1,
   "taskId": 1,
   "description": "Task01 Comment01"
}

- GET http://localhost:9090/user/task-with-comments?taskId=1
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- PATCH http://localhost:9090/user/task/status?taskId=2&taskStatus=COMPLETED
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

### 13 Для входа в систему с ролью пользователя - в браузере локального компьютера выполнить следующий запрос:
- http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid%20USER&redirect_uri=http://localhost:9090/tms/authorized&code_challenge=tqtVKD0f_jp-O9Z2-iCFgGtussb5Lh55nBsEFz2gPb8&code_challenge_method=S256

### 14 На странице входа ввести email и пароль пользователя (используется демонстрационный вариант):
- логин: user1@tms.ru
- пароль: user1

### 15 Выполнить копирование кода авторизации со страницы, на которую произведено перенаправление, - из строки формата:
- http://localhost:9090/tms/authorized?code=<код_авторизации>

### 16 Выполнить запрос токена доступа с использованием Postman и полученного кода авторизации:
- POST http://localhost:8080/oauth2/token?client_id=client&redirect_uri=http://localhost:9090/tms/authorized&grant_type=authorization_code&code=<код_авторизации>&code_verifier=L21TeFEVuOw_lWfi8pkvgqldcjZSDJdVtT4qvJBF7Do

### 17 С использованием полученного токена произвести в Postman проверку функционирования API (роль USER) на основании приведенного ниже описания (файл openapi.yaml) и/или с использованием следующих примеров запросов:
- GET http://localhost:9090/user/tasks/all?userName=user1@tms.ru
- Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- GET http://localhost:9090/user/tasks/all-by-pages?userName=user1@tms.ru&pageNumber=1&pageAmount=2
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- POST http://localhost:9090/user/task/comment
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==
  - Body: {
   "id": 1,
   "taskId": 1,
   "description": "Task01 Comment01"
}

- GET http://localhost:9090/user/task-with-comments?taskId=1
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==

- PATCH http://localhost:9090/user/task/status?taskId=2&taskStatus=COMPLETED
  - Headers: Authorization: Basic Y2xpZW50OiQyYSQxMCRvMXM5MnJ3a1M0Q3hVSjZacHo5aXd1YWkybDlaSlNZNzdteEJMNzhlVUdnLmZ6UXJ4LnExRw==
____
### Описание интерфейса: файл openapi.yaml
```yaml
{
  "openapi": "3.0.1",
  "info": {
    "title": "OpenAPI definition",
    "version": "v0"
  },
  "servers": [
    {
      "url": "http://localhost:9090",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/user/task/comment": {
      "post": {
        "tags": [
          "Задачи"
        ],
        "summary": "Создать комментарий к задаче пользователя : доступно администратору или авторизованному пользователю",
        "operationId": "userAddCommentToTask",
        "requestBody": {
          "description": "Создаваемый комментарий к задаче",
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/TaskDTO"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/CommDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/CommDTO"
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/CommDTO"
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/CommDTO"
                }
              }
            }
          }
        }
      }
    },
    "/admin/user/task": {
      "post": {
        "tags": [
          "Задачи"
        ],
        "summary": "Создать новую задачу в системе : доступно администратору",
        "operationId": "addTask",
        "requestBody": {
          "description": "Создаваемая задача",
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/TaskDTO"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          }
        }
      },
      "delete": {
        "tags": [
          "Задачи"
        ],
        "summary": "Удалить задачу в системе : доступно администратору",
        "operationId": "deleteTask",
        "parameters": [
          {
            "name": "taskId",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          }
        }
      },
      "patch": {
        "tags": [
          "Задачи"
        ],
        "summary": "Редактировать задачу в системе : доступно администратору (заголовок | статус | приоритет | исполнитель | описание)",
        "operationId": "updateTask",
        "requestBody": {
          "description": "Редактируемая задача",
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/TaskDTO"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          }
        }
      }
    },
    "/user/task/status": {
      "patch": {
        "tags": [
          "Задачи"
        ],
        "summary": "Изменить статус задачи пользователя : доступно администратору или авторизованному пользователю",
        "operationId": "userUpdateTaskStatus",
        "parameters": [
          {
            "name": "taskId",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "taskStatus",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string",
              "enum": [
                "WAITING",
                "WORKING",
                "COMPLETED"
              ]
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          }
        }
      }
    },
    "/user/tasks/all": {
      "get": {
        "tags": [
          "Задачи"
        ],
        "summary": "Получить информацию о всех задачах пользователя : доступно администратору или авторизованному пользователю",
        "operationId": "getAllUserTasks",
        "parameters": [
          {
            "name": "userName",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/user/tasks/all-by-pages": {
      "get": {
        "tags": [
          "Задачи"
        ],
        "summary": "Получить постраничную информацию о всех задачах пользователя : доступно администратору или авторизованному пользователю",
        "operationId": "getAllUserTasksByPages",
        "parameters": [
          {
            "name": "userName",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "pageNumber",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int32"
            }
          },
          {
            "name": "pageAmount",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int32"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/user/task-with-comments": {
      "get": {
        "tags": [
          "Задачи"
        ],
        "summary": "Получить информацию о задаче пользователя и комментариях к ней : доступно администратору или авторизованному пользователю",
        "operationId": "userGetTaskWithComments",
        "parameters": [
          {
            "name": "taskId",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskWithCommDTO"
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskWithCommDTO"
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/TaskWithCommDTO"
                }
              }
            }
          }
        }
      }
    },
    "/admin/user": {
      "get": {
        "tags": [
          "Пользователи"
        ],
        "summary": "Получить информацию о любом пользователе : доступно администратору",
        "operationId": "userGetInfo",
        "parameters": [
          {
            "name": "userName",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/UzerDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UzerDTO"
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UzerDTO"
                }
              }
            }
          },
          "404": {
            "description": "Not Found",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UzerDTO"
                }
              }
            }
          }
        }
      }
    },
    "/admin/user/tasks/all-by-pages": {
      "get": {
        "tags": [
          "Задачи"
        ],
        "summary": "Получить информацию о всех задачах в системе : доступно администратору",
        "operationId": "getAllTasksByPages",
        "parameters": [
          {
            "name": "pageNumber",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int32"
            }
          },
          {
            "name": "pageAmount",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int32"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/TaskDTO"
                }
              }
            }
          },
          "401": {
            "description": "Unauthorized",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          },
          "403": {
            "description": "Forbidden",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/TaskDTO"
                  }
                }
              }
            }
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "TaskDTO": {
        "required": [
          "authorId",
          "description",
          "executorId",
          "header",
          "id",
          "priority",
          "status"
        ],
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "header": {
            "type": "string"
          },
          "status": {
            "type": "string",
            "enum": [
              "WAITING",
              "WORKING",
              "COMPLETED"
            ]
          },
          "priority": {
            "type": "string",
            "enum": [
              "HIGH",
              "MIDDLE",
              "LOW"
            ]
          },
          "authorId": {
            "type": "integer",
            "format": "int64"
          },
          "executorId": {
            "type": "integer",
            "format": "int64"
          },
          "description": {
            "type": "string"
          }
        }
      },
      "CommDTO": {
        "required": [
          "description",
          "id",
          "taskId"
        ],
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "taskId": {
            "type": "integer",
            "format": "int64"
          },
          "description": {
            "type": "string"
          }
        }
      },
      "TaskWithCommDTO": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "header": {
            "type": "string"
          },
          "status": {
            "type": "string",
            "enum": [
              "WAITING",
              "WORKING",
              "COMPLETED"
            ]
          },
          "priority": {
            "type": "string",
            "enum": [
              "HIGH",
              "MIDDLE",
              "LOW"
            ]
          },
          "authorId": {
            "type": "integer",
            "format": "int64"
          },
          "executorId": {
            "type": "integer",
            "format": "int64"
          },
          "description": {
            "type": "string"
          },
          "commDtoList": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/CommDTO"
            }
          }
        }
      },
      "UzerDTO": {
        "required": [
          "email",
          "id"
        ],
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "email": {
            "pattern": "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            "type": "string"
          },
          "role": {
            "type": "string",
            "enum": [
              "ROLE_ADMIN",
              "ROLE_USER"
            ]
          }
        }
      }
    }
  }
}
```
____
### Технологический стек:
- Java 17
- Maven
- Java Spring Boot Security
- WEB RESTful
- PostgreSQL
- Hibernate
- Liquibase
- Lombok
- Mapstruct
____
