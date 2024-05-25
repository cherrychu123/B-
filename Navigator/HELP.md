# Getting Started


依赖
1. springboot
2. Lombok
3. Gson
4. maven
5. Thymeleaf 

### 项目结构
src
├── main
│   ├── java
│   │   └── com
│   │       └── com.example.navigator
│   │           ├── controller
│   │           │   └── ShortestPathController.java
│   │           ├── model
│   │           │   └── Classroom.java
│   │           ├── service
│   │           │   └── ShortestPathService.java
│   │           └── ShortestPathApplication.java
│   └── resources
│       ├── static
│       │   └── js
│       │       └── draw.js
│       ├── templates
│       │   └── index.html
│       └── application.properties
└── test
└── java
└── com
└── yourpackage
└── controller
└── ShortestPathControllerTest.java

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Shortest Path</title>
    <script src="/js/draw.js"></script>
</head>
<body>
    <h1>Shortest Path between Classrooms</h1>
    <form id="shortestPathForm">
        <label for="startRoom">Start Room:</label>
        <input type="text" id="startRoom" name="startRoom"><br><br>
        <label for="endRoom">End Room:</label>
        <input type="text" id="endRoom" name="endRoom"><br><br>
        <button type="button" onclick="findShortestPath()">Find Shortest Path</button>
    </form>
    <div id="shortestPathResult"></div>
</body>
</html>

