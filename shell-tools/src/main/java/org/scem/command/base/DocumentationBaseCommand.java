package org.scem.command.base;

import org.scem.command.constante.SubProject;
import org.scem.command.exception.FileCreationException;
import org.scem.command.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class DocumentationBaseCommand extends BaseCommand {

    public abstract SubProject getSubProject();

    public File getSubProjectReadme() throws IOException {
        File directory = FileUtils.getRootProjectDirectory();
        File file = Paths.get(directory.getAbsolutePath(), getSubProject().getValue(),"README.md").toFile();

        if (!file.exists()){
            boolean isCreated = file.createNewFile();
            if (!isCreated){
                throw new FileCreationException(file);
            }
        }
        return file;
    }

    public File replaceInReadme(String delimiter, String contentToPut) throws IOException {
        File readmeFile = getSubProjectReadme();

        String startDelimiter = "<!-- START_"+delimiter+" -->";
        String endDelimiter = "<!-- END_"+delimiter+" -->";

        String content = Files.readString(readmeFile.toPath());

        if (!content.contains(startDelimiter) && !content.contains(endDelimiter)){
            content += startDelimiter + endDelimiter;
        }

        String result = content.substring(0, content.indexOf(startDelimiter)) +
                startDelimiter + "\n"+
                contentToPut + "\n" +
                endDelimiter  +
                content.substring(content.indexOf(endDelimiter) + endDelimiter.length());

        Files.writeString(readmeFile.toPath(), result, StandardCharsets.UTF_8);
        return readmeFile;
    }

}
