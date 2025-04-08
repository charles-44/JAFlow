package org.scem.command.sub.updater;

import org.reflections.Reflections;
import org.scem.command.base.DocumentationBaseCommand;
import org.scem.command.client.SonarQubeClient;
import org.scem.command.constante.SubProject;
import org.scem.command.exception.ExecutionCommandException;
import org.scem.command.util.FileUtils;
import org.scem.command.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.util.Map;
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
        try {
            File readmeFile = getSubProjectReadme();
            String projectDirName = getSubProject().getValue();
            String sonarToken = FileUtils.getPrivateProperties("sonar.project."+ projectDirName + ".token");
            String mvnBinPath = FileUtils.getPrivateProperties("maven.bin.path");
            logger.info("Execute mvn clean verify sonar:sonar ...");
            this.executeCommand(readmeFile.getParentFile(),
                    mvnBinPath,"clean", "verify", "sonar:sonar",
                    "-Dsonar.projectKey=" + this.getSubProject().getValue(),
                    "-Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml" ,
                    "-Dsonar.projectName="+ projectDirName,
                    "-Dsonar.host.url=" + FileUtils.getPrivateProperties("sonar.host.url"),
                    "-Dsonar.token=" + sonarToken);

            logger.info("Start update shell tools documentation");
            String packageToScan = "org.scem.command";
            Reflections reflections = new Reflections(packageToScan);

            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(CommandLine.Command.class).stream().filter(aClass -> aClass.getPackageName().equals(packageToScan)).collect(Collectors.toSet());

            StringBuilder content = new StringBuilder();

            for (Class<?> clazz : classes) {
                Object cmdObj = clazz.getConstructors()[0].newInstance();
                CommandLine cmd = new CommandLine(cmdObj);
                String helpText = cmd.getUsageMessage(CommandLine.Help.Ansi.OFF);

                String name = StringUtils.lowercaseFirstLetter(clazz.getSimpleName());
                name = name.substring(0, name.length() - "Commands".length());

                content.append("Commande: ./run.cmd ").append(name).append(" ? ? ?");

                content.append("\n```\n");
                content.append(helpText);
                content.append("\n```\n");
            }
            replaceInReadme("AUTO_GENERATED_COMMAND", content.toString());
            logger.info("Finish update shell tools documentation for help");


            StringBuilder sonarQubeContent = new StringBuilder("\n");
            SonarQubeClient client = SonarQubeClient.getSonarQubeClient("shell-tools");
            Map<String,String> metrics = client.fetchMetrics();

            metrics.forEach((key,value)->
                sonarQubeContent.append("- ").append(key).append(": ").append(value).append("\n")
            );
            replaceInReadme("AUTO_GENERATED_SONARQUBE_REPORT", sonarQubeContent.toString());
            logger.info("Finish update shell tools documentation for SonarQube");


        } catch (Exception e) {
            throw new ExecutionCommandException("Failed to execute UpdateShellToolsDocumentation", e);
        }

    }

    @Override
    public Logger getLogger() {
        return logger;
    }


    @Override
    public SubProject getSubProject() {
        return SubProject.SHELL;
    }
}