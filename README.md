# TensorFlow Java tutorial with Spring Framework and Gradle
Object detection server side application sample program written in Java. It uses the TensorFlow Java API with a trained YOLOv2 model. The server application is implemented with Spring Framework and it is built by Gradle.

#### How it works?

It provides a web user interface to upload images and detect objects.

<img src="https://github.com/szaza/java-tensorflow-spring/blob/master/sample/home-page.jpg" alt="TensorFlow Java API home page" title="TensorFlow Java API home page" width="600"/><br/>
Step1: upload your image

<img src="https://github.com/szaza/java-tensorflow-spring/blob/master/sample/object-detection-page.jpg" alt="TensorFlow Java API object detection page" title="TensorFlow Java API object detection page" width="600"/><br/>Step2: display the recognized objects

#### Compile and run

Preconditions:
- Java JDK 1.8 or greater;
- TensorFlow 1.6 or grater;
- Git version control system;

Strongly recommended to install:
- nVidia CUDA Toolkit 8.0 or higher version;
- nVidia cuDNN GPU accelerated deep learning framework;

**Download the frozen graph and the label file**

Before compiling the source code you have to place the frozen graph and the label file into the `./graph/YOLO` directory. Download one of my graphs from my [google drive](https://drive.google.com/drive/folders/1GfS1Yle7Xari1tRUEi2EDYedFteAOaoN). There are two graphs: tiny-yolo-voc.pb and yolo-voc.pb. The tiny-yolo.pb has a lower size, however it is less accurate than the yolo-voc.pb. Modify the [application.yml](https://github.com/szaza/tensorflow-java-examples-spring/blob/master/src/main/resources/application.yml) configuration file if it is necessary. Here you can increase the file upload limit also.

**Compile with Gradle**

Compile the code by typing `./gradlew clean build` in the terminal window.<br/>
Run it with the command `./gradlew bootRun`

Open the [http://localhost:8080](http://localhost:8080) and you should see the webpage.<br/>

#### Demo application

Deployed to **Google cloud**: http://35.229.93.105:8080/ <br/>
Deployed to **Heroku** with a tiny-yolo model: https://still-crag-64816.herokuapp.com/

Have a look at my previous project for better understanding of the object detection part: [Tensorflow Java API example application](https://github.com/szaza/tensorflow-example-java) or visit my site: https://sites.google.com/view/tensorflow-example-java-api.
