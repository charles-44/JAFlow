@echo off
mvn clean verify sonar:sonar ^
  -Dsonar.projectKey=shell-tools ^
  -Dsonar.projectName=shell-tools ^
  -Dsonar.host.url=http://localhost:9000 ^
  -Dsonar.token=sqp_cf6e90f60d2fe914db07d6b4254759b8132d20d7