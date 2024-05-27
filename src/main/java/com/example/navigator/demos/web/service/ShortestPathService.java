package com.example.navigator.demos.web.service;


import com.example.navigator.demos.web.JsonReader;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShortestPathService {

    private Map<String, Map<String, Integer>> adjacencyList;

    //public ShortestPathService(Map<String, Map<String, Integer>> adjacencyList) {
    //  this.adjacencyList = adjacencyList;
    //}
    public ShortestPathService() {
        //String fileName = "src\\main\\resources\\classroom_data.json";
        String fileName = "D:\\ChengYushuo\\2024-1\\Software Modeling Techniques\\B-routing\\Navigator\\src\\main\\resources\\classroom_data.json";
        this.adjacencyList = JsonReader.readFromJsonFile(fileName);
    }

    // 新增方法：从多个入口找到最短路径
    public Map<String, Object> findShortestPathFromAnyEntrance(String endRoom) {
        List<String> entrances = Arrays.asList("入口1", "入口2", "入口3", "入口4", "入口5","入口6", "入口7", "入口8");
        String bestEntrance = null;
        List<String> shortestPath = Collections.emptyList();
        int shortestDistance = Integer.MAX_VALUE;

        for (String entrance : entrances) {
            List<String> path = findShortestPath(entrance, endRoom);
            int distance = calculatePathDistance(path);
            if (!path.isEmpty() && distance < shortestDistance) {
                bestEntrance = entrance;
                shortestPath = path;
                shortestDistance = distance;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("entrance", bestEntrance);
        result.put("path", shortestPath);
        return result;
    }

    // 计算路径距离
    private int calculatePathDistance(List<String> path) {
        if (path.isEmpty() || path.size() < 2) {
            return Integer.MAX_VALUE;
        }

        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String current = path.get(i);
            String next = path.get(i + 1);
            distance += adjacencyList.get(current).get(next);
        }

        return distance;
    }

    public List<String> findShortestPath(String startRoom, String endRoom) {
        if (adjacencyList == null || adjacencyList.isEmpty()) {
            throw new IllegalArgumentException("Adjacency list is empty or null");
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> nodes = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialize distances and priority queue
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
            if (smallest == null || distances.get(smallest) == Integer.MAX_VALUE) {
                break;
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

            visited.add(smallest);

            Map<String, Integer> neighbors = adjacencyList.get(smallest);
            if (neighbors != null) {
                for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                    if (visited.contains(neighbor.getKey())) {
                        continue;
                    }

                    int alt = distances.get(smallest) + neighbor.getValue();
                    Integer currentDistance = distances.get(neighbor.getKey());
                    if (currentDistance == null || alt < currentDistance) {
                        distances.put(neighbor.getKey(), alt);
                        previousNodes.put(neighbor.getKey(), smallest);
                        // To update the priority queue, we must remove and re-add the node
                        nodes.remove(neighbor.getKey());
                        nodes.add(neighbor.getKey());
                    }
                }
            }
        }

        return Collections.emptyList();  // No path found
    }
}






