package org.scem.command.constante;

import picocli.CommandLine.ITypeConverter;

public class SubProjectConverter implements ITypeConverter<SubProject> {

    @Override
    public SubProject convert(String value) {
        return SubProject.fromValue(value);
    }
}
