package calculator;

import java.calculator.commands.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Calculator {
    public static void main(String[] args) {
        String inputFile = args.length > 0 ? args[0] : null;

        try {
            CommandFactory factory = new CommandFactory("/commands.config");
            Context context = new Context();

            BufferedReader reader = (inputFile != null)
                    ? new BufferedReader(new FileReader(inputFile))
                    : new BufferedReader(new InputStreamReader(System.in));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(" ");
                String commandName = parts[0];
                String[] commandArgs = new String[parts.length - 1];
                System.arraycopy(parts, 1, commandArgs, 0, commandArgs.length);

                try {
                    Command command = factory.createCommand(commandName);
                    command.execute(context, commandArgs);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
        }
    }
}
