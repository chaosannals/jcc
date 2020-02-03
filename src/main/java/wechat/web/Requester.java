package wechat.web;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 */
abstract class Requester {
    static StringBuilder post(String url, String content) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        try (PrintWriter writer = new PrintWriter(connection.getOutputStream())) {
            writer.print(content);
        }
        return request(connection);
    }

    static StringBuilder get(String url) throws IOException {
        return request(new URL(url).openConnection());
    }

    static StringBuilder get(URL url) throws IOException {
        return request(url.openConnection());
    }

    private static StringBuilder request(URLConnection connection) throws IOException {
        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder buffer = new StringBuilder();
        while (scanner.hasNextLine()) {
            buffer.append(scanner.nextLine());
            buffer.append('\n');
        }
        scanner.close();
        return buffer;
    }
}
