package dev.grevend.count;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

@Command(name = "count", version = "count 1.0", description = "Count the human readable characters, lines or words from stdin or a file and write the number to stdout or a file.")
public class Count implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Count()).execute("");
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        return 0;
    }

}
