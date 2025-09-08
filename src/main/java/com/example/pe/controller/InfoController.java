package com.example.pe.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InfoController {

    @Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;

    @RequestMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,String> getInfo() {
        Map<String, String> resp = new HashMap<>();
        resp.put("application", applicationName);
        resp.put("version", buildVersion);
        return resp;
    }

    @RequestMapping(value = "/getDate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,String> getDate() {
        Map<String, String> resp = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        resp.put("currentDate", formattedDate);
        return resp;
    }
}
