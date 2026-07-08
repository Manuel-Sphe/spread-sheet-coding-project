package sphe.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {

        // file input reading
        try (InputStream in = Main.class.getClassLoader()
                .getResourceAsStream("input.csv")) {
            if (in == null) {
                throw new IllegalArgumentException("file not found");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

                String line;
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(",");
                    System.out.println(split[0] + " " + split[1]);
                }
            }
        }

    }
}