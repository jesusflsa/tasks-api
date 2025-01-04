package com.jesusfs.tasks.domain.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/task")
@PreAuthorize("isAuthenticated()")
public class TaskController {
}
