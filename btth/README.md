# Hệ thống Quản lý Mượn/Trả Thiết bị IT &amp; Phòng Lab

Ứng dụng web demo xây dựng bằng **Spring WebMVC 7.x** (không dùng Spring Boot) và **Thymeleaf**, triển khai theo mô hình **MVC** theo đúng tài liệu đặc tả SRS.

## 1. Công nghệ &amp; Ràng buộc

- **Java**: 21
- **Build tool**: Gradle (project dạng WAR)
- **Framework web**: Spring WebMVC 7.0.x
- **View template**: Thymeleaf 3 (thymeleaf-spring6)
- **Validation**: Hibernate Validator (JSR-380)
- **Servlet container khuyến nghị**: Tomcat 10+ (Jakarta EE 10)

Toàn bộ cấu hình DispatcherServlet, ViewResolver, Validator đều được cấu hình thủ công bằng **Java Config**:

- `org.example.btth.config.WebInitializer`
- `org.example.btth.config.WebConfig`
- `org.example.btth.config.AppConfig`

Không sử dụng Spring Boot.

## 2. Cách build &amp; chạy dự án

### 2.1. Build bằng Gradle

Trong thư mục gốc dự án, chạy:

```bash
./gradlew clean build
```

Trên Windows (PowerShell / CMD):

```bash
.\gradlew.bat clean build
```

Kết quả build sẽ tạo file WAR trong thư mục:

```text
build/libs/btth-1.0-SNAPSHOT.war
```

### 2.2. Deploy WAR lên Tomcat

1. Copy file WAR ở trên vào thư mục `webapps` của Tomcat (ví dụ: `apache-tomcat-10.x/webapps`).
2. Khởi động Tomcat.
3. Truy cập trình duyệt:
   - Trang danh mục thiết bị/phòng Lab (Sinh viên): `http://localhost:8080/<context-path>/items`
   - Trang Admin xem yêu cầu mượn: `http://localhost:8080/<context-path>/admin/requests`

Nếu WAR được đặt tên `btth-1.0-SNAPSHOT.war`, context-path mặc định sẽ là `/btth-1.0-SNAPSHOT`.

## 3. Chức năng đã hiện thực

### 3.1. Module Sinh viên

- **REQ-S01: Xem danh mục**
  - Trang `/items` hiển thị danh sách thiết bị/phòng Lab (tên, hình minh họa, số lượng tồn).
- **REQ-S02: Form Đăng ký mượn**
  - Sinh viên chọn một thiết bị/phòng và mở trang `/items/{id}/request`.
  - Form yêu cầu nhập:
    - Họ và tên sinh viên
    - Mã sinh viên
    - Email liên hệ
    - Số lượng muốn mượn (nếu là phòng Lab thì mặc định là 1)
    - Ngày dự kiến nhận / sử dụng
    - Ngày dự kiến trả
    - Lý do mượn
  - Khi submit thành công, hệ thống redirect về `/items` và hiển thị thông báo **"Đăng ký mượn thành công!"** bằng **Flash Attribute**, tránh lỗi submit lại khi F5.

### 3.2. Module Quản lý Lab (Admin)

- **REQ-A01: Quản lý danh sách thiết bị**
  - Trang `/admin/items` hiển thị danh sách thiết bị/phòng Lab (demo dữ liệu in-memory).
- **REQ-A02: Xem danh sách yêu cầu**
  - Trang `/admin/requests` hiển thị các yêu cầu mượn đã được sinh viên gửi (lưu in-memory).

## 4. Ràng buộc nghiệp vụ &amp; Validation

Toàn bộ validation sử dụng **Data Binding** của Spring MVC và **Annotation của Hibernate Validator**:

- **VAL-01 (Bắt buộc nhập)**:
  - Các trường trong `BorrowRequestForm` sử dụng `@NotBlank`, `@NotNull`.
- **VAL-02 (Logic Thời gian)**:
  - `startDate`, `endDate` dùng `@Future`.
  - Trong controller kiểm tra thêm logic `endDate` phải sau `startDate`, nếu sai sẽ `rejectValue` và trả về form với thông báo lỗi tiếng Việt.
- **VAL-03 (Logic Số lượng)**:
  - Trường `quantity` có `@Min(1)` và controller kiểm tra không vượt quá số lượng tồn kho của thiết bị.
- **VAL-04 (Định dạng Dữ liệu)**:
  - `email` dùng `@Email`.
  - `studentCode` dùng `@Pattern("^[A-Z]{2}\\d{4,}$")` để đảm bảo bắt đầu bằng 2 chữ cái theo sau là số.
- **VAL-05 (Xử lý lỗi View)**:
  - Sử dụng `BindingResult` trong controller.
  - Khi có lỗi, hệ thống **không crash**, trả lại đúng form `requests/form.html`, giữ nguyên dữ liệu người dùng đã nhập và hiển thị lỗi bằng `th:errors` ngay dưới mỗi trường.

## 5. Giao diện (UI)

- Sử dụng **Bootstrap 5** cho layout form gọn gàng, responsive.
- Sử dụng **Thymeleaf Fragment**:
  - File `fragments/layout.html` định nghĩa:
    - `head` (include CSS/JS dùng chung)
    - `navbar`
    - `footer`
  - Các view `items/list.html`, `requests/form.html`, `admin/*.html` đều `th:replace` các fragment này để tái sử dụng layout.

## 6. Ghi chú triển khai / mở rộng

- Dữ liệu thiết bị/phòng Lab và yêu cầu mượn hiện đang lưu **trong bộ nhớ (in-memory)** trong các service:
  - `ItemService`
  - `BorrowRequestService`
- Khi cần mở rộng, có thể:
  - Tích hợp database (MySQL/PostgreSQL) và Spring JDBC/JPA.
  - Bổ sung **Custom Validator** (@CheckDateCollision) để kiểm tra trùng lịch mượn phòng Lab theo Bonus 1.
  - Thêm tính năng cập nhật trạng thái yêu cầu (Đã duyệt, Đã trả, ...).

## 7. Báo cáo chức năng (tóm tắt)

- **Sinh viên**:
  - Xem danh sách thiết bị/phòng Lab.
  - Gửi form đăng ký mượn với đầy đủ validation.
  - Nhận thông báo thành công qua Redirect + Flash Attribute.
- **Quản lý Lab**:
  - Xem danh sách thiết bị/phòng Lab.
  - Xem danh sách các yêu cầu mượn từ sinh viên để chuẩn bị thiết bị.

