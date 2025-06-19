package src.server.java;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import javax.swing.SwingUtilities;

public class ServerURL {
    private String baseURL;

    public ServerURL() {
        try {
            InetAddress localIp = InetAddress.getLocalHost();
            String ip = localIp.getHostAddress();
            this.baseURL = "http://" + ip + "/mrscleansystem/src/server/php/";
        } catch (Exception e) {
            this.baseURL = "http://localhost/mrscleansystem/src/server/php/";
        }
    }

    public String getFullURL(String path) {
        return baseURL + path;
    }

    public void pingServer(Consumer<Boolean> callback) {
        new Thread(() -> {
            boolean result = false;
            try {
                HttpURLConnection conn = createGetConnection("ping.php");
                result = conn.getResponseCode() == 200;
            } catch (Exception ignored) {
            }
            boolean finalResult = result;
            SwingUtilities.invokeLater(() -> callback.accept(finalResult));
        }).start();
    }

    public HttpURLConnection createPostConnection(String path, String postData) {
        try {
            URL url = new URL(getFullURL(path));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Connection", "close");

            byte[] postBytes = postData.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postBytes);
                os.flush();
            }

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpURLConnection createGetConnection(String path) {
        try {
            URL url = new URL(getFullURL(path));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.connect();
            return conn;
        } catch (Exception e) {
            return null;
        }
    }
}
