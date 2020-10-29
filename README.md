[Compare example/rest-api with develop](http://git.system.local/projects/SF/repos/sdasi-blueprint/compare/diff?sourceBranch=refs%2Fheads%2Fexample%2Frest-api&targetRepoId=1315)

# Blueprint Service
The **sdasi-blueprint** project is a minimal, runnable project setup for your SDA Service.

For general questions join the Rocket.Chat channel [#sda-si](http://rocket.system.local/channel/sda-si). You should consider using the [DevVM](http://rocket.system.local/channel/developervm) as your preferred development environment.

## Start microservice
To **build** the microservice locally you have to run the [gradle](https://gradle.org/) `installDist` task first:
```shell
$ cd blueprint-service
$ ./gradlew installDist
```

After that you can use [docker-compose](https://docs.docker.com/compose/) to **start** the microservice.

```shell
$ docker-compose up --build
```

The [docker-compose.yml File](docker-compose.yml) defines the environment for the service to start.

Consider reading the [service specific README](blueprint-service/README.md) for example-related details.

By default remote debugging is enabled.
If you need to debug the service, join on port 5005.

## Admin features
Dropwizard's built-in admin console can be found at [http://localhost:8081](http://localhost:8081).

### Healthchecks
OpenShift needs two types of HealthChecks (see: [kubernetes-documentation](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-probes/#define-readiness-probes)).
This template provides both types of probes. OpenShift is configured to use those endpoints via [openshift/backend-template.yml](openshift/backend-template.yml).

#### Liveness Probe
This template uses Dropwizard's default ping-endpoint for its liveness probe.

### Dependency Injection
Dependency Injection in this template is realized using Weld SE, the CDI reference implementation. Please refer to the [offical documentation](http://docs.jboss.org/weld/reference/latest/en-US/html_single/) as well to the projects [beans.xml file](blueprint-service/src/main/resources/META-INF/beans.xml) for further configuration.

# Example usage

__Request with JWT__
```
curl -X GET http://localhost:8080/api/cars \
   -H 'Authorization: Bearer <your-token>'
```

# Getting Started

This section describes how to setup ***your*** SDA service based on the *sdasi-blueprint* project template.

## Preliminaries

SDA services are deployed to OpenShift by a deployment pipeline included in the *sdasi-blueprint*. In order to use that pipeline you have to [request](http://wiki.system.local/display/sdasi/Service+Onboarding) your individual namespace in OpenShift. Please [contact](http://rocket.system.local/channel/service-factory) the *Service Factory* if you need any help defining a proper namespace or designing your service. By doing this, you will get a *service name* and a *service group*.

Furthermore you need Git credentials which you probably already have because you can read this file. 😉 Finally it is helpful to see your deployed service in the OpenShift console. For that you need credentials as well.

## Clone the blueprint

Please clone the `develop` branch of the *sdasi-blueprint* project.

> **Do not use one of the example branches as a blueprint for your project!**
> 
> They do not necessarily contain the latest updates from `develop`.
> 
> Example branches are helpful to see how a specific technical challenge can be solved (e.g. connection to Kafka, MongoDB, IMS SOR).

Clone the *sdasi-blueprint* project into a folder that matches the name of the service you want to build.

```
git clone ssh://git@git.system.local:7999/sf/sdasi-blueprint.git [service name]
```

Then remove the `.git` directory in your project root and initialize a new git repository (you will push this project to your own repository later) .

```
cd [service name]
rm -rf .git
git init
```

## Customize the blueprint for your needs

As described in [README.md](README.md) the blueprint provides you with a working service that exposes a WelcomeApi. You can start this service immediately by running `docker-compose` on your local machine. To adapt this service to your specific use-case, some additional steps are required.

Open your preferred IDE and remember the *service name* and *service group* you got along with your OpenShift namespace. Additionally, you have to decide on a name for your *deployment unit* that describes the Java application that will be deployed. This might be something generic like `backend` or the same as your *service name*. But if your service is going to consist of more than one deployment units, choosing a descriptive name is important.

Using *service name*, *service group* and *deployment unit*  you can now replace and rename identifiers and files in the blueprint to match your project.

1. In your `Jenkinsfile`
    1. set the `SERVICE_NAME` and `SERVICE_GROUP` variables according to your *service name* and *service group* and
    2. change the `APP_BACKEND_NAME` and `APP_BACKEND_FOLDER` variables to your *deployment unit*.
2. Rename the `blueprint-service` directory in your project root to your *deployment unit* (the name has to match the `APP_BACKEND_FOLDER` in your `Jenkinsfile`).
3. Replace the occurrences of `sdasi-blueprint` in `README.md` with your *service name*.
4. Replace the occurrences of `sdasi-blueprint` in `docker-compose.yml` with your *deployment name*.
4. Perform a Java package refactoring with help of your IDE. Rename the package `de.signaliduna.blueprint` to `de.signaliduna.[your-service-name]`. Make sure the `mainClassName` variable in `build.gradle` is modified accordingly.
5. Finally, search the whole project for the term `blueprint` to make sure that we didn't miss something.

## Push the project to your own repository

Create a new Git repository if you haven't already and perform an initial commit. An initial setup tutorial is included on the main page of an empty repository.

```
git checkout -b develop
git add .
git commit -m "chore: project setup"
git remote add origin [your repository URL]
git push --set-upstream origin develop
```

To learn more about Git and Bitbucket, refer to the [documentation in our Wiki](http://wiki.system.local/pages/viewpage.action?pageId=87131770).

## Create a Jenkins job

1. Click 'Element anlegen' in [Jenkins](http://app416020.system.local:8080/).
2. Determine a name for the job.
3. Choose a "Multibranch Pipeline" Jenkins job.
4. Press 'OK'.
5. Insert your Git repo with credentials.

## Create a Web-Hook

To automatically trigger a build on every commit you need to configure a webhook for your repository.

1. Go to your Repository Settings in BitBucket.
2. Select 'Webhooks' and then 'Webhook erstellen'.
3. Enter 'Jenkins' as Name and this URL:

    `http://app416020.system.local:8080/git/notifyCommit?url=ssh://git@git.system.local:7999/[your repository].git`

    Make sure that you insert the correct SSH-URL to your repository.

4. You can test your settings using the 'Verbindung testen' button.
5. Press 'Speichern'.

