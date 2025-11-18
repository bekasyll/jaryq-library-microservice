#  JaryqLibrary — Микросервисная система для управления библиотекой

Микросервисный проект **JaryqLibrary** - это система для управления библиотечными данными, состоящая из нескольких сервисов:

* **Books Service** — управление книгами
* **Members Service** — управление участниками (читателями)
* **Loans Service** — управление займами книг (выдача, возврат, продление)

Каждый сервис имеет своё REST API, собственный слой данных и бизнес-логику.
Проект использует **Spring Boot**, **PostgreSQL**, а также включает **валидацию**, **исключения**, **аудит**, **Swagger/OpenAPI** и **MapStruct**.

---

##  Технологический стек

| Технологии                                 |
|--------------------------------------------|
| **Java 17+**                               |
| **Spring Boot 3**                          |
| **Spring Web**                             |
| **Spring Data JPA**                        |
| **Spring Validation (Jakarta Validation)** |
| **MapStruct**                              |
| **Lombok**                                 |
| **PostgreSQL**                             |
| **Swagger / OpenAPI 3**                    |
| **JPA Auditing**                           |

---

##  Модули проекта

###  1. Books Service

Обрабатывает информацию о книгах.

Основные функции:

* Получение книги по ISBN
* Создание, обновление, удаление книги
* Валидация ISBN (ровно 13 цифр)
* Возврат ResponseDto с кодом/сообщением

API:
`/api/books/fetch?isbn=...`
`/api/books/create`
`/api/books/update`
`/api/books/delete?isbn=...`

---

###  2. Members Service

Отвечает за управление данными участников библиотеки.

Основные функции:

* Получение участника по ИИН
* Создание, обновление, удаление участника
* Валидация ИИН (не более 12 символов)

API:
`/api/members/fetch?iin=...`
`/api/members/create`
`/api/members/update`
`/api/members/delete?iin=...`

---

###  3. Loans Service

Работает с займами книг.

Основные функции:

* Получить займ по bookId / memberId
* Создать новый займ
* Вернуть книгу
* Продлить займ
* Удалить займ
* Аудит всех операций (createdAt/updatedAt, createdBy/updatedBy)

API:
`/api/loans/fetch-by-book?bookId=...`
`/api/loans/fetch-by-member?memberId=...`
`/api/loans/create`
`/api/loans/return-book`
`/api/loans/extend-loan`
`/api/loans/delete?id=...`

---

##  Swagger документация

После запуска доступна по адресам:

* **Books:**
  `http://localhost:8080/swagger-ui.html`

* **Members:**
  `http://localhost:8090/swagger-ui.html`

* **Loans:**
  `http://localhost:9000/swagger-ui.html`

---

##  Автор

**Bekasyl Asylbekov**
[bekasylasylbekov@gmail.com](mailto:bekasylasylbekov@gmail.com)
