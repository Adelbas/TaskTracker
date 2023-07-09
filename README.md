# REST task list service
This project is simple tasktracker with implementation of Spring Security using JSON Web Tokens (JWT) and role-based authorization.
## Task tracker features 
- View the list of tasks for today/week/month with filtering by completion
- Create task
- Update task
- Setting/removing completion mark
- Delete task

## Security features
- Registration and login with JWT authentication
- Password encryption using BCrypt
- Role-based authorization
- Custom access denied handler
- Custom exception handler

## Stack
- Java
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- JSON Web Tokens (JWT)
- BCrypt
- Lombok
- Postman (for testing)

## Endpoints
### Anyone
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `POST`   | `/api/register`                          | Register new user                        |
| `POST`   | `/api/auth`                              | Authenticate user                        |

### User
| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`   | `/api/tasks`                          | Get current user tasks                        |
| `GET`   | `/api/tasks?interval=today`                          | Get current user tasks for a day                         |
| `GET`   | `/api/tasks?interval=today&completed=true`                          | Get current user completed tasks for a day                         |
| `GET`   | `/api/tasks?interval=week&completed=false`                          | Get current user uncompleted tasks for a week                         |
| `GET`   | `/api/tasks?interval=month&completed=false`                          | Get current user uncompleted tasks for a month                         |
| `GET`   | `/api/tasks/1`                              | Get current user task with id #1                 |
| `POST`   | `/api/tasks/create`                          | Create new task                        |
| `DELETE`   | `/api/tasks/delete/1`                              | Delete current user task with id #1                 |
| `PUT`   | `/api/tasks/update/1`                          | Update current user task with id #1                        |
| `DELETE`   | `/api/tasks/update-mark/1`                              | Update current user task mark with id #1                 |
| `GET`   | `/api/users`                          | Get current user                        |
| `PUT`   | `/api/users/update`                          | Update current user                        |

### Admin
- All user options
- Update/Delete for any task of any user

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`   | `/api/tasks/1`                              | Get any user task with id #1                 |
| `DELETE`   | `/api/tasks/delete/1`                              | Delete any user task with id #1                 |
| `PUT`   | `/api/tasks/update/1`                          | Update any user task with id #1                        |
| `DELETE`   | `/api/tasks/update-mark/1`                              | Update any user task mark with id #1                 |
| `GET`   | `/api/users/1`                          | Get user with id #1                        |
| `DELETE`   | `/api/users/delete/1`                          | Delete user with id #1                        |
| `GET`   | `/api/admin/all-users`                          | Get all users                        |
| `GET`   | `/api/admin/all-tasks`                          | Get all tasks                        |
| `GET`   | `/api/admin/all-tasks?interval=week&completed=false`          | Get all uncompleted tasks for a week                 |
| `GET`   | `/api/admin/user-tasks/1`                          | Get tasks for user with id #1                       |
| `GET`   | `/api/admin/user-tasks/1?interval=month&completed=false`          | Get uncompleted tasks for a month for user with id #1                 |
| `PUT`   | `/api/admin/ban/1`                          | Ban user with id #1                       |
| `PUT`   | `/api/admin/unban/1`                          | Unban user with id #1                       |
