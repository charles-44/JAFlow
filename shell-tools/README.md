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
Commande: ./run.cmd updateDoc ? ? ?
```
Usage: updateDoc [-hV] [COMMAND]
CLI tool to update documentation
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
Commands:
  shell  update shell tools documentation

```

<!-- END_AUTO_GENERATED_COMMAND -->








## Development

This project uses [picocli](https://picocli.info/) for command-line parsing. 

