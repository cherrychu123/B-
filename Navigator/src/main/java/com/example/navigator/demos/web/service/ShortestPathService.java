package com.example.navigator.demos.web.service;


import com.example.navigator.demos.web.JsonReader;

import java.util.List;
import java.util.Map;


import java.util.*;

public class ShortestPathService {

    private Map<String, Map<String, Integer>> adjacencyList;

    public ShortestPathService(Map<String, Map<String, Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public List<String> findShortestPath(String startRoom, String endRoom) {
        if (adjacencyList == null || adjacencyList.isEmpty()) {
            throw new IllegalArgumentException("Adjacency list is empty or null");
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<String> nodes = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String room : adjacencyList.keySet()) {
            if (room.equals(startRoom)) {
                distances.put(room, 0);
            } else {
                distances.put(room, Integer.MAX_VALUE);
            }
            nodes.add(room);
        }

        while (!nodes.isEmpty()) {
            String smallest = nodes.poll();
            if (smallest == null) {
                continue;
            }
            if (smallest.equals(endRoom)) {
                List<String> path = new ArrayList<>();
                while (previousNodes.containsKey(smallest)) {
                    path.add(smallest);
                    smallest = previousNodes.get(smallest);
                }
                path.add(startRoom);
                Collections.reverse(path);
                return path;
            }

            if (distances.get(smallest) == Integer.MAX_VALUE) {
                break;
            }

            Map<String, Integer> neighbors = adjacencyList.get(smallest);
            if (neighbors == null) {
                continue;
            }
            for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                int alt = distances.get(smallest) + neighbor.getValue();
                if (alt < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), alt);
                    previousNodes.put(neighbor.getKey(), smallest);
                    nodes.add(neighbor.getKey());
                }
            }
        }

        return Collections.emptyList();  // No path found
    }

    public static void main(String[] args) {
        Map<String, Map<String, Integer>> classroomAdjacencyList = JsonReader.readFromJsonFile("D:\\ChengYushuo\\2024-1\\Software Modeling Techniques\\B-routing\\Navigator\\src\\main\\resources\\classroom_data.json");
        if (classroomAdjacencyList == null) {
            System.out.println("Failed to load adjacency list from JSON file.");
            return;
        }

        ShortestPathService service = new ShortestPathService(classroomAdjacencyList);

        List<String> path = service.findShortestPath("101", "105");
        System.out.println("Shortest path: " + path);
    }
}


