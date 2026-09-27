# ToDo REST API

Spring Boot와 Spring Data JPA로 구현한 할 일 관리 REST API입니다.

회원 가입과 로그인 없이 할 일을 생성하고, 조회하고, 수정하고, 완료 처리하고, 삭제할 수 있습니다.

## 1. 실행 방법

### 실행 환경

- JDK 21
- Spring Boot 3.4.4
- Gradle
- H2 Database

### DB 준비

별도의 데이터베이스 서버를 설치할 필요가 없습니다.

H2 Database를 파일 모드로 사용하며, 프로젝트 실행 시 프로젝트 루트에 `db_dev.mv.db` 파일이 생성됩니다.

H2 접속 정보는 다음과 같습니다.

```text
JDBC URL: jdbc:h2:./db_dev;MODE=MySQL
Username: sa
Password: 없음
```

`db_dev.mv.db`와 같은 데이터베이스 파일은 제출 대상에서 제외합니다.

### 서버 실행

프로젝트 루트에서 다음 명령을 실행합니다.

```bash
./gradlew bootRun
```

서버가 정상적으로 실행되면 다음 주소에서 API를 사용할 수 있습니다.

```text
http://localhost:8080
```

## 2. API 설계

모든 API의 기본 주소는 다음과 같습니다.

```text
/api/v1/tasks
```

| 기능 | 메서드 | 주소 | 요청 본문 | 성공 상태 |
|---|---|---|---|---|
| 할 일 생성 | `POST` | `/api/v1/tasks` | 필요 | `200 OK` |
| 할 일 목록 조회 | `GET` | `/api/v1/tasks` | 없음 | `200 OK` |
| 할 일 단건 조회 | `GET` | `/api/v1/tasks/{id}` | 없음 | `200 OK` |
| 할 일 수정 | `PUT` | `/api/v1/tasks/{id}` | 필요 | `200 OK` |
| 완료 여부 변경 | `PATCH` | `/api/v1/tasks/{id}/complete?complete={true\|false}` | 없음 | `200 OK` |
| 할 일 삭제 | `DELETE` | `/api/v1/tasks/{id}` | 없음 | `200 OK` |

할 일의 `id`는 URL의 경로 변수로 전달합니다.

HTTP 메서드는 REST 관례에 따라 다음과 같이 선택했습니다.

- `POST`: 새로운 할 일을 생성하기 위해 사용합니다.
- `GET`: 할 일 목록 또는 특정 할 일을 조회하기 위해 사용합니다.
- `PUT`: 할 일의 제목, 설명, 완료 여부를 수정하기 위해 사용합니다.
- `PATCH`: 완료 여부만 일부 수정하기 위해 사용합니다.
- `DELETE`: 할 일을 삭제하기 위해 사용합니다.

현재 생성과 삭제 API는 처리된 할 일 정보를 응답 본문으로 반환하도록 구현했기 때문에 성공 상태 코드로 `200 OK`를 사용했습니다.

## 4. API 명세

### 4.1 할 일 생성

#### 요청

```http
POST /api/v1/tasks
Content-Type: application/json
```

```json
{
  "title": "Spring 공부하기",
  "description": "Controller와 Service 구현하기"
}
```

#### 응답

상태 코드: `200 OK`

```json
{
  "id": 1,
  "title": "Spring 공부하기",
  "description": "Controller와 Service 구현하기",
  "complete": false,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:40:00"
}
```

---

### 4.2 할 일 목록 조회

#### 요청

```http
GET /api/v1/tasks
```

#### 응답

상태 코드: `200 OK`

```json
[
  {
    "id": 1,
    "title": "Spring 공부하기",
    "description": "Controller와 Service 구현하기",
    "complete": false,
    "createDate": "2026-09-27T23:40:00",
    "modifyDate": "2026-09-27T23:40:00"
  }
]
```

---

### 4.3 할 일 단건 조회

#### 요청

```http
GET /api/v1/tasks/1
```

#### 응답

상태 코드: `200 OK`

```json
{
  "id": 1,
  "title": "Spring 공부하기",
  "description": "Controller와 Service 구현하기",
  "complete": false,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:40:00"
}
```

---

### 4.4 할 일 수정

#### 요청

```http
PUT /api/v1/tasks/1
Content-Type: application/json
```

```json
{
  "title": "Spring Boot 복습하기",
  "description": "예외 처리까지 구현하기",
  "complete": false
}
```

#### 응답

상태 코드: `200 OK`

```json
{
  "id": 1,
  "title": "Spring Boot 복습하기",
  "description": "예외 처리까지 구현하기",
  "complete": false,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:45:00"
}
```

---

### 4.5 완료 여부 변경

완료 여부만 변경할 때는 `complete` 값을 쿼리 파라미터로 전달합니다.

#### 요청

```http
PATCH /api/v1/tasks/1/complete?complete=true
```

요청 본문은 없습니다.

#### 응답

상태 코드: `200 OK`

```json
{
  "id": 1,
  "title": "Spring Boot 복습하기",
  "description": "예외 처리까지 구현하기",
  "complete": true,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:50:00"
}
```

미완료로 변경하려면 다음과 같이 요청합니다.

```http
PATCH /api/v1/tasks/1/complete?complete=false
```

---

### 4.6 할 일 삭제

#### 요청

```http
DELETE /api/v1/tasks/1
```

#### 응답

상태 코드: `200 OK`

```json
{
  "id": 1,
  "title": "Spring Boot 복습하기",
  "description": "예외 처리까지 구현하기",
  "complete": true,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:50:00"
}
```

응답이 정상적으로 반환된 뒤 해당 할 일은 데이터베이스에서 삭제됩니다.

## 5. 오류 응답

모든 오류는 다음과 같은 동일한 형식으로 응답하도록 설계했습니다.

```json
{
  "status": 400,
  "code": "400-VALIDATION",
  "message": "제목은 비어 있을 수 없습니다."
}
```

오류 응답 필드는 다음과 같습니다.

| 필드 | 설명 |
|---|---|
| `status` | HTTP 상태 코드 |
| `code` | 오류 종류를 나타내는 코드 |
| `message` | 오류 내용 |

### 5.1 입력 검증 오류

제목이 비어 있거나 공백만 있는 경우 `400 Bad Request`를 반환합니다.

#### 요청

```bash
curl -i -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "   ",
    "description": "잘못된 요청"
  }'
```

#### 응답

```http
HTTP/1.1 400
Content-Type: application/json
```

```json
{
  "status": 400,
  "code": "400-VALIDATION",
  "message": "제목은 비어 있을 수 없습니다."
}
```

제목이 100자를 초과한 경우에도 `400 Bad Request`를 반환합니다.

---

### 5.2 존재하지 않는 할 일

존재하지 않는 `id`를 조회, 수정, 완료 처리 또는 삭제하려고 하면 `404 Not Found`를 반환합니다.

#### 요청

```bash
curl -i http://localhost:8080/api/v1/tasks/999999
```

#### 응답

```http
HTTP/1.1 404
Content-Type: application/json
```

```json
{
  "status": 404,
  "code": "404-1",
  "message": "존재하지 않는 할 일입니다."
}
```

존재하지 않는 할 일에 대해 서버 내부 오류인 `500 Internal Server Error`가 발생하지 않도록 Service에서 먼저 존재 여부를 확인합니다.

## 6. 실행 결과

아래는 서버를 실행한 뒤 curl로 확인한 정상 흐름입니다.

실제 제출 시에는 직접 실행한 결과의 `id`, 날짜와 시간을 실제 응답에 맞게 수정합니다.

### 6.1 할 일 만들기

#### 요청

```bash
curl -i -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring 공부하기",
    "description": "ToDo API 만들기"
  }'
```

#### 응답

```http
HTTP/1.1 200
Content-Type: application/json
```

```json
{
  "id": 1,
  "title": "Spring 공부하기",
  "description": "ToDo API 만들기",
  "complete": false,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:40:00"
}
```

### 6.2 목록 조회

#### 요청

```bash
curl -i http://localhost:8080/api/v1/tasks
```

#### 응답

```http
HTTP/1.1 200
Content-Type: application/json
```

```json
[
  {
    "id": 1,
    "title": "Spring 공부하기",
    "description": "ToDo API 만들기",
    "complete": false,
    "createDate": "2026-09-27T23:40:00",
    "modifyDate": "2026-09-27T23:40:00"
  }
]
```

### 6.3 완료 처리

#### 요청

```bash
curl -i -X PATCH \
  "http://localhost:8080/api/v1/tasks/1/complete?complete=true"
```

#### 응답

```http
HTTP/1.1 200
Content-Type: application/json
```

```json
{
  "id": 1,
  "title": "Spring 공부하기",
  "description": "ToDo API 만들기",
  "complete": true,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:55:00"
}
```

### 6.4 삭제

#### 요청

```bash
curl -i -X DELETE http://localhost:8080/api/v1/tasks/1
```

#### 응답

```http
HTTP/1.1 200
Content-Type: application/json
```

```json
{
  "id": 1,
  "title": "Spring 공부하기",
  "description": "ToDo API 만들기",
  "complete": true,
  "createDate": "2026-09-27T23:40:00",
  "modifyDate": "2026-09-27T23:55:00"
}
```

### 6.5 400 오류 확인

#### 요청

```bash
curl -i -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "   "
  }'
```

#### 응답

```json
{
  "status": 400,
  "code": "400-VALIDATION",
  "message": "제목은 비어 있을 수 없습니다."
}
```

### 6.6 404 오류 확인

#### 요청

```bash
curl -i http://localhost:8080/api/v1/tasks/999999
```

#### 응답

```json
{
  "status": 404,
  "code": "404-1",
  "message": "존재하지 않는 할 일입니다."
}
```

## 7. 프로젝트 구조

```text
src/main/java/com/project
├── Application.java
├── boundedContext/task
│   ├── controller
│   │   └── ApiV1TaskController.java
│   ├── entity
│   │   └── Task.java
│   ├── exception
│   │   └── DomainException.java
│   ├── repository
│   │   └── TaskRepository.java
│   └── service
│       └── TaskService.java
└── shared/task/dto
    ├── TaskCreateRequest.java
    ├── TaskResponse.java
    └── TaskUpdateRequest.java
```

Controller, Service, Repository로 역할을 나누었습니다.

- Controller: HTTP 요청을 받고 응답을 반환합니다.
- Service: 할 일 생성, 조회, 수정, 삭제와 예외 처리를 담당합니다.
- Repository: JPA를 통해 데이터베이스에 접근합니다.
- DTO: 요청과 응답에 사용하며, JPA 엔티티를 API에 직접 노출하지 않습니다.

## 8. DB를 H2로 선택한 이유

H2는 별도의 데이터베이스 서버나 Docker 환경을 준비하지 않아도 사용할 수 있습니다.

따라서 프로젝트를 받은 사람이 별도의 DB 설치 없이 다음 명령 하나로 서버를 실행할 수 있습니다.

```bash
./gradlew bootRun
```

또한 현재 과제 규모에서는 H2의 파일 모드만으로 할 일 데이터를 저장하고 조회하는 데 충분하다고 판단했습니다.

## 9. 제출 시 제외할 파일

다음 파일과 디렉터리는 제출하지 않습니다.

```text
build/
.gradle/
db_dev.mv.db
db_dev.trace.db
.env
```