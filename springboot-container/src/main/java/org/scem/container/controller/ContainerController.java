package org.scem.container.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;



@Controller
public class ContainerController {

    private static final Logger logger = LoggerFactory.getLogger(ContainerController.class);

    @GetMapping("/")
    public String welcome(Model model) {

        model.addAttribute("dirPath", getJarDirectory().getAbsolutePath());
        model.addAttribute("names", getJarNames());

        return "welcome"; // Sans l'extension .html
    }


    @GetMapping("/kill")
    public String kill(@RequestParam("artifactId") String artifactId, Model model) {

        File directory = getJarDirectory();
        model.addAttribute("dirPath", directory.getAbsolutePath());
        model.addAttribute("name", artifactId);

        Stream<ProcessHandle> processes = ProcessHandle.allProcesses();

        processes.forEach(process -> {
            ProcessHandle.Info info = process.info();
            if (info.commandLine().isPresent()){
                String commandLine = info.commandLine().get();
                if (commandLine.contains(artifactId)){
                    model.addAttribute("destroyed", process.destroy());
                    model.addAttribute("pid", process.pid());
                    model.addAttribute("commandLine", commandLine);

                }
                Optional<String> optionalFilename = getJarNames().stream().filter(s -> s.startsWith(artifactId + "-")).findFirst();
                if(optionalFilename.isPresent()) {
                    Paths.get(directory.getAbsolutePath(), optionalFilename.get()).toFile().delete();
                }
            }
        });
        return "kill"; // Sans l'extension .html
    }


    @GetMapping("/launch")
    public String launch(@RequestParam("artifactId") String artifactId, Model model) throws IOException {

        File directory = getJarDirectory();
        List<String> names= getJarNames();
        Optional<String> optionalFilename = names.stream().filter(s -> s.startsWith(artifactId + "-")).findFirst();

        if (optionalFilename.isPresent()){
            String filepath = Paths.get(directory.getAbsolutePath() ,optionalFilename.get()).toFile().getAbsolutePath();
            ProcessBuilder pb = new ProcessBuilder("java","-jar", filepath);
            pb.directory(directory);
            Process process = pb.start();

            model.addAttribute("pid", process.pid());
            model.addAttribute("filename", filepath);

        }

        model.addAttribute("dirPath", directory.getAbsolutePath());
        model.addAttribute("name", artifactId);
        return "launch"; // Sans l'extension .html
    }


    private File getJarDirectory() {
        String dirPath = System.getProperty("user.dir");
        File directory = Paths.get(dirPath,"springboot-jars").toFile();
        if (!directory.exists()) {
            directory.mkdirs(); // Créer le répertoire si il n'existe pas déjà
        }
        return directory;
    }

    private List<String> getJarNames() {
        File directory = getJarDirectory();
        File[] jars = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        return Arrays.stream(jars).map(file -> file.getName()).toList();
    }

}
