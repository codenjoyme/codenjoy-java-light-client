package com.codenjoy.clients.java.lite.engine;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class WebSocketRunner {

    private static final Pattern URL_REGEX = compile(
            "(?<scheme>http|https)://(?<host>.*)/codenjoy-contest/board/player/(?<player>\\w*)\\?code=(?<code>\\w*)");

    private final String token;
    private final WebSocketClient wsClient;

    public WebSocketRunner(String url) {
        this.token = urlToWsToken(url);
        this.wsClient = new WebSocketClient();
    }

    private static String urlToWsToken(String url) {
        Matcher matcher = URL_REGEX.matcher(url);
        if (!matcher.find()) {
            throw new IllegalArgumentException("invalid url: " + url);
        }
        String scheme = "https".equals(matcher.group("scheme")) ? "wss" : "ws";
        return String.format("%s://%s/codenjoy-contest/ws?user=%s&code=%s",
                scheme,
                matcher.group("host"),
                matcher.group("player"),
                matcher.group("code"));
    }

    public void run(Solver solver) {
        try {
            wsClient.start();
            wsClient.connect(new CodenjoyWebSocket(solver), URI.create(token))
                    .get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @WebSocket
    public static class CodenjoyWebSocket {

        private final Solver solver;
        private Session session;

        public CodenjoyWebSocket(Solver solver) {
            this.solver = solver;
        }

        @OnWebSocketConnect
        public void onConnect(Session session) {
            this.session = session;
            System.out.println("websocket connection successfully established");
        }

        @OnWebSocketClose
        public void onClose(int code, String message) {
            System.out.println("close websocket connection [" + code + "] - " + message);
        }

        @OnWebSocketError
        public void onError(Session session, Throwable e) {
            session.close();
            System.err.println("websocket connection error " + e);
        }

        @OnWebSocketMessage
        public void onMessage(String message) throws IOException {
            String answer = solver.answer(message);
            session.getRemote().sendString(answer);
        }
    }
}
