package org.scem.command.document.updater;

import org.reflections.Reflections;
import org.scem.command.base.DocumentationBaseCommand;
import org.scem.command.constante.SubProject;
import org.scem.command.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "shell",
        description = {"update shell tools documentation"}
)
public class UpdateShellToolsDocumentation extends DocumentationBaseCommand implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(UpdateShellToolsDocumentation.class);


    @Override
    public void run() {
        String packageToScan = "org.scem.command";
        Reflections reflections = new Reflections(packageToScan);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(CommandLine.Command.class).stream().filter(aClass -> aClass.getPackageName().equals(packageToScan)).collect(Collectors.toSet());

        StringBuilder content = new StringBuilder();

        for (Class<?> clazz : classes) {
            try {
                Object cmdObj = clazz.getConstructors()[0].newInstance();
                CommandLine cmd = new CommandLine(cmdObj);
                String helpText = cmd.getUsageMessage(CommandLine.Help.Ansi.OFF);

                String name = StringUtils.lowercaseFirstLetter(clazz.getSimpleName());
                name = name.substring(0, name.length() - "Commands".length());

                content.append("Commande: ./run.cmd ").append(name).append(" ? ? ?");

                content.append("\n```\n");
                content.append(helpText);
                content.append("\n```\n");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            replaceInReadme("AUTO_GENERATED_COMMAND", content.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Logger getLogger() {
        return logger;
    }


    public static void main(String[] args) {
        UpdateShellToolsDocumentation cmd = new UpdateShellToolsDocumentation();
        cmd.run();
    }

    @Override
    public SubProject getSubProject() {
        return SubProject.SHELL;
    }
}