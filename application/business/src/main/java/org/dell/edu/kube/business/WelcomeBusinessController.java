package org.dell.edu.kube.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@RestController
@RequestMapping("/")
public class WelcomeBusinessController {
    Logger loger = LoggerFactory.getLogger(WelcomeBusinessController.class);
    @Value("${welcome.message:Welcome to Kubernetes Business Application}")
    private String message;

    @Value("${HOSTNAME:business}")
    private String hostname;
    @GetMapping
    public String index(){
        loger.trace("Welcome to Kubernetes Business Application Message Generated @"+hostname);
        loger.debug("Welcome to Kubernetes Business Application Message Generated @"+hostname);
        loger.warn("Welcome to Kubernetes Business Application Message Generated @"+hostname);
        loger.info("Welcome to Kubernetes Business Application Message Generated @"+hostname);
        loger.error("Welcome to Kubernetes Business Application Message Generated @"+hostname);
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
