package ru.chemarev.andrey;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.*;

public class Main {

    public static void main(String... args) {
        Main main = new Main();

        try {
            main.execute();
        } catch (IOException e) {
            System.err.println( e.getMessage() );
        }
    }

    public void execute() throws IOException {

        try (InputStream stream =
                     getClass().getClassLoader().getResourceAsStream("hello.txt");
             InputStreamReader reader =
                     new InputStreamReader(stream, Charsets.UTF_8)) {

            try {
                System.out.println(CharStreams.toString(reader));
            } catch (IOException e) {
                System.err.println( e.getMessage() );
            }
        }

    }

}