# Shell Tools

Shell Tools is a command-line tool (CLI) for controlling Docker Compose environments.

## Usage

### On Windows

Use the `run.cmd` script to launch commands:

- Example with : stop containers and remove volumes:

  ```bash
  run.cmd docker purge
  ```

You can also rebuild the project using:

```bash
run.cmd rebuild
```

## Commands

<!-- START_AUTO_GENERATED_COMMAND -->
Commande: ./run.cmd updateDoc ? ? ?
```
Usage: updateDoc [-hV] [COMMAND]
CLI tool to update documentation
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  shell  update shell tools documentation

```
Commande: ./run.cmd docker ? ? ?
```
Usage: docker [-hV] [COMMAND]
CLI tool to control Docker Compose
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  start  Starts the Docker platform (docker compose up -d)
  stop   Stops the Docker platform (docker compose down)
  tail   Starts Docker logs (docker compose logs -f)
  purge  Stop dockers & remove volumes

```

<!-- END_AUTO_GENERATED_COMMAND -->

## SonarQube

<!-- START_AUTO_GENERATED_SONARQUBE_REPORT -->

- coverage: 93.3
- alert_status: OK
- bugs: 0
- reliability_rating: 1.0
- code_smells: 0
- duplicated_lines_density: 0.0
- security_rating: 1.0
- ncloc: 674
- vulnerabilities: 0
- security_hotspots: 0
- sqale_rating: 1.0

<!-- END_AUTO_GENERATED_SONARQUBE_REPORT -->

## Development

This project uses [picocli](https://picocli.info/) for command-line parsing. 

