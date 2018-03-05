package edu.ml.tensorflow;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
public class ApplicationProperties {
    private String graph;
    private String label;
    private String outputDir;
    private String uploadDir;
    private Integer imageSize;
    private Float imageMean;

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

    public Float getImageMean() {
        return imageMean;
    }

    public void setImageMean(Float imageMean) {
        this.imageMean = imageMean;
    }
}
