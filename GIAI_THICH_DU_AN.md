# Giai thich du an StudentManager

## 1. Tong quan

Day la du an Spring Boot dung de quan ly sinh vien. Ung dung co 2 phan chinh:

- Giao dien web Thymeleaf tai `/students`
- API JSON tai `/api/students`

Du an su dung SQL Server de luu du lieu sinh vien trong bang `students`.

## 2. Cong nghe su dung

- Java 21
- Spring Boot 4.0.1
- Spring Web
- Spring Data JPA
- Thymeleaf
- SQL Server
- Bootstrap 5

## 3. Cau truc thu muc quan trong

```text
src/main/java/com/example/studentmanager
|-- StudentmanagerApplication.java
|-- controller
|   |-- StudentController.java
|   |-- StudentApiController.java
|-- entity
|   |-- Student.java
|-- repository
|   |-- StudentRepository.java
|-- service
|   |-- StudentService.java

src/main/resources
|-- application.properties
|-- templates
|   |-- students.html
```

## 4. File StudentmanagerApplication.java

Day la file chay chinh cua Spring Boot.

```java
SpringApplication.run(StudentmanagerApplication.class, args);
```

Khi chay file nay, Spring Boot se khoi dong server Tomcat va doc cac cau hinh trong `application.properties`.

## 5. File application.properties

File nay cau hinh ten ung dung, port web va ket noi database.

Vi du:

```properties
server.port=8080
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=School;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=123123
```

Y nghia:

- Ung dung web chay tai port `8080`
- Database ten la `School`
- SQL Server chay tai `localhost:1433`
- Tai khoan database la `sa`

## 6. Entity Student.java

File nay dai dien cho bang `students` trong SQL Server.

```java
@Entity
@Table(name = "students")
public class Student
```

Moi object `Student` tuong ung voi mot dong trong bang `students`.

Thuoc tinh:

- `id`: khoa chinh, tu dong tang
- `name`: ho ten sinh vien
- `age`: tuoi
- `email`: email

## 7. Repository StudentRepository.java

Repository la lop lam viec truc tiep voi database.

```java
public interface StudentRepository extends JpaRepository<Student, Integer>
```

Nho ke thua `JpaRepository`, project co san cac ham:

- `findAll()`: lay tat ca sinh vien
- `findById(id)`: tim theo ID
- `save(student)`: them hoac cap nhat sinh vien
- `deleteById(id)`: xoa theo ID

Ham tu viet them:

```java
findByNameContainingIgnoreCase(String name)
```

Ham nay dung de tim sinh vien theo ten, khong phan biet chu hoa/chu thuong.

## 8. Service StudentService.java

Service la lop trung gian giua controller va repository.

Controller khong goi database truc tiep, ma goi qua service.

Mot so ham chinh:

- `getAllStudents()`: lay tat ca sinh vien
- `getStudentsPage(Pageable pageable)`: lay danh sach sinh vien co phan trang
- `save(Student student)`: luu sinh vien
- `getById(Integer id)`: lay sinh vien theo ID
- `delete(Integer id)`: xoa sinh vien
- `findByName(String name)`: tim theo ten

## 9. Controller giao dien StudentController.java

File nay xu ly cac request tu giao dien web.

Base URL:

```text
/students
```

### Danh sach sinh vien

```text
GET /students
```

Hien thi danh sach sinh vien len trang `students.html`.

Danh sach duoc phan trang, moi trang 10 sinh vien.

Vi du:

```text
http://localhost:8080/students?page=0
http://localhost:8080/students?page=1
```

### Them hoac cap nhat sinh vien

```text
POST /students/save
```

Khi bam nut Luu tren form, du lieu se duoc gui ve route nay.

Neu co `id`, chuong trinh se cap nhat sinh vien cu.

Neu khong co `id`, chuong trinh se them sinh vien moi.

### Sua sinh vien

```text
GET /students/edit/{id}
```

Route nay lay thong tin sinh vien theo ID va dua du lieu len form de sua.

### Xoa sinh vien

```text
GET /students/delete/{id}
```

Route nay xoa sinh vien theo ID.

### Tim kiem sinh vien

```text
GET /students/search?keyword=...
```

Neu keyword la so, chuong trinh tim theo ID.

Neu keyword la chu, chuong trinh tim theo ten.

## 10. Controller API StudentApiController.java

File nay tra ve du lieu JSON, dung de test bang Postman, fetch API, mobile app hoac frontend rieng.

Base URL:

```text
/api/students
```

### Lay tat ca sinh vien

```text
GET http://localhost:8080/api/students
```

Tra ve danh sach sinh vien dang JSON.

### Lay sinh vien theo ID

```text
GET http://localhost:8080/api/students/1
```

Neu co sinh vien ID = 1 thi tra ve sinh vien do.

Neu khong co thi tra ve HTTP 404.

### Them sinh vien

```text
POST http://localhost:8080/api/students
```

Body:

```json
{
  "name": "Nguyen Van Test",
  "age": 20,
  "email": "test@gmail.com"
}
```

Khong can gui `id` vi ID duoc database tu dong tang.

### Cap nhat sinh vien

```text
PUT http://localhost:8080/api/students/1
```

Body:

```json
{
  "name": "Nguyen Van Update",
  "age": 21,
  "email": "update@gmail.com"
}
```

### Xoa sinh vien

```text
DELETE http://localhost:8080/api/students/1
```

Neu xoa thanh cong thi tra ve HTTP 204.

## 11. Template students.html

File nay la giao dien web.

No gom cac phan:

- Form tim kiem sinh vien
- Form them/sua sinh vien
- Bang danh sach sinh vien
- Nut Sua
- Nut Xoa
- Thanh phan trang

Du lieu trong file HTML duoc render bang Thymeleaf.

Vi du:

```html
<tr th:each="sv : ${students}">
```

Dong tren lap qua danh sach `students` do controller gui sang.

## 12. Luong chay cua chuc nang xem danh sach

```text
Trinh duyet goi /students
-> StudentController nhan request
-> StudentService lay du lieu
-> StudentRepository truy van SQL Server
-> Controller gui du lieu sang students.html
-> Thymeleaf render thanh HTML
-> Trinh duyet hien thi bang sinh vien
```

## 13. Luong chay cua API

```text
Postman goi /api/students
-> StudentApiController nhan request
-> StudentService xu ly
-> StudentRepository lam viec voi database
-> Ket qua tra ve dang JSON
```

## 14. Link chay du an

Giao dien web:

```text
http://localhost:8080/students
```

API lay danh sach:

```text
http://localhost:8080/api/students
```

## 15. Loi thuong gap

### Loi Whitelabel Error Page 500

Neu vao `/students` bi loi 500 va log co noi khong ket noi duoc SQL Server, nghia la ung dung da chay nhung database bi loi ket noi.

Can kiem tra:

- SQL Server da chay chua
- Database `School` co ton tai khong
- Bang `students` co ton tai khong
- Username/password dung chua
- Port SQL Server co dung la `1433` khong

### Loi khong vao duoc localhost

Kiem tra app da chay thanh cong chua. Log dung se co dong:

```text
Tomcat started on port 8080
```

Neu co dong nay thi vao:

```text
http://localhost:8080/students
```

