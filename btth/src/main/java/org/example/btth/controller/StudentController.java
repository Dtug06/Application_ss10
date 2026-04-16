package org.example.btth.controller;

import jakarta.validation.Valid;
import org.example.btth.model.BorrowRequestForm;
import org.example.btth.model.Item;
import org.example.btth.service.BorrowRequestService;
import org.example.btth.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class StudentController {

    private final ItemService itemService;
    private final BorrowRequestService borrowRequestService;

    public StudentController(ItemService itemService, BorrowRequestService borrowRequestService) {
        this.itemService = itemService;
        this.borrowRequestService = borrowRequestService;
    }

    @GetMapping({"/", "/items"})
    public String listItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/list";
    }

    @GetMapping("/items/{id}/request")
    public String showRequestForm(@PathVariable("id") Long id, Model model) {
        Item item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));

        BorrowRequestForm form = new BorrowRequestForm();
        form.setItemId(item.getId());
        form.setItemName(item.getName());

        if (item.getType() == Item.Type.LAB) {
            form.setQuantity(1);
        }

        model.addAttribute("item", item);
        model.addAttribute("borrowForm", form);
        return "requests/form";
    }

    @PostMapping("/items/{id}/request")
    public String submitRequest(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("borrowForm") BorrowRequestForm form,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        Item item = itemService.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));
        form.setItemId(item.getId());
        form.setItemName(item.getName());

        // Business validation: endDate after startDate
        LocalDate start = form.getStartDate();
        LocalDate end = form.getEndDate();
        if (start != null && end != null && !end.isAfter(start)) {
            bindingResult.rejectValue("endDate", "date.invalid", "Ngày dự kiến trả phải sau ngày nhận");
        }

        // Business validation: quantity does not exceed stock
        if (form.getQuantity() != null && form.getQuantity() > item.getStock()) {
            bindingResult.rejectValue("quantity", "quantity.invalid", "Số lượng mượn không được vượt quá số lượng tồn kho");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "requests/form";
        }

        borrowRequestService.addRequest(form);

        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký mượn thành công!");
        return "redirect:/items";
    }
}

