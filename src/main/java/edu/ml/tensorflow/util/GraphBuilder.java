package edu.ml.tensorflow.util;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

/**
 * Defines the necessary operations for image processing and builds the graph.
 */
public class GraphBuilder {
    private Graph graph;

    public GraphBuilder(Graph graph) {
        this.graph = graph;
    }

    public Output<Float> div(Output<Float> x, Output<Float> y) {
        return binaryOp("Div", x, y);
    }

    public <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {
        return binaryOp3("ResizeBilinear", images, size);
    }

    public <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
        return binaryOp3("ExpandDims", input, dim);
    }

    public <T, U> Output<U> cast(Output<T> value, Class<U> type) {
        DataType dtype = DataType.fromClass(type);
        return graph.opBuilder("Cast", "Cast")
                .addInput(value)
                .setAttr("DstT", dtype)
                .build()
                .<U>output(0);
    }

    public Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
        return graph.opBuilder("DecodeJpeg", "DecodeJpeg")
                .addInput(contents)
                .setAttr("channels", channels)
                .build()
                .<UInt8>output(0);
    }

    public <T> Output<T> constant(String name, Object value, Class<T> type) {
        try (Tensor<T> t = Tensor.<T>create(value, type)) {
            return graph.opBuilder("Const", name)
                    .setAttr("dtype", DataType.fromClass(type))
                    .setAttr("value", t)
                    .build()
                    .<T>output(0);
        }
    }

    public Output<String> constant(String name, byte[] value) {
        return this.constant(name, value, String.class);
    }

    public Output<Integer> constant(String name, int value) {
        return this.constant(name, value, Integer.class);
    }

    public Output<Integer> constant(String name, int[] value) {
        return this.constant(name, value, Integer.class);
    }

    public Output<Float> constant(String name, float value) {
        return this.constant(name, value, Float.class);
    }

    private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
        return graph.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
    }

    private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1, Output<V> in2) {
        return graph.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
    }
}
