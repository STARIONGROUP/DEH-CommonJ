# DEH-CommonJ
The DEH-CommonJ library provides common UI components, view models and services to exchange between CDP4-COMET ECSS-E-TM-10-25 Annex A implementation and any java client. It is based on the following libraries.

| Artifact           | Link                                                                                                                                                                                  |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| CDP4JsonSerializer | [![Maven Central](https://img.shields.io/maven-central/v/eu.stariongroup/cdp4jsonserializer?style=plastic)](https://central.sonatype.com/artifact/eu.stariongroup/cdp4jsonserializer) |
| CDP4Dal            | [![Maven Central](https://img.shields.io/maven-central/v/eu.stariongroup/cdp4dal?style=plastic)](https://central.sonatype.com/artifact/eu.stariongroup/cdp4dal)                       |
| CDP4ServicesDal    | [![Maven Central](https://img.shields.io/maven-central/v/eu.stariongroup/cdp4servicesdal?style=plastic)](https://central.sonatype.com/artifact/eu.stariongroup/cdp4servicesdal)       |

## Technical implementation

The DEH-CommonJ library is highly based on [Reactive](https://reactivex.io/) and uses as IoC [picocontainer](https://central.sonatype.com/artifact/org.bidib.org.picocontainer/picocontainer)
The container is extendable and available through the [App.AppContainer.Container](https://github.com/STARIONGROUP/DEH-CommonJ/blob/0d92f7a04020c1f4105c2eef707bccbd543e8cfc/src/main/java/App/AppContainer.java#L55) field.

View navigation can be done using the [INavigationService](https://github.com/STARIONGROUP/DEH-CommonJ/blob/0d92f7a04020c1f4105c2eef707bccbd543e8cfc/src/main/java/Services/NavigationService/NavigationService.java#L39)
Where the view model can be resolved and injected from the container based on the naming convention ```{ViewName}/I{ViewNameView}Model``` automatically when providing an implementation of an [IView](https://github.com/STARIONGROUP/DEH-CommonJ/blob/0d92f7a04020c1f4105c2eef707bccbd543e8cfc/src/main/java/Views/Interfaces/IView.java#L33)
or an implementation of an [IDialog](https://github.com/STARIONGROUP/DEH-CommonJ/blob/0d92f7a04020c1f4105c2eef707bccbd543e8cfc/src/main/java/Views/Interfaces/IDialog.java#L33).

## Download

[![Maven Central](https://img.shields.io/maven-central/v/eu.stariongroup/cdp4common?style=plastic)](https://central.sonatype.com/artifact/eu.stariongroup/dehcommonj)

## Build status

AppVeyor is used to build and test the Java DEH-CommonJ

| Branch | Build Status                                                                                                                                                                  |
|--------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Master | [![Java CI with Maven](https://github.com/STARIONGROUP/DEH-CommonJ/actions/workflows/ci.yml/badge.svg)](https://github.com/STARIONGROUP/DEH-CommonJ/actions/workflows/ci.yml) |

## SonarQube Status:
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=alert_status)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=security_rating)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=coverage)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=bugs)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=ncloc)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=sqale_index)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=STARIONGROUP_DEH-CommonJ&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=STARIONGROUP_DEH-CommonJ)
