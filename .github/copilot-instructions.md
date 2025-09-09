## Repository snapshot for AI coding agents

This file contains concise, actionable guidance to help an AI agent be productive in this repo (a small Spring Boot Java microservice template).

If a prompt or instruction requires an input, always ask for the input first before proceeding.

High level
- This is a Spring Boot service (main class `com.example.pe.Application`). See `src/main/java/com/example/pe`.
- Web endpoints live under `src/main/java/com/example/pe/controller` (examples: `HelloWorldController` -> GET /hello, `InfoController` -> GET /info).
- Configuration values in `src/main/resources/application.properties` are Maven-filtered placeholders (`@project.artifactId@`, `@project.version@`). Prefer changing metadata in `pom.xml` rather than editing that file directly.
- Docker images built via Jib plugin configured in `pom.xml` (`com.google.cloud.tools:jib-maven-plugin`). The plugin expects environment vars: `ACR`, optionally `ACR_USERNAME` and `ACR_PASSWORD`.

Build / run / test (practical commands)
- Preferred local build/run (use Maven wrapper):
  - Run the app locally: `./mvnw spring-boot:run` (serves on port 8080).
  - Build artifact: `./mvnw clean package`.
  - Run unit tests: `./mvnw test` (reports in `target/surefire-reports`).
- Build a local Docker image with Jib:
  - `export ACR=local && ./mvnw clean compile jib:dockerBuild` -> produces `local/pe/hello-world-java-service:0-SNAPSHOT` by default.
  - If using Podman: `export ACR=local && mvn jib:dockerBuild -Djib.dockerClient.executable=$(which podman)`.
  - Run container: `docker run -p 8080:8080 <image>` or `podman run -p 8080:8080 <image>`.

CI / CD and repo policies
- Azure DevOps pipelines live under `pipelines/` (`pipelines-ci.yml`, `pipelines-cd.yml`, commitlint pipeline). The repo expects Conventional Commits enforced by a pipeline and a commit-msg hook (husky configured via `package.json`).
- Branch protection: `main` requires CI and commitlint (see README and `pipelines/vars-dev.yaml`).

Key project conventions and patterns
- Java version is pinned to 1.8 in `pom.xml` and Spring Boot parent 2.7.18 — keep compatibility when adding dependencies or code.
- Resource filtering: `application.properties` uses Maven placeholders. When changing application name/version prefer updating `pom.xml` or release process rather than directly editing the file.
- Image naming: Jib uses `${env.ACR}/pe/${project.artifactId}:${project.version}`. When updating image tagging, update both `pom.xml` (if needed) and `k8s/chart/values.yaml` which drives deployment templates.
- Controllers are simple Spring MVC `@RestController` / `@Controller` classes returning primitives or maps; follow existing package layout `com.example.pe.controller`.

Integration points to be aware of
- Azure Container Registry (ACR) — environment variable `ACR` is required to publish images in CI and locally when using Jib.
- AKS / Kubernetes — Helm chart and templates under `k8s/chart/` control deployment. Image tag and repository are configured in `k8s/chart/values.yaml`.
- Azure DevOps — pipelines expect certain variables and service connections; changing pipeline structure requires updating `pipelines/` files and README instructions.

Practical examples to copy/paste when making changes
- Add an endpoint: create class under `src/main/java/com/example/pe/controller` and register mapping with `@GetMapping`/`@RequestMapping`.
- Verify app metadata: inspect `src/main/resources/application.properties` (values come from `pom.xml` at build time).
- Build-and-run locally: `./mvnw spring-boot:run` then `curl -v http://localhost:8080/hello` or `curl -v http://localhost:8080/info`.

AI agent rules (project-specific)
- Preserve package structure and `mainClass` defined in `pom.xml` (`com.example.pe.Application`). If changing packages, update `pom.xml` and `k8s` charts accordingly.
- When editing Java code: run `./mvnw test` after changes. When editing build or Docker image fields, run a local Jib build or package to validate.
- Avoid editing generated or build artifacts under `target/` — they are outputs.
- Respect Java 1.8 compatibility and Spring Boot 2.7.x APIs.

Where to look first (quick pointers)
- `pom.xml` — build, plugins (Jib), Java version
- `README.md` — manual and CI/CD hints
- `src/main/java/com/example/pe` — application + controllers
- `src/main/resources/application.properties` — runtime metadata
- `k8s/chart/` and `pipelines/` — deployment and CI/CD wiring

If anything here is unclear or you'd like more detail (examples of patch diffs, tests to add, or mapping between `pom.xml` and chart values), tell me which section to expand.
