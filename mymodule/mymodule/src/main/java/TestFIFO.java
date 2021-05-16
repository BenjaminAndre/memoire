import de.learnlib.algorithms.lstar.dfa.ClassicLStarDFA;
import de.learnlib.algorithms.lstar.dfa.ClassicLStarDFABuilder;
import de.learnlib.api.exception.SULException;
import de.learnlib.api.exception.exception.SafeException;
import de.learnlib.api.exception.exception.UnsafeException;
import de.learnlib.api.oracle.MembershipOracle;
import de.learnlib.oracle.equivalence.ALFEQOracle;
import de.learnlib.oracle.membership.FIFOTraceSimulatorOracle;
import de.learnlib.util.Experiment;
import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.impl.compact.CompactDFA;
import net.automatalib.util.automata.builders.AutomatonBuilders;
import net.automatalib.words.Alphabet;
import net.automatalib.words.PhiChar;
import net.automatalib.words.impl.Alphabets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestFIFO {

    private static final int EXPLORATION_DEPTH = 4;

    private TestFIFO() {
    }//prevent instanciation

    public static void main(String[] args) throws IOException {
        //Load dfa and alphabet
        CompactFIFOA<Character, Character> target = constructSUL();
        Alphabet<Character> inputs = target.getInputAlphabet();
        Alphabet<Character> channels = target.getChannelNames();


        System.out.println(inputs);
        System.out.println(channels);
        System.out.println(target.getInitialState());
        //Visualization.visualize(target, inputs);

        MembershipOracle<PhiChar, Boolean> sul = new FIFOTraceSimulatorOracle(target);

        Alphabet<PhiChar> annotedAlphabet = target.getAnnotationAlphabet();

        ClassicLStarDFA<PhiChar> lstar =
              new ClassicLStarDFABuilder<PhiChar>().withAlphabet(annotedAlphabet)
                    .withOracle(sul)
                    .create();

        // 4 is not within the states so the automaton should be safe
        List<Integer> baddies = Arrays.asList(new Integer[]{50});

        ALFEQOracle eqo = new ALFEQOracle(target, sul, baddies);

        Experiment.LeverExperiment experiment = new Experiment.LeverExperiment(lstar, eqo, annotedAlphabet);
        try {
            experiment.run();
        } catch(SULException sule) {
            Throwable e = sule.getCause();
            if(e instanceof SafeException) {
                System.out.println("Automaton is safe");
            } else if (e instanceof  UnsafeException){
                System.out.println("Automaton is unsafe");
            }
        }
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

    /**
     * creates example from Angluin's seminal paper.
     *
     * @return example dfa
     */
    private static CompactDFA<Character> constructDFASUL() {
        // input alphabet contains characters 'a'..'b'
        Alphabet<Character> sigma = Alphabets.characters('a', 'b');

        // @formatter:off
        // create automaton
        return AutomatonBuilders.newDFA(sigma)
                .withInitial("q0")
                .from("q0")
                .on('a').to("q1")
                .on('b').to("q2")
                .from("q1")
                .on('a').to("q0")
                .on('b').to("q3")
                .from("q2")
                .on('a').to("q3")
                .on('b').to("q0")
                .from("q3")
                .on('a').to("q2")
                .on('b').to("q1")
                .withAccepting("q0")
                .create();
        // @formatter:on
    }


}
