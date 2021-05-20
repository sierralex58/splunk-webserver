

import io.javalin.Javalin;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String... args) throws Exception {
        AtomicInteger received = new AtomicInteger();
        Javalin j = Javalin.create().start(38888);
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
}
