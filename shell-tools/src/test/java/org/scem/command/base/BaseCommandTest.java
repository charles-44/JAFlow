package org.scem.command.base;

import org.junit.jupiter.api.DisplayName;

@SuppressWarnings({"java:S2187"}) // TestCases should contain tests , but in our case there are errors
@DisplayName("BaseCommand Test")
class BaseCommandTest {
    //empty
    //because: [ERROR] [SUREFIRE] std/in stream corrupted
    // [ERROR] [SUREFIRE] std/in stream corrupted
    // [ERROR] org.apache.maven.surefire.booter.SurefireBooterForkException: There was an error in the forked process
    // [ERROR] [SUREFIRE] std/in stream corrupted
    // [ERROR]         at org.apache.maven.plugin.surefire.booterclient.ForkStarter.fork(ForkStarter.java:628)
    // [ERROR]         at org.apache.maven.plugin.surefire.booterclient.ForkStarter.run(ForkStarter.java:285)
    // [ERROR]         at org.apache.maven.plugin.surefire.booterclient.ForkStarter.run(ForkStarter.java:250)
}
