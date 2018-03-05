package edu.ml.tensorflow.util;

import edu.ml.tensorflow.ApplicationProperties;
import edu.ml.tensorflow.model.BoxPosition;
import edu.ml.tensorflow.model.Recognition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Util class for image processing.
 */
public class ImageUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);
    private static ImageUtil imageUtil;
    private ApplicationProperties applicationProperties;

    private ImageUtil(final ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        IOUtil.createDirIfNotExists(new File(applicationProperties.getOutputDir()));
    }

    /**
     * It returns the singleton instance of this class.
     * @return ImageUtil instance
     */
    public static ImageUtil getInstance(final ApplicationProperties applicationProperties) {
        if (imageUtil == null) {
            imageUtil = new ImageUtil(applicationProperties);
        }

        return imageUtil;
    }

    /**
     * Label image with classes and predictions given by the ThensorFLow
     * @param image buffered image to label
     * @param recognitions list of recognized objects
     * @return location of the labeled image
     */
    public String labelImage(final byte[] image, final List<Recognition> recognitions, final String fileName) {
        BufferedImage bufferedImage = imageUtil.createImageFromBytes(image);
        float scaleX = (float) bufferedImage.getWidth() / (float) applicationProperties.getImageSize();
        float scaleY = (float) bufferedImage.getHeight() / (float) applicationProperties.getImageSize();
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.green);

        for (Recognition recognition: recognitions) {
            BoxPosition box = recognition.getScaledLocation(scaleX, scaleY);
            //draw text
            graphics.drawString(recognition.getTitle() + " " + recognition.getConfidence(), box.getLeft(), box.getTop() - 7);
            // draw bounding box
            graphics.drawRect(box.getLeftInt(),box.getTopInt(), box.getWidthInt(), box.getHeightInt());
        }

        graphics.dispose();
        return saveImage(bufferedImage, applicationProperties.getOutputDir() + "/" + fileName);
    }

    /**
     *
     * @param image to save
     * @param target to save image
     * @return location of the labeled image
     */
    public String saveImage(final BufferedImage image, final String target) {
        try {
            ImageIO.write(image,"jpg", new File(target));
            return target;
        } catch (IOException ex) {
            LOGGER.error("Unagle to save image {}!", target);
            throw new RuntimeException(ex);
        }
    }

    private BufferedImage createImageFromBytes(final byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException ex) {
            throw new ServiceException("Unable to create image from bytes!", ex);
        }
    }
}
