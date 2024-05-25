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
                // 获取到达相邻节点的当前最短距离
                Integer currentDistance = distances.get(neighbor.getKey());

                // 如果当前最短距离为 null 或者新路径更短，则更新最短距离并添加相邻节点到待处理节点队列中
                if (currentDistance == null || alt < currentDistance) {
                    // 更新到达相邻节点的最短距离
                    distances.put(neighbor.getKey(), alt);
                    previousNodes.put(neighbor.getKey(), smallest);
                    nodes.add(neighbor.getKey());
                }
               /* if (alt < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), alt);
                    previousNodes.put(neighbor.getKey(), smallest);
                    nodes.add(neighbor.getKey());
                }*/
            }
        }
        System.out.println("Finding shortest path from " + startRoom + " to " + endRoom);

        return Collections.emptyList();  // No path found
    }

    public static void main(String[] args) {
        String fileName = "D:\\ChengYushuo\\2024-1\\Software Modeling Techniques\\B-routing\\Navigator\\src\\main\\resources\\classroom_data.json";
        System.out.println("Loading adjacency list from file: " + fileName);
        Map<String, Map<String, Integer>> classroomAdjacencyList = JsonReader.readFromJsonFile("D:\\ChengYushuo\\2024-1\\Software Modeling Techniques\\B-routing\\Navigator\\src\\main\\resources\\classroom_data.json");
        if (classroomAdjacencyList == null)
            System.out.println("Failed to load adjacency list from JSON file.");
        else
            System.out.println("Loaded adjacency list: " + classroomAdjacencyList);



        ShortestPathService service = new ShortestPathService(classroomAdjacencyList);


        List<String> path = service.findShortestPath("101", "105");

        System.out.println("Shortest path: " + path);
    }
}




