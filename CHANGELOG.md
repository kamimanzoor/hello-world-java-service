# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

## [2.2.0](https://github.com/kamimanzoor/hello-world-java-service/compare/2.1.0...2.2.0) (2025-08-12)


### Features

* add conditional checks for release steps in CI workflow ([26fe995](https://github.com/kamimanzoor/hello-world-java-service/commit/26fe995832ffad3d2142ce86c8dbcf0a63099389))

## [2.1.0](https://github.com/kamimanzoor/hello-world-java-service/compare/2.0.0...2.1.0) (2025-08-12)


### Features

* add body_path for release notes in GitHub action ([f6b1aa3](https://github.com/kamimanzoor/hello-world-java-service/commit/f6b1aa3a0c9085f68e60f55a548c4e00bbdd786a))
* add deploy-service workflow to trigger deployments via workflow dispatch and push events ([f5bf056](https://github.com/kamimanzoor/hello-world-java-service/commit/f5bf0569bbe5774520f27d73252943a35b032127))
* add github action workflows ([722826c](https://github.com/kamimanzoor/hello-world-java-service/commit/722826ca70988eae353a4cf3e0815e3c614f6ed5))
* add initial GitHub Actions CI workflow configuration ([950005c](https://github.com/kamimanzoor/hello-world-java-service/commit/950005c58f958118223eddd6b2859527f3358aac))
* add output display for Release Please action and fix manifest formatting ([0fb0fed](https://github.com/kamimanzoor/hello-world-java-service/commit/0fb0fedc9bfbbe3585d2b0b56b4d5062cff3e48d))
* add permissions for contents and pull-requests in CI workflow ([610d147](https://github.com/kamimanzoor/hello-world-java-service/commit/610d147005a7203295c4b4963267e6f9048cf147))
* add release configuration and manifest files ([cd5c005](https://github.com/kamimanzoor/hello-world-java-service/commit/cd5c0057306727adab97c08f81c2af123ea024bd))
* add release-please configuration and manifest files ([5c86d3b](https://github.com/kamimanzoor/hello-world-java-service/commit/5c86d3bb92df8f0cc1bb82f2d26930b2a606bfc6))
* add templates for commit linting and CI/CD pipelines ([4c6bc25](https://github.com/kamimanzoor/hello-world-java-service/commit/4c6bc258f725cc710a3231f6a3edc217c3c46a92))
* awesome feature ([54c2d4e](https://github.com/kamimanzoor/hello-world-java-service/commit/54c2d4e63624fedb39a1108a374d0c8897f25928))
* enhance release logic by setting GIT_REF ([1fa3c33](https://github.com/kamimanzoor/hello-world-java-service/commit/1fa3c33e24053970e7f6cc4f7479f8d082594407))
* initial commit for hello-world service ([813a44b](https://github.com/kamimanzoor/hello-world-java-service/commit/813a44b85a01cae41ef61ad68c58b3c53349f30e))
* replace Node setup with Release Please ([388ca8c](https://github.com/kamimanzoor/hello-world-java-service/commit/388ca8cea38daae719cbb2257ab3a6a74b7bbd88))
* set default image version in deploy-service workflow ([5ae5785](https://github.com/kamimanzoor/hello-world-java-service/commit/5ae5785e77af19d7d1cd3b00926770082ddab05e))
* streamline deploy-service workflow by removing push and pull_request triggers ([cc37a5f](https://github.com/kamimanzoor/hello-world-java-service/commit/cc37a5f14c6038d5b7e73ef17c7f52183f5d50e4))
* update CI workflow to enhance variable setting and image building steps ([40c034a](https://github.com/kamimanzoor/hello-world-java-service/commit/40c034a97359d930903e540594d4df13cd45220b))
* update deploy-template inputs to make environment optional and set default image version ([84a1db8](https://github.com/kamimanzoor/hello-world-java-service/commit/84a1db8e12b31afe78ff2a4904aada88b695e636))
* update greeting message in HelloWorldController and corresponding test ([48d26c3](https://github.com/kamimanzoor/hello-world-java-service/commit/48d26c329b9e915cd610554edd17dfbc21a10884))
* update permissions to write-all for CI workflow ([728178e](https://github.com/kamimanzoor/hello-world-java-service/commit/728178e3d2740c9d3baf14f5d152170415089dbc))
* update release-please action reference to googleapis ([1e1d05f](https://github.com/kamimanzoor/hello-world-java-service/commit/1e1d05f54f32c1e1c9bdcb24df13286d0cf3cbdc))
* update release-please configuration and clean up manifest file ([9afa45b](https://github.com/kamimanzoor/hello-world-java-service/commit/9afa45b518431f565129a59fd55497996e99b874))


### Bug Fixes

* change service type from LoadBalancer to ClusterIP ([82e3f96](https://github.com/kamimanzoor/hello-world-java-service/commit/82e3f96686d96793003c6f797ca75f789e5a44a2))
* remove default values for flexibility ([88f7cc5](https://github.com/kamimanzoor/hello-world-java-service/commit/88f7cc52c168a01f41f38fdc61a988dd4d0e72c4))
* update ([4fa8b8a](https://github.com/kamimanzoor/hello-world-java-service/commit/4fa8b8af95655caf51dfab3486ee348f44cb2ac1))
* update cluster variable to match development environment ([d4d91c8](https://github.com/kamimanzoor/hello-world-java-service/commit/d4d91c827b45d1da4f096bb5e2a682074fb9f8bf))
* update container image to use Azure CLI for version test ([283d1eb](https://github.com/kamimanzoor/hello-world-java-service/commit/283d1eb9980458dac58f64e5c54c1868f688b3cf))
* update image tag ([ef080ad](https://github.com/kamimanzoor/hello-world-java-service/commit/ef080ad28699571e0ad24170033b465c8ce86cc3))
* update permissions and branch settings in CI workflow ([1d407d6](https://github.com/kamimanzoor/hello-world-java-service/commit/1d407d64639eadca042f5bc765c05fb5f1f5a856))
* update permissions in CI workflow to use write-all ([80c2728](https://github.com/kamimanzoor/hello-world-java-service/commit/80c27286e9ffbe0fd6f2ae5de41f3e69dbf1ee3b))
* update pipeline templates to use environment-specific variables ([10325a1](https://github.com/kamimanzoor/hello-world-java-service/commit/10325a18216473b573e8165ae3f6ab44cbdc8f96))
* update repository name and enhance CI/CD pipeline configuration ([56f4c8a](https://github.com/kamimanzoor/hello-world-java-service/commit/56f4c8af3112b64bad661fc45af09b4da2f31582))
* update the file path ([99f36cc](https://github.com/kamimanzoor/hello-world-java-service/commit/99f36cc6f67014978a2e64331b59b4054f6e6be5))

## [1.2.0](https://github.com/kamimanzoor/hello-world-java-service/compare/1.1.1...1.2.0) (2025-08-11)


### Features

* add body_path for release notes in GitHub action ([f6b1aa3](https://github.com/kamimanzoor/hello-world-java-service/commit/f6b1aa3a0c9085f68e60f55a548c4e00bbdd786a))

### [1.1.1](https://github.com/kamimanzoor/hello-world-java-service/compare/1.1.0...1.1.1) (2025-08-11)


### Bug Fixes

* update image tag ([ef080ad](https://github.com/kamimanzoor/hello-world-java-service/commit/ef080ad28699571e0ad24170033b465c8ce86cc3))

## 1.1.0 (2025-08-11)


### Features

* add deploy-service workflow to trigger deployments via workflow dispatch and push events ([f5bf056](https://github.com/kamimanzoor/hello-world-java-service/commit/f5bf0569bbe5774520f27d73252943a35b032127))
* add github action workflows ([722826c](https://github.com/kamimanzoor/hello-world-java-service/commit/722826ca70988eae353a4cf3e0815e3c614f6ed5))
* add initial GitHub Actions CI workflow configuration ([950005c](https://github.com/kamimanzoor/hello-world-java-service/commit/950005c58f958118223eddd6b2859527f3358aac))
* add templates for commit linting and CI/CD pipelines ([4c6bc25](https://github.com/kamimanzoor/hello-world-java-service/commit/4c6bc258f725cc710a3231f6a3edc217c3c46a92))
* enhance release logic by setting GIT_REF ([1fa3c33](https://github.com/kamimanzoor/hello-world-java-service/commit/1fa3c33e24053970e7f6cc4f7479f8d082594407))
* initial commit for hello-world service ([813a44b](https://github.com/kamimanzoor/hello-world-java-service/commit/813a44b85a01cae41ef61ad68c58b3c53349f30e))
* set default image version in deploy-service workflow ([5ae5785](https://github.com/kamimanzoor/hello-world-java-service/commit/5ae5785e77af19d7d1cd3b00926770082ddab05e))
* streamline deploy-service workflow by removing push and pull_request triggers ([cc37a5f](https://github.com/kamimanzoor/hello-world-java-service/commit/cc37a5f14c6038d5b7e73ef17c7f52183f5d50e4))
* update CI workflow to enhance variable setting and image building steps ([40c034a](https://github.com/kamimanzoor/hello-world-java-service/commit/40c034a97359d930903e540594d4df13cd45220b))
* update deploy-template inputs to make environment optional and set default image version ([84a1db8](https://github.com/kamimanzoor/hello-world-java-service/commit/84a1db8e12b31afe78ff2a4904aada88b695e636))
* update greeting message in HelloWorldController and corresponding test ([48d26c3](https://github.com/kamimanzoor/hello-world-java-service/commit/48d26c329b9e915cd610554edd17dfbc21a10884))


### Bug Fixes

* change service type from LoadBalancer to ClusterIP ([82e3f96](https://github.com/kamimanzoor/hello-world-java-service/commit/82e3f96686d96793003c6f797ca75f789e5a44a2))
* remove default values for flexibility ([88f7cc5](https://github.com/kamimanzoor/hello-world-java-service/commit/88f7cc52c168a01f41f38fdc61a988dd4d0e72c4))
* update ([4fa8b8a](https://github.com/kamimanzoor/hello-world-java-service/commit/4fa8b8af95655caf51dfab3486ee348f44cb2ac1))
* update cluster variable to match development environment ([d4d91c8](https://github.com/kamimanzoor/hello-world-java-service/commit/d4d91c827b45d1da4f096bb5e2a682074fb9f8bf))
* update container image to use Azure CLI for version test ([283d1eb](https://github.com/kamimanzoor/hello-world-java-service/commit/283d1eb9980458dac58f64e5c54c1868f688b3cf))
* update permissions and branch settings in CI workflow ([1d407d6](https://github.com/kamimanzoor/hello-world-java-service/commit/1d407d64639eadca042f5bc765c05fb5f1f5a856))
* update permissions in CI workflow to use write-all ([80c2728](https://github.com/kamimanzoor/hello-world-java-service/commit/80c27286e9ffbe0fd6f2ae5de41f3e69dbf1ee3b))
* update pipeline templates to use environment-specific variables ([10325a1](https://github.com/kamimanzoor/hello-world-java-service/commit/10325a18216473b573e8165ae3f6ab44cbdc8f96))
* update repository name and enhance CI/CD pipeline configuration ([56f4c8a](https://github.com/kamimanzoor/hello-world-java-service/commit/56f4c8af3112b64bad661fc45af09b4da2f31582))
* update the file path ([99f36cc](https://github.com/kamimanzoor/hello-world-java-service/commit/99f36cc6f67014978a2e64331b59b4054f6e6be5))
