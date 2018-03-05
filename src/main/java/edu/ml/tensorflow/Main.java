package edu.ml.tensorflow;

import edu.ml.tensorflow.classifier.ObjectDetector;

public class Main {
    private final static String IMAGE = "/image/eagle.jpg";

    public static void main(String[] args) {
        ObjectDetector objectDetector = new ObjectDetector();
        objectDetector.detect(IMAGE);
    }
}
