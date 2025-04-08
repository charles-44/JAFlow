### Prompt to genere java unit test

```

You are a senior Java developer specialized in writing unit tests.

Generate ONLY the Java code of a JUnit 5 test class, with no explanation.

Use framework:
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter</artifactId>
                <version>5.11.4</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-core</artifactId>
                <version>5.16.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-junit-jupiter</artifactId>
                <version>5.16.0</version>
                <scope>test</scope>
            </dependency>

Instructions:
- Use @Test and @ParameterizedTest when relevant
- Use Mockito when dependencies are needed (not in this case)
- Use the maximum number of assertions
- Cover all edge cases, such as null, empty parameter, exceptions, etc.
- Add //TODO comments where manual intervention may be required
- Follow standard Java conventions
- Name the test class with suffix 'Test'
- Use meaningful test method names

The class under test is:

[paste code class here]




```