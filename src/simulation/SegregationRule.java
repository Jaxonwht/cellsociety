package simulation;

import java.util.List;

/**
 * @author Haotian Wang
 * A specific Rule class for Segregation, adapting from http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/.
 */
public class SegregationRule extends Rule {
    public final double SATISFACTION_THRESHOLD;

    public SegregationRule(Grid grid, List<Double> extraParameters) {
        super(grid, extraParameters);
        SATISFACTION_THRESHOLD = extraParameters.get(0);
    }

    @Override
    public void determineNextStates() {

    }
}
