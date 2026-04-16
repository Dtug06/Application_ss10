package org.example.btth.model;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class BorrowRequestForm {

    private Long itemId;
    private String itemName; // for display only

    @NotBlank(message = "Họ và tên không được để trống")
    private String studentName;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^[A-Z]{2}\\d{4,}$",
            message = "Mã sinh viên phải bắt đầu bằng 2 chữ cái in hoa, theo sau là các chữ số")
    private String studentCode;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Số lượng mượn không được để trống")
    @Min(value = 1, message = "Số lượng mượn phải lớn hơn 0")
    private Integer quantity;

    @NotNull(message = "Ngày dự kiến nhận không được để trống")
    @Future(message = "Ngày dự kiến nhận phải ở tương lai")
    private LocalDate startDate;

    @NotNull(message = "Ngày dự kiến trả không được để trống")
    @Future(message = "Ngày dự kiến trả phải ở tương lai")
    private LocalDate endDate;

    @NotBlank(message = "Lý do mượn không được để trống")
    @Size(max = 500, message = "Lý do mượn tối đa 500 ký tự")
    private String reason;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

