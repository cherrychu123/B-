document.addEventListener('DOMContentLoaded', (event) => {
// 初始化地图
var map = new AMap.Map('container', {
  viewMode: '2D', // 使用 2D 模式
  zoom: 15, // 地图缩放级别
  center: [108.831872, 34.125998] // 地图中心点
});

// 定义标记点的经纬度数据
var points = [
  { lnglat: [108.832695, 34.125531], name: 'Point 1' },
  { lnglat: [108.832444, 34.125471], name: 'Point 2' },
  { lnglat: [108.831959, 34.126056], name: 'Point 3' },
  { lnglat: [108.832051, 34.125768], name: 'Point 4' },
  { lnglat: [108.831852, 34.125828], name: 'Point 5' },
  { lnglat: [108.831304, 34.1263], name: 'Point 6' },
  { lnglat: [108.831247, 34.126088], name: 'Point 7' },
  { lnglat: [108.831059, 34.126161], name: 'Point 8' }
];

// 遍历标记点数据，创建并添加标记点到地图上
points.forEach(function (point) {
  new AMap.Marker({
    position: point.lnglat,  // 设置标记点的经纬度
    map: map,  // 添加到地图上
    title: point.name  // 设置标记点的标题
  });
});

  const dormitoryCoordinates = {
    "海棠5号楼": [108.83541, 34.128907],
    "海棠6号楼": [108.83515, 34.129096],
    "海棠7号楼": [108.832958, 34.129880],
    "海棠8号楼": [108.832388, 34.129859],
    "丁香13号楼": [108.830233, 34.122049],
    "丁香14号楼": [108.830233, 34.122049],
    "竹园1号楼": [108.840748, 34.12682],
    "竹园2号楼": [108.840519, 34.12693],
    "竹园3号楼": [108.839034, 34.127575],
    "竹园4号楼": [108.838791, 34.127682]
  };

  const entranceCoordinates = {
    "入口1": [108.832695, 34.125531],
    "入口2": [108.832444, 34.125471],
    "入口3": [108.831959, 34.126056],
    "入口4": [108.832051, 34.125768],
    "入口5": [108.831852, 34.125828],
    "入口6": [108.831304, 34.1263],
    "入口7": [108.831247, 34.126088],
    "入口8": [108.831059, 34.126161]
  };

  var shortestPath;
  // 步行导航
  var walking = new AMap.Walking({
    map: map,
    panel: 'panel' // 路径规划结果显示的面板
  });

  function calculateRoute() {
    var targetRoom = document.getElementById('target-room').value;
    var dormitoryArea = document.getElementById('dormitory-area').value;

    if (!targetRoom || !dormitoryArea) {
      alert("请选择宿舍区并输入目标教室");
      return;
    }

    fetch(`/api/shortest-path?endRoom=${targetRoom}`)
        .then(response => response.json())
        .then(data => {
          var bestEntrance = data.entrance;
          var path = data.path;
          console.log("Best entrance:", bestEntrance);
          console.log("Shortest path:", path);
          shortestPath = path;
          console.log(" path:", shortestPath);

          if (path.length > 0) {
            // 确保bestEntrance是一个坐标数组
            if (bestEntrance && dormitoryCoordinates[dormitoryArea]) {
              walking.search(dormitoryCoordinates[dormitoryArea], entranceCoordinates[bestEntrance], function(status, result) {
                if (status === 'complete') {
                  console.log('路线规划成功', result);
                  localStorage.setItem('shortestPath', JSON.stringify(path));
                  //window.location.href = 'results.html';
                  shortestPath =path;
                } else {
                  console.error('路线规划失败', result);
                }
              });
            } else {
              alert("未找到对应的坐标，请检查输入是否正确");
            }
          } else {
            alert("未找到路径");
          }
        })
        .catch(error => {
          console.error('Error fetching shortest path:', error);
        });
  }

  // 绑定按钮点击事件来触发路线规划
  var routeBtn = document.getElementById('route-btn');
  if (routeBtn) {
    routeBtn.onclick = calculateRoute;
  }
});


