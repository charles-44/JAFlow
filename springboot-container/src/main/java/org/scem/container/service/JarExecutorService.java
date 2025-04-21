package org.scem.container.service;

import org.scem.container.dto.JarProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class JarExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(JarExecutorService.class);

    public JarProcess kill(String artifactId) {

        JarProcess jarProcess = new JarProcess();
        File directory =  this.getJarDirectory();
        jarProcess.setDirPath(directory.getAbsolutePath());
        jarProcess.setName(artifactId);

        Stream<ProcessHandle> processes = ProcessHandle.allProcesses();

        processes.forEach(process -> {
            ProcessHandle.Info info = process.info();
            if (info.commandLine().isPresent()){
                String commandLine = info.commandLine().get();
                if (commandLine.contains(artifactId)){
                    jarProcess.setDestroyed(process.destroy());
                    jarProcess.setPid( process.pid());
                    jarProcess.setCommandLine(commandLine);
                }
                Optional<String> optionalFilename =  this.getJarNames().stream().filter(s -> s.startsWith(artifactId + "-")).findFirst();
                if(optionalFilename.isPresent()) {
                    Paths.get(directory.getAbsolutePath(), optionalFilename.get()).toFile().delete();
                    jarProcess.setName(optionalFilename.get());
                }
            }
        });
        return jarProcess;
    }

    public JarProcess executeJar(String artifactId) throws Exception {
        JarProcess jarProcess = new JarProcess();
        File directory =  this.getJarDirectory();
        List<String> names=  this.getJarNames();
        Optional<String> optionalFilename = names.stream().filter(s -> s.startsWith(artifactId + "-")).findFirst();
        if (optionalFilename.isPresent()){
            executeJar(jarProcess,directory,optionalFilename.get());
        }
        jarProcess.setArtifactId(artifactId);
        return jarProcess;
    }
    private JarProcess executeJar(JarProcess jarProcess,File directory , String filename) throws IOException {
        String filepath = Paths.get(directory.getAbsolutePath() ,filename).toFile().getAbsolutePath();
        ProcessBuilder pb = new ProcessBuilder("java","-jar", filepath);
        pb.directory(directory);
        Process process = pb.start();
        jarProcess.setPid(process.pid());
        jarProcess.setFilepath(filepath);
        jarProcess.setName(filename);
        jarProcess.setDirPath(directory.getAbsolutePath());
        return jarProcess;
    }

    public File getJarDirectory() {
        String dirPath = System.getProperty("user.dir");
        File directory = Paths.get(dirPath,"springboot-jars").toFile();
        if (!directory.exists()) {
            directory.mkdirs(); // Créer le répertoire si il n'existe pas déjà
        }
        return directory;
    }

    public List<String> getJarNames() {
        File directory = getJarDirectory();
        File[] jars = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        return Arrays.stream(jars).map(file -> file.getName()).toList();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("✅ Application started, method executed....");
        String dirPath = this.getJarDirectory().getAbsolutePath();
        List<String> names = this.getJarNames();

        logger.info("✅ Application started, dirPath: {}",dirPath);
        logger.info("✅ Application started, names: {}",String.join(", ", names));

        names.forEach(name->{
            try {
                logger.info("✅ Application started, executing jar: {}",name);
                this.executeJar(new JarProcess(),this.getJarDirectory(),name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
