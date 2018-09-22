package simulation;

/**
 * @author Haotian Wang
 * A specific Rule class for Segregation, adapting from http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/.
 */
public class SegregationRule extends Rule {
    // If number of neighbors < UNDERPOPULATION_THRESHOLD, underpopulation happens.
    public static final int UNDERPOPULATION_THRESHOLD = 2;
    // If number of neighbors > OVERPOPULATION_THRESHOLD, overpopulation happens.
    public static final int OVERPOPUlATION_THRESHOLD = 3;

    @Override
    public void determineNextStates() {

    }
}
