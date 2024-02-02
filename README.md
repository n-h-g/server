<p align="center"> 
  <img src="https://github.com/n-h-g/.github/blob/main/assets/nhg_logo_rainbow.png" width="250"> <br>
</p>

# NHG Server 
> Issues status
> 
> [![Bug issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20bug?color=%23B60205&label=bug)](https://github.com/n-h-g/server/labels/type%3A%20bug)
> [![Performance issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20performance?color=%23D93F0B&label=performance)](https://github.com/n-h-g/server/labels/type%3A%20performance)
> [![Refactoring issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20refactoring?color=%23E99695&label=refactoring)](https://github.com/n-h-g/server/labels/type%3A%20refactoring)
> [![Security issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20security?color=%230E8A16&label=security)](https://github.com/n-h-g/server/labels/type%3A%20security)
> [![Documentation issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20documentation?color=%23006B75&label=documentation)](https://github.com/n-h-g/server/labels/type%3A%20documentation)
> [![Feature issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20feature?color=%230052CC&label=feature)](https://github.com/n-h-g/server/labels/type%3A%20feature)

<a href="https://discord.gg/PSbCGaVWr5"><img src="https://img.shields.io/badge/join-discord-7289da.svg?sanitize=true"></a>
---

## ⬇️ Installation 
Follow these steps to install everything you need to run **NHG Server**:
- Clone the repository.
- Download [Docker](https://www.docker.com/).
- Java SDK 21
  - Install directly from your IDE.
  - Install from website [Download SDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html).
- Gradle
  - Install directly from your IDE.
  - Install from [Gradle website](https://gradle.org/install/) a Gradle version compatible with the Java version.

> [!NOTE]  
> Lombok is used, although it is not necessary to start the application, if you intend to work on the code, we recommend installing an Annotation Processing plugin for Lombok on your IDE.

---

## 🧰 Build & Run

### 🛠️ Build the application
To build this application:
- Open a terminal in the folder where you cloned the repository
- Run the `gradle build` command.

### ▶️ Run the application
To run this application:
- Build the application.
- Start docker.
- Open a terminal in the folder where you cloned the repository.
- Run the `docker-compose up` command.
  
> [!TIP]  
> If you're using IntelliJ IDEA you can directly run docker-compose from the IDE.
> 
> Open the `docker-compose.yml` and press the arrows button next to `services`.

> [!IMPORTANT]
> When you run the `docker-compose up` command docker creates the images and caches them and then starts the services, or, if they are already available in the cache, takes them from the cache and starts the services. If changes are made, if you run the command directly, they will not be visible since the cached images will be used. To solve this run the `docker-compose build --no-cache` command to recreate the images (remember to build the application first).

---


