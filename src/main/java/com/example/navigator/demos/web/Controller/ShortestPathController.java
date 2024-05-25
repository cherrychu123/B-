package com.example.navigator.demos.web.Controller;


import com.example.navigator.demos.web.service.ShortestPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShortestPathController {

    @Autowired
    private ShortestPathService shortestPathService;

    @GetMapping("/shortestPath")
    public List<String> getShortestPath(@RequestParam String startRoom, @RequestParam String endRoom) throws IOException {
        return shortestPathService.findShortestPath(startRoom, endRoom);
    }
}
