var statisticsSocket = function() {
    var ws;
    var refreshIntervalId;

    return {
        init: function(refreshTime) {
                ws = new WebSocket("ws://localhost:8090/websocket-statistics");

                ws.onopen = function (event) {
                    statisticsSocket.getStatistics();
                    refreshIntervalId = setInterval(statisticsSocket.getStatistics, refreshTime);
                }

                ws.onmessage = function (event) {
                    var statistics = JSON.parse(event.data);
                    document.getElementById("maxElementsField").innerHTML = statistics.maxElements;
                    document.getElementById("lifeTimeField").innerHTML = statistics.lifeTimeMs;
                    document.getElementById("idleTimeField").innerHTML = statistics.idleTimeMs;
                    document.getElementById("isEternalField").innerHTML = statistics.isEternal;
                    document.getElementById("hitField").innerHTML = statistics.hit;
                    document.getElementById("missField").innerHTML = statistics.miss;
                }

                ws.onclose = function (event) {
                    ws.close();
                }
        },

        getStatistics: function() {
            ws.send("");
        }
    };
}();