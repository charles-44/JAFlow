package org.scem.command;

import org.reflections.Reflections;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.sub.docker.DockerPurgeCommand;
import org.scem.command.sub.docker.DockerStartCommand;
import org.scem.command.sub.docker.DockerStopCommand;
import org.scem.command.sub.docker.DockerTailCommand;
import org.scem.command.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "help",
        mixinStandardHelpOptions = true,
        version = {"help 1.0"},
        description = {"Help for commands"}
)
public class HelpCommands implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HelpCommands.class);


    public static String getHelpCommands(String delimiter) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        String packageToScan = "org.scem.command";
        Reflections reflections = new Reflections(packageToScan);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(CommandLine.Command.class).stream().filter(aClass -> aClass.getPackageName().equals(packageToScan)).collect(Collectors.toSet());

        StringBuilder content = new StringBuilder();

        for (Class<?> clazz : classes) {

            if (! clazz.equals(HelpCommands.class)) {
                Object cmdObj = clazz.getConstructors()[0].newInstance();
                CommandLine cmd = new CommandLine(cmdObj);
                String helpText = cmd.getUsageMessage(CommandLine.Help.Ansi.OFF);

                String name = StringUtils.lowercaseFirstLetter(clazz.getSimpleName());
                name = name.substring(0, name.length() - "Commands".length());

                content.append("Commande: ./run.cmd ").append(name).append(" ? ? ?");

                content.append(delimiter);
                content.append(helpText);
                content.append(delimiter);
            }
        }

        return content.toString();
    }

    public void run() {
        try {
            logger.info("\n{}", getHelpCommands("\n"));
        } catch (Exception e) {
            throw new ExecutionCommandException("Unable to generate help",e);
        }
    }


    public static void main(String[] args) {
        System.exit((new CommandLine(new HelpCommands())).execute(args));
    }
}
