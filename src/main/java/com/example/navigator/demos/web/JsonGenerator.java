package com.example.navigator.demos.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonGenerator {

    public static void main(String[] args) {
        // 构造教室之间的邻接关系数据
        Map<String, Map<String, Integer>> classroomAdjacencyList = new HashMap<>();
        classroomAdjacencyList.put("101", createAdjacencyMap("102", 10, "105", 20, "201", 50));
        classroomAdjacencyList.put("105", createAdjacencyMap("101", 20));
        // 更多教室之间的邻接关系数据...

        // 将数据保存到 JSON 文件
        saveToJsonFile(classroomAdjacencyList, "D:\\ChengYushuo\\2024-1\\Software Modeling Techniques\\B-routing\\Navigator\\src\\main\\resources\\classroom_data.json");
    }

    private static Map<String, Integer> createAdjacencyMap(Object... values) {
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < values.length; i += 2) {
            String key = (String) values[i];
            Integer value = (Integer) values[i + 1];
            map.put(key, value);
        }
        return map;
    }

    private static void saveToJsonFile(Map<String, Map<String, Integer>> data, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty print
        try {
            objectMapper.writeValue(new File(fileName), data);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving data to " + fileName);
            e.printStackTrace();
        }
    }
}





