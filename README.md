# NHG Server

## Description

## Installation
- clone [server](https://gitlab.com/ballstorm/ballstorm-server) repository.
- open cloned repository.
- if you don't have SDK installed
  - install directly from your IDE.
  - install from website [Download SDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Configure each service under `resources/application.yml` on the service folder
  - Change the port `server > port` if these are busy on your machine
  - Change database credentials at `spring > datasoruce`.
- Create a database for each service that requires it, check this by looking under `resources/application.yml`
  then check `spring > datasource > url`, there will be something like `jdbc:postgresql://host:port/db_name`,
  finally create a database with the name you find under the heading `db_name`
- Start the services you need.
