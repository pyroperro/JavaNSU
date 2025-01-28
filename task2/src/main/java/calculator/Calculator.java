package calculator;

import calculator.commands.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.logging.*;

public class Calculator {
    public static void main(String[] args) {
        String inputFile = args.length > 0 ? args[0] : null;
        Logger logger = Logger.getLogger(Calculator.class.getName());
        try {
            FileHandler fileHandler = new FileHandler("src/main/resources/calculator.log", false);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);

            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handler);
                }
            }

        } catch (Exception e) {
            System.err.println("Could not configure FileHandler: " + e.getMessage() + " Logs will not be saved.");
        }
        logger.info("File handler configured. Logs will be saved to \"calculator.log\"");

        try {
            CommandFactory factory = new CommandFactory("/commands.config");
            logger.info("Loaded configuration file with command mappings");
            Context context = new Context();

            BufferedReader reader;
            if (inputFile != null) {
                reader = new BufferedReader(new FileReader(inputFile));
                logger.info("Loaded command file \"" + inputFile + "\" successfully");
            } else {
                reader = new BufferedReader(new InputStreamReader(System.in));
                logger.info("No input file or incorrect path provided. Console input mode initialized");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    logger.info("Comment line \"" + line + "\" skipped");
                    continue;
                }

                String[] parts = line.split(" ");
                String commandName = parts[0];
                String[] commandArgs = new String[parts.length - 1];
                System.arraycopy(parts, 1, commandArgs, 0, commandArgs.length);

                try {
                    Command command = factory.createCommand(commandName);
                    logger.info("Command \"" + line + "\" parsed");
                    command.execute(context, commandArgs);
                    logger.info("Command \"" + line + "\" executed successfully");
                } catch (Exception e) {
                    logger.warning("Could not execute command: " + e.getMessage() + ". Command skipped");
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            System.err.println("Fatal error: " + e.getMessage());
        }
    }
}
