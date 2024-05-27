package com.example.navigator.demos.web.Controller;


import com.example.navigator.demos.web.service.ShortestPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShortestPathController {

    @Autowired
    private ShortestPathService shortestPathService;

    @GetMapping("/shortest-path")
    public Map<String, Object> getShortestPath(@RequestParam String endRoom) {
        return shortestPathService.findShortestPathFromAnyEntrance(endRoom);
    }
}
