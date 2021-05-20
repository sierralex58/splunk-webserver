

import io.javalin.Javalin;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class MainSSL {
    public static void main(String... args) throws Exception {
        AtomicInteger received = new AtomicInteger();
        Javalin j = Javalin.create(config -> {
            config.server(() -> {
                Server server = new Server();
                ServerConnector sslConnector = new ServerConnector(server, getSslContextFactory());
                sslConnector.setPort(38888);
                ServerConnector connector = new ServerConnector(server);
                connector.setPort(38887);
                server.setConnectors(new Connector[]{sslConnector, connector});
                return server;
            });
        }).start();
        j.post("/*", req -> {
            received.incrementAndGet();
            System.out.println(req.body());
            req.result("OK");
        });
        j.get("/*", req -> {
            received.incrementAndGet();
            System.out.println(req.body());
            req.result("OK");
        });
        while(received.get() < 10000) {
            Thread.sleep(1000);
            System.out.println(received.get());
        }
    }

    private static SslContextFactory getSslContextFactory() {
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("/home/tkmax/untitled/src/main/resources/keystore.jks");
        sslContextFactory.setKeyStorePassword("cvidev");
        return sslContextFactory;
    }
}
