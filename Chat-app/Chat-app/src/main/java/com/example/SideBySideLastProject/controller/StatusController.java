package com.example.SideBySideLastProject.controller;

import com.example.SideBySideLastProject.model.Status;
import com.example.SideBySideLastProject.service.StatusService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    @Autowired
    private StatusService statusServiceservice;

    @PostMapping("/addStatus")
    public int setStatus(@RequestBody String status){

        JSONObject jsonObject = new JSONObject(status);
        Status newStatus = statusServiceservice.setStatus(jsonObject);
        return statusServiceservice.saveSatus(newStatus);

    }

}
