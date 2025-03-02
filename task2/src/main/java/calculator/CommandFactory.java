package calculator;

import calculator.commands.Command;
import calculator.exceptions.UnsupportedOperatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<String, String> commandNames = new HashMap<>();

    public CommandFactory(String configFile) throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream(configFile)) {
            if (inputStream == null) {
                throw new IOException("Config file not found");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    commandNames.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }

    public Command createCommand(String commandName) throws Exception {
        String className = commandNames.get(commandName);
        if (className == null) {
            throw new UnsupportedOperatorException(commandName);
        }
            Class<?> commandClass = Class.forName(className);
        return (Command) commandClass.getDeclaredConstructor().newInstance();
    }
}
