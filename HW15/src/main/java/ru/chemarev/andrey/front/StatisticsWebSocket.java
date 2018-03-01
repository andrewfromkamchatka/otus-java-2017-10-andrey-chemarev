package ru.chemarev.andrey.front;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.chemarev.andrey.app.CacheStatistics;
import ru.chemarev.andrey.app.FrontendService;

import java.io.IOException;

@WebSocket
public class StatisticsWebSocket {
    private Session session;
    private FrontendService frontendService;

    public StatisticsWebSocket(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        System.out.println("onOpen");
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        try {
            frontendService.handleStatisticsRequest(this);
            System.out.println("Sending statistics request");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("onClose");
    }

    public void sendStatisticsAnswer(CacheStatistics statistics) {
        try {
            Gson gson = new Gson();
            session.getRemote().sendString(gson.toJson(statistics));
            System.out.println("Answering statistics request");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
