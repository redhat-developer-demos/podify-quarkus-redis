# Podified Quarkus with Redis Podman (Desktop) Demo

# Pre-requisites

## Podman 4.5

Make sure to use Podman 4.5 or later.

## Podman Desktop 1.0

Make sure to install Podman Desktop 1.0 or later.

# Demo Script (without build of images)

In order to accelerate demo script, we'll use pre-built images from quay.io and pre-load them.

## Preparation of the demo

### OpenShift DevSandbox Login

1. Go to Devsandbox: https://developers.redhat.com/developer-sandbox
2. Login with your Red Hat account
3. Get the login command for the OpenShift cluster


![login-devsandbox](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/devsandbox-login.png)

4. Run the login command in a terminal

### Make sure you don't have anything from previous demo in your openshift namespace


### Start with a fresh Podman Machine (if you're on Windows or Mac, otherwise skip this section)

**Make sure to init with the proper image**
```bash
podman machine init --image-path ./fedora-coreos-36.20220806.3.0-qemu.x86_64.qcow2
```

**Podman Machine start**
```bash
podman machine start
```

### Prepare your browser

1. Open a new browser http://podman-desktop.io
2. Open another tab with Developer Sandbox and the Topology view

### Resize and zoom in Podman Desktop and in your Browsr

- Make sure you have Podman Desktop bigger and zoomed in (usually 2 levels are enough).
- Make sure to zoom in your browser as well.

## Script

1. Open Podman Desktop
2. Show the containers running in the Podman Machine
3. Explain a little bit about the demo application we are using for the demo

For this demo, we are going to use a simple microservice which is leveraging Redis. It's a basic Quarkus hello world, which is displaying Hello World and the number of time we opened up the app.
For that, we have a Quarkus app running in its container, and a Redis container running in the background.

4. Get into the details of one of the container
    - Show the container logs
    - Show the ability to get a shell into the container
    - Show the inspect, with all the metadata of the container

5. Now let's open the application, by clicking on the small "open" icon from podman desktop

![open-app](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/open-app.png)

6. Show the application running in the browser and refresh it multiple times

7. Get back into Podman Desktop. And speak about the ability to run pods inside of Podman.
    - Open the Pod tab from Podman Desktop
    - It  is empty, but now we want to turn our 2 containers running into a pod.

8. Create a pod from the container view in the Podman Desktop UI
    - Select the 2 containers in the list
      ![create-pod-button](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/create-pod-button.png)
    - Click on the "Create Pod" button

9. Podman Desktop is showing a new page where the user can customize the creation of the Pod. Let's just get the default settings.

![create-pod-form](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/create-pod-form.png)

Wait until the pod is getting created. You'll be automatically redirected to the pod view.

10. Once on the pod, you can speak over the ability to run that pod, get details about it.
    - Show the pod details
    - Get back to the container view, and you can show you can still access to a terminal inside of the container running into the pod

11. Now let's open the application again, by clicking on the small "open" icon from podman desktop

12. Now, what we want to do, is to transition that pod to OpenShift!
    - From the pod view, click on the "Deploy to kubernetes" button

![deploy-pod-to-kubernetes](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/deploy-pod-to-kubernetes.png)

13. Podman Desktop is showing a new page where the user can customize the deployment of the Pod to OpenShift. Let's just get the default settings.
    - We can speak over the generate kube capabilities from Podman
    - We have some adjustment to make sure it's going to run OpenShift. We need to create services and routes for the pod to be accessible from outside of the cluster.
    - It detects the current kubernetes context

14. Run the deployment, by clicking on the button "Deploy"

![deploy](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/deploy.png)

15. Wait until the deployment is done. You'll be have links to open the application running on OpenShift.

![open-openshift](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/open-openshift.png)

16. On Openshift, get back to your previous tab with the Topology view. You can see the pod running on OpenShift.
    - Open the Pod details

![pod-details](https://github.com/redhat-developer-demos/podify-quarkus-redis/blob/main/assets/pod-detail.png)

    - You can click on "my pod" in the pod section, to show details of the pod
    - Show running containers, access to logs, terminal

17. Get back into podman desktop.


# Demo introduction with build & run of images

### Create podman network

```bash
podman network create quarkus
```


### Run the redis from quay.io

```bash
podman run -d -p 6379:6379 --network=quarkus --name redis quay.io/podman-desktop-demo/podify-demo-backend:v1
```

### Run the application

#### Build the container

(it is also published and public on quay.io)

```bash
podman build -t quay.io/rhdevelopers/quarkus-redis -f ./src/main/docker/Dockerfile.jvm .
```

#### Run the Quarkus app

```bash
podman run -d -p 8080:8080 --network=quarkus -e redis=redis --name quarkus-app quay.io/rhdevelopers/quarkus-redis
```

# Instructions to create pod manually from CLI

## Run this application in a Pod

### Prerequisites

**Make sure you are running a podman machine with at least CPU 2 and Memory 4GB.**

### Start Redis in a pod

Here we declare all the ports that we need for the application. It has to be done each time creating a new pod.

```bash
podman run -dt --pod new:quarkus-pod -p 6379:6379 -p 8080:8080 --name redis quay.io/podman-desktop-demo/podify-demo-backend:v1
```

### Start the Quarkus container in a pod

```bash
podman run -dt --pod quarkus-pod --name quarkus-app quay.io/rhdevelopers/quarkus-redis
```
