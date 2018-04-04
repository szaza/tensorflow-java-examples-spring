# TensorFlow Java tutorial with Spring Framework and Gradle
## Object detection server application
Object detection server side application sample program written in Java. It uses the TensorFlow Java API with a trained YOLOv2 model. The server application is implemented with Spring Framework and it is built by Gradle.

#### How it works?

It provides a web user interface to upload images and detect objects. Please have a look at the following screenshots:

<img src="https://github.com/szaza/java-tensorflow-spring/blob/master/sample/home-page.jpg" alt="TensorFlow Java API home page" title="TensorFlow Java API home page" width="600"/><br/>
Step1: upload your image

<img src="https://github.com/szaza/java-tensorflow-spring/blob/master/sample/object-detection-page.jpg" alt="TensorFlow Java API object detection page" title="TensorFlow Java API object detection page" width="600"/><br/>Step2: display the recognized objects

#### Compile and run
Before compiling the source code you have to place the frozen graph and the label file into the `./graph/` directory. Download one of my graphs from my [google drive](https://drive.google.com/drive/folders/1GfS1Yle7Xari1tRUEi2EDYedFteAOaoN). There are two graphs: tiny-yolo-voc.pb and yolo-voc.pb. The tiny-yolo.pb has a lower size, however it is less accurate than the yolo-voc.pb.

Compile the code by typing `./gradlew clean build` in the terminal window.<br/>
Run it with the command `./gradlew bootRun`

Open the [http://localhot:8080](http://localhot:8080) and you should see the webpage.<br/>
If you want to understand better how the image recognition part works, have a look at my previous project here: [Tensorflow Java API example application](https://github.com/szaza/tensorflow-example-java)

#### Demo application

Deployed to **Google cloud**: http://35.231.120.117:8080/

Deployed to **Heroku** with a tiny-yolo model: https://still-crag-64816.herokuapp.com/
