package org.example.btth.controller;

import org.example.btth.service.BorrowRequestService;
import org.example.btth.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ItemService itemService;
    private final BorrowRequestService borrowRequestService;

    public AdminController(ItemService itemService, BorrowRequestService borrowRequestService) {
        this.itemService = itemService;
        this.borrowRequestService = borrowRequestService;
    }

    @GetMapping("/items")
    public String manageItems(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "admin/items";
    }

    @GetMapping("/requests")
    public String viewRequests(Model model) {
        model.addAttribute("requests", borrowRequestService.findAll());
        return "admin/requests";
    }
}

