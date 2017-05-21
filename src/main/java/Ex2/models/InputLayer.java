package Ex2.models;

import Ex2.utils.MatrixUtils;
import org.la4j.Matrix;

/**
 * Created by szale_000 on 2017-03-07.
 */
public class InputLayer extends Layer {

    public InputLayer(int numNeurons, double learningRate, double momentum) {
        this(numNeurons, false, learningRate, momentum);
    }

    public InputLayer(int numNeurons, boolean hasBias, double learningRate, double momentum) {
        super(1, numNeurons, hasBias, null, learningRate, momentum);
    }

    public void setInput(Matrix data) {
        activationValues = hasBias() ? MatrixUtils.addBiasColumn(data) : data;
    }
}
