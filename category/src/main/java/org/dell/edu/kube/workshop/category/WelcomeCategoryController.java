package org.dell.edu.kube.workshop.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeCategoryController {
    Logger loger = LoggerFactory.getLogger(WelcomeCategoryController.class);
    @Value("${welcome.message:Welcome to Kubernetes Category Application}")
    private String message;
    @GetMapping
    public String index(){
        loger.debug("Welcome Message Generated");
        /*StringBuilder message = new StringBuilder();
        message.append("<html>");
        message.append("<head><title>Welcome to Dell Kubernetes Microservices</title></head>");
        message.append("<body><center><h1>Welcome to the Dell Kubernetes Microservices Workshop<h1><br>");
        message.append("<h2>Please click <a href='/swagger-ui.html'>here </a> to access the API Documentation</h2>");
        message.append("<br><h2>Please click <a href='/actuator'>here </a> to access the actuator endpoints</h2>");
        message.append("</center></body></html>");
        return message.toString();*/
        return message;
    }
}
