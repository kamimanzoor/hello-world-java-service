# alcl-java-template-function

This repository is meant to be used as a template microservice implemented using [Spring Boot](https://spring.io/projects/spring-boot) framework in Java. The purpose is to have a service which we may use for testing pipelines and cluster functionality.

## Running Service Locally
We are using Maven and its wrapper to make life easy. In order to spin-up the service locally, all we need to do is to run the following command:
```bash
./mvnw spring-boot:run
```
The service will be up and running on port `8080`. We may call the service using the following command:
```bash
curl -v http://localhost:8080/hello 
```
If everything goes fine, a `Hello, World!` response should be received.

## Building & Running Docker Image Locally
We are using [jib plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) to build container images. In order to build docker image locally, following command need to be executed:
```bash
export ACR=local && ./mvnw clean compile jib:dockerBuild
```

If you are using podman, you may use the following command:
```bash
export ACR=local && mvn jib:dockerBuild -Djib.dockerClient.executable=$(which podman)
```

The above command should publish `local/pe/hello-world-java-service:0-SNAPSHOT`
image to local docker daemon.

The image can then be executed by running:
```bash
docker run -p 8080:8080 local/pe/hello-world-java-service:0-SNAPSHOT
```

If you are running podman
```bash
podman run -p 8080:8080 local/pe/hello-world-java-service:0-SNAPSHOT
```

## Conventional Commits & Versioning
We are using [semver](https://semver.org/) for versioning the artifacts from main build/releases. The branch build artifacts are tagged as <git-sha>. Moreover, we have decided to use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) specification for git commits. 
We have a linter in the pipeline which ensures that commits follow the convention. In order to provide help to developers locally, we have provided a commit-msg git hook. This hook (after adding locally) will lint the git commits and provides feedback in case a commit message doesnot follow the standard.
We are using [husky](https://typicode.github.io/husky/#/) for sharing git hooks. The following commands need to be executed in order to add git hooks locally. You need to be on the root of git repo (i.e., same level as `.husky` folder). The following expects [Node.js](https://nodejs.org/en/download/) to be installed on the machine.
```bash
npm i husky -D # Install husky
npm run prepare # Enable Git hooks
```
The above commands should enable the Git hooks placed under `.husk` directory. We may verify by making a git commit which should fail like below:
```bash
λ git commit --allow-empty -m "should fail commit"
npx: installed 184 in 23.075s
⧗   input: should fail commit
✖   subject may not be empty [subject-empty]
✖   type may not be empty [type-empty]

✖   found 2 problems, 0 warnings
ⓘ   Get help: https://github.com/conventional-changelog/commitlint/#what-is-commitlint

husky - commit-msg script failed (code 1)
```

The following commit should work fine:
```bash
λ git commit --allow-empty -m "feat: should pass commit"
```

For skipping git hooks, you may simply pass `-n/--no-verify` flag to git command.

In order to add more git hooks in the future, we may either add them manually inside the `.husky` directory or run the following command. The following expects [Node.js](https://nodejs.org/en/download/) to be installed on the machine.
```bash
echo "./mvnw test" > .husky/pre-commit
git add .husky/pre-commit
```