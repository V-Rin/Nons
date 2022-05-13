package org.vrin.nons;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class NonsApplication {
    public static void main(String[] args)throws IOException
    {
        SpringApplication.run(NonsApplication.class, args);
    }
}
