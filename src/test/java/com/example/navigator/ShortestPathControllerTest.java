package com.example.navigator;

import com.example.navigator.demos.web.service.ShortestPathService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestPathControllerTest {

    @Test
    public void testFindShortestPathFromAnyEntrance() {
        // 创建 ShortestPathService 对象
        ShortestPathService shortestPathService = new ShortestPathService();

        // 模拟输入和预期输出
        String endRoom = "目标教室";
        Map<String, Object> expectedResult = new HashMap<>();
        // 设置 expectedResult 中的期望值

        // 调用被测试的方法并验证结果
        Map<String, Object> actualResult = shortestPathService.findShortestPathFromAnyEntrance(endRoom);
        assertEquals(expectedResult, actualResult);
    }

    // 可以添加更多的测试方法来验证其他功能
}
