import de.learnlib.oracle.equivalence.SimulatorEQOracle;
import de.learnlib.oracle.membership.SimulatorOracle;
import net.automatalib.automata.transducers.impl.compact.CompactMealy;
import net.automatalib.words.Word;
import de.learnlib.examples;

public class Test {


    public static void main(String[] args) {
        CompactMealy<Character, String> cm = null;
        CompactMealy<Input, String> fm = ExampleCoffeeMachine.constructMachine();
        Alphabet<Input> alphabet = fm.getInputAlphabet();

        SimulatorOracle<Input, Word<String>> simoracle = new SimulatorOracle<>(fm);
        SimulatorEQOracle<Input, Word<String>> eqoracle = new SimulatorEQOracle<>(fm);

        MembershipOracle<Input, Word<String>> cache = MealyCaches.createCache(alphabet, simoracle);

        MealyDHC<Input, String> learner = new MealyDHC<>(alphabet, cache);

        DefaultQuery<Input, Word<String>> counterexample = null;
        do {
            if (counterexample == null) {
                learner.startLearning();
            } else {
                boolean refined = learner.refineHypothesis(counterexample);
                if (!refined) {
                    System.err.println("No refinement effected by counterexample!");
                }
            }

            counterexample = eqoracle.findCounterExample(learner.getHypothesisModel(), alphabet);

        } while (counterexample != null);

// from here on learner.getHypothesisModel() will provide an accurate model
    }

}
