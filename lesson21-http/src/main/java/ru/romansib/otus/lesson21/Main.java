package ru.romansib.otus.lesson21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        int port = appConfig.getPort();
        try (ServerSocket serverSocket = new ServerSocket(port) ) {
            serverSocket.setReceiveBufferSize(appConfig.getMaxRequestSize());
            System.out.println("Сервер запущен. Порт " + port);

            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(appConfig.getThreadsCount());
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    Map<String, String> requestParts = getSocketRequestParts(socket);
                    PrintWriter response = getResponseWriter(socket);
                    if (isShutdownSignal(requestParts)) {
                        makeOkResponse(response, "shutdown");
                        socket.close();
                        break;
                    }
                    executor.submit(() -> {
                        handleRequest(requestParts, response);
                    });
                } catch (Exception e) {
                    makeInternalServerErrorResponse(getResponseWriter(socket), "что-то пошло не так");
                }
            }
            executor.shutdown();
        } catch (IOException ioe) {
            System.out.println("Не удалось запустить сервер!");
            ioe.printStackTrace();
        }
    }

    private static Map<String, String> getSocketRequestParts(Socket socket) throws IOException {
        BufferedReader request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Map<String, String> requestContent = new HashMap<>();
        String requestLine = request.readLine();
        String[] firstLine = requestLine.split(" ");
        requestContent.put("Method", firstLine[0]);
        requestContent.put("Path", firstLine[1]);
        requestContent.put("Protocol", firstLine[2]);
        while (requestLine != null) {
            requestLine = request.readLine();
            String[] lineArr = requestLine.split(":", 2);
            if (requestLine.length() == 0) {
                break;
            }
            requestContent.put(lineArr[0], lineArr[1].trim());
            //System.out.println(requestLine);
        }
        StringBuilder payload = new StringBuilder();
        while (request.ready()) {
            payload.append((char) request.read());
        }
        if (!payload.toString().isEmpty())
            requestContent.put("Body", payload.toString());
        return requestContent;
    }

    private static PrintWriter getResponseWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    private static void makeOkResponse(PrintWriter response, String message) {
        response.println("HTTP/1.1 200 OK");
        response.println("Content-Type: text/html");
        response.println();
        response.println("<html><body><h1>" + message + "</h1></body></html>");
        response.close();
    }

    private static void makeInternalServerErrorResponse(PrintWriter response, String message) {
        response.println("HTTP/1.1 500 Internal Server Error");
        response.println("Content-Type: text/html");
        response.println("<html><body><h1>500 Internal Server Error</h1><p>" + message + "</p></body></html>");
    }

    private static boolean isShutdownSignal(Map<String, String> requestParts) {
        return requestParts.get("Method").equals("GET") && requestParts.get("Path").contains("/shutdown");
    }

    private static void handleRequest(Map<String, String> requestParts, PrintWriter response) {
        try {
            if (requestParts.get("Method").equals("GET")) {
                handleGetRequest(requestParts, response);
            }
            if (requestParts.get("Method").equals("POST")) {
                handlePostRequest(requestParts, response);
            }
        } catch (Exception e) {
            makeInternalServerErrorResponse(response, Arrays.toString(e.getStackTrace()));
        }
    }

    private static void handleGetRequest(Map<String, String> requestParts, PrintWriter response) {
        makeHttpRequest(requestParts, null);
        makeOkResponse(response, "request " + requestParts.get("Path") + " sussessfully processed");
    }

    private static void handlePostRequest(Map<String, String> requestParts, PrintWriter response) {
        Map<String, String> paramMap = new HashMap<>();
        if (requestParts.get("Path").contains("?")) {
            String[] params = requestParts.get("Path").split("\\?");
            for (String splittedParam : params[1].split("&")) {
                String[] kvParam = splittedParam.split("=");
                paramMap.put(kvParam[0], kvParam[1]);
            }
        }
        StringBuilder body = new StringBuilder();
        body.append("Входные параметры:").append(System.getProperty("line.separator"));
        for (Map.Entry<String, String> p : paramMap.entrySet()) {
            body.append(p.getKey()).append(" : ").append(p.getValue()).append(System.getProperty("line.separator"));
        }

        if (requestParts.containsKey("Body")) {
            body.append("Тело запроса:").append(System.getProperty("line.separator")).append(requestParts.get("Body"));
            makeHttpRequest(requestParts, requestParts.get("Body"));
        } else {
            makeHttpRequest(requestParts, null);
        }


        makeOkResponse(response, body.toString());
    }

    private static void makeHttpRequest(Map<String, String> requestParts, String body) {
        //искомый (п.2 задания) объект.
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("http://" + requestParts.get("Host") + requestParts.get("Path")))
                .setHeader("User-Agent", requestParts.get("User-Agent"))
                .setHeader("Accept", requestParts.get("Accept"))
                .setHeader("Accept-Encoding", requestParts.get("Accept-Encoding"))
                .setHeader("Accept-Language", requestParts.getOrDefault("Accept-Language", "ru-RU,ru"))
                .setHeader("Cookie", requestParts.getOrDefault("Cookie", "No cookie"))
                .version(HttpClient.Version.HTTP_1_1)
                .method(requestParts.get("Method"), getHttpRequestBodyPublisher(requestParts.get("Method"), body))
                .build();
        System.out.println("result HttpRequest: " + request.toString());
    }

    private static HttpRequest.BodyPublisher getHttpRequestBodyPublisher(String method, String body) {
        if (method.equals("POST") && body != null && !body.isEmpty()) {
            return HttpRequest.BodyPublishers.ofString(body);
        }
        return HttpRequest.BodyPublishers.noBody();
    }
}