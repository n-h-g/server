<p align="center"> 
  <img src="https://github.com/n-h-g/.github/blob/main/assets/nhg_logo_rainbow.png" width="250"> <br>
</p>

# NHG Server 
![example workflow](https://github.com/n-h-g/server/actions/workflows/gradle.yml/badge.svg?style=flat-square)
> Issues status
> 
> [![Bug issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20bug?style=flat-square&color=%23B60205&label=bug)](https://github.com/n-h-g/server/labels/type%3A%20bug)
> [![Performance issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20performance?style=flat-square&color=%23D93F0B&label=performance)](https://github.com/n-h-g/server/labels/type%3A%20performance)
> [![Refactoring issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20refactoring?style=flat-square&color=%23E99695&label=refactoring)](https://github.com/n-h-g/server/labels/type%3A%20refactoring)
> [![Security issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20security?style=flat-square&color=%230E8A16&label=security)](https://github.com/n-h-g/server/labels/type%3A%20security)
> [![Documentation issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20documentation?style=flat-square&color=%23006B75&label=documentation)](https://github.com/n-h-g/server/labels/type%3A%20documentation)
> [![Feature issues](https://img.shields.io/github/issues/n-h-g/server/type%3A%20feature?style=flat-square&color=%230052CC&label=feature)](https://github.com/n-h-g/server/labels/type%3A%20feature)
> [![Good first issues](https://img.shields.io/github/issues/n-h-g/server/good%20first%20issue?style=flat-square&color=%239937DC&label=good%20first%20issue)](https://github.com/n-h-g/server/labels/good%20first%20issue)


> Join our Discord server
>
> [![Discord](https://img.shields.io/discord/953057939743207454?style=for-the-badge&logo=discord&color=7289DA&logoColor=fff)](https://discord.gg/PSbCGaVWr5)

---

### Table Of Contents


* [📃 Description](#description)
* [⬇️ Installation](#installation)
* [🧰 Build & Run](#build-run)
  + [🛠️ Build the application](#build)
  + [▶️ Run the application](#run)
* [✨ Contributors](#contributors)
  
<span id="description"></span>
## 📃 Description

<span id="installation"></span>
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


<span id="build-run"></span>
## 🧰 Build & Run


<span id="build"></span>
### 🛠️ Build the application
To build this application:
- Open a terminal in the folder where you cloned the repository
- Run the `gradle build` command.

<span id="run"></span>
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

<span id="contributors"></span>
## ✨ Contributors
Contributions are welcomed!

<a href="https://github.com/n-h-g/server/graphs/contributors"><img src="https://contributors-img.web.app/image?repo=n-h-g/server"></a>


