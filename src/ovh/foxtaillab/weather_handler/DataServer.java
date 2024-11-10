package ovh.foxtaillab.weather_handler;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DataServer {
    protected InetSocketAddress isa;
    protected HttpServer server;

    public DataServer(int port, int backlog, String path) {
        isa = new InetSocketAddress(port);
        try {
            server = HttpServer.create(isa, backlog, path, new DataServerRequestHandler());
            server.setExecutor(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        server.start();
    }
    public void stop() {
        server.stop(5);
    }
}
