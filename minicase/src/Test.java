import de.learnlib.algorithms.lstar.dfa.ClassicLStarDFA;
import de.learnlib.algorithms.lstar.dfa.ClassicLStarDFABuilder;
import de.learnlib.api.oracle.MembershipOracle.DFAMembershipOracle;
import de.learnlib.filter.statistic.oracle.DFACounterOracle;
import de.learnlib.oracle.equivalence.ALFEQOracle;
import de.learnlib.oracle.membership.FIFOTraceSimulatorOracle;
import de.learnlib.util.Experiment;
import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.DFA;
import net.automatalib.util.automata.builders.AutomatonBuilders;
import net.automatalib.visualization.Visualization;
import net.automatalib.words.Alphabet;
import net.automatalib.words.PhiChar;
import net.automatalib.words.impl.Alphabets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static final int EXPLORATION_DEPTH = 4;

    private Test() {
    }//prevent instanciation

    public static void main(String[] args) throws IOException {
        // Loads FIFOA and alphabet
        CompactFIFOA<Character, Character> target = constructSUL();
        Alphabet<Character> inputs = target.getInputAlphabet();
        Alphabet<Character> channels = target.getChannelNames();
        Alphabet<PhiChar> phi = target.getAnnotationAlphabet();

        // Shows FIFOA before learning
        System.out.println(inputs);
        System.out.println(channels);
        System.out.println(target.getInitialState());
        Visualization.visualize(target, inputs, channels);

        // Creating the membership oracle
        DFAMembershipOracle<PhiChar> sul = new FIFOTraceSimulatorOracle(target); //Allows answer membership has in the annex
        DFACounterOracle<PhiChar> mqOracle = new DFACounterOracle<>(sul, "membership queries");

        // Creating the equivalency oracle

        ClassicLStarDFA<PhiChar> lstar =
                new ClassicLStarDFABuilder<PhiChar>().withAlphabet(phi)
                .withOracle(mqOracle)
                .create();

        List<Integer> badStates = new ArrayList<>();
        badStates.add(4);

        ALFEQOracle eqOracle = new ALFEQOracle(target, null, badStates);
        Experiment.DFAExperiment experiment = new Experiment.DFAExperiment(lstar, eqOracle, phi);

        // run experiment
        experiment.run();

        DFA<?, PhiChar> result = (DFA<?, PhiChar>) experiment.getFinalHypothesis();

        Visualization.visualize(result, phi);

    }

    /**
     * creates example from Andre's master thesis
     *
     * @return example dfa
     */
    private static CompactFIFOA<Character,Character> constructSUL() {
        // input alphabet contains characters 'a'..'b'
        Alphabet<Character> channelNames =Alphabets.characters('a', 'b');
        Alphabet<Character> sigma = Alphabets.characters('0', '1');

        // @formatter:off
        // create automaton
        // "(from((loop|to) (on(write|read)))+)* withInitial create"
        return AutomatonBuilders.newFIFOA(channelNames, sigma)
                .from("q0")
                .on('a').write('0').to("q1")
                .on('a').write('1').to("q2")
                .from("q1")
                .on('b').write('1').loop()
                .on('a').read('0').to("q3")
                .from("q2")
                .on('b').write('0').loop()
                .on('a').read('1').to("q3")
                .from("q3")
                .on('b').read('0').to("q0")
                .withInitial("q0")
                .create();
    }

}
