package com.codebros.evaluator.workspace.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {



    @GetMapping("/")
    public String userIndex() { return "auth/user/admin"; }
    @GetMapping("/stats")
    public String statsIndex(){return "workspace/admin/index";}


}
