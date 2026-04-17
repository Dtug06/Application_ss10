package org.example.project_base_spring_mvc.controller;

import org.example.project_base_spring_mvc.model.Priority;
import org.example.project_base_spring_mvc.model.TaskItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping
public class TaskController {

    private List<TaskItem> tasks = Arrays.asList(
            new TaskItem("le123", "Dai nao thien cung", LocalDate.of(2026, 5, 15), Priority.MEDIUM),
            new TaskItem("le124", "Hoc Spring MVC", LocalDate.of(2026, 4, 20), Priority.HIGH),
            new TaskItem("le125", "Lam bai tap Thymeleaf", LocalDate.of(2026, 4, 25), Priority.LOW)
    );

    @GetMapping("/tasks")
    public String getTasks(Model model) {
        model.addAttribute("tasks", tasks);
        return "task-list";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("task", new TaskItem());
        return "task-form";
    }


    @PostMapping("/add")
    public String addTask(@ModelAttribute TaskItem task) {
        tasks.add(task);
        return "redirect:/tasks";
    }



}
