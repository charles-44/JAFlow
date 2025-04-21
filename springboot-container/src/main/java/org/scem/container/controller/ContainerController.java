package org.scem.container.controller;

import org.scem.container.dto.JarProcess;
import org.scem.container.service.JarExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JarExecutorService jarExecutorService;

    @GetMapping("/")
    public String welcome(Model model) {

        model.addAttribute("dirPath", this.jarExecutorService.getJarDirectory().getAbsolutePath());
        model.addAttribute("names",  this.jarExecutorService.getJarNames());

        return "welcome"; // Sans l'extension .html
    }


    @GetMapping("/kill")
    public String kill(@RequestParam("artifactId") String artifactId, Model model) {
        JarProcess jarProcess = this.jarExecutorService.kill(artifactId);
        model.addAttribute("jarProcess", jarProcess);
        return "kill"; // Sans l'extension .html
    }


    @GetMapping("/launch")
    public String launch(@RequestParam("artifactId") String artifactId, Model model) throws Exception {
        JarProcess jarProcess = this.jarExecutorService.executeJar(artifactId);
        model.addAttribute("jarProcess", jarProcess);
        return "launch"; // Sans l'extension .html
    }

}
