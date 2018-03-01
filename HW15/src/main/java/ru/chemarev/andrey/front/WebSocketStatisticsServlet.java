package ru.chemarev.andrey.front;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.chemarev.andrey.app.FrontendService;

import java.util.concurrent.TimeUnit;

public class WebSocketStatisticsServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);
    private FrontendService frontendService;

    public WebSocketStatisticsServlet(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        webSocketServletFactory.setCreator(new StatisticsWebSocketCreator(frontendService));
    }
}
