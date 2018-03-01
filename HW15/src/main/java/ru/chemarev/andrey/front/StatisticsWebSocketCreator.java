package ru.chemarev.andrey.front;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import ru.chemarev.andrey.app.FrontendService;

public class StatisticsWebSocketCreator implements WebSocketCreator {
    private final FrontendService frontendService;

    public StatisticsWebSocketCreator(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        System.out.println("Socket created");
        return new StatisticsWebSocket(frontendService);
    }
}
