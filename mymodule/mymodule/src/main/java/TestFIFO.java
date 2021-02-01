
import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.impl.compact.CompactDFA;
import net.automatalib.util.automata.builders.AutomatonBuilders;
import net.automatalib.visualization.Visualization;
import net.automatalib.words.Alphabet;
import net.automatalib.words.impl.Alphabets;

import java.io.IOException;

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
        Visualization.visualize(target, inputs);


        /** Le meilleur des mondes
         *
         *  //get transition names (numbers)
         *  DFAMembershipOracle<Characte> sul = new FIFOASimulatorOracle<>(target); //Yes means it can represent some execution of the FIFOA
         *  DFACountarOracle<Character> mqOracle = new DFACounterOracle<>(sul, "membership queries"); // No modification
         *  ClassicLStarDFA<Character> lstar =
         *      new ClassicLStarDFABuilder<Character>().withAlphabet(transitionNames) //learning to name the transitions
         *      .withOracle(mqOracle)
         *      .create(); //99% same call
         *  DFAWMethodEQOracle<Character> wMethod = new DFAWMethoEQOracle<>(mpOracle, EXPLORATION_DEPTH); // No modification
         *  DFAEXperiment<Character> experiment = new DFAExperiment<>(lstar, wMethod, transitionNames);
         *
         *  QUID DE l'EQUIVALENCE ?
         */

        /**
        // load DFA and alphabet
        CompactDFA<Character> target2 = constructDFASUL();
        Alphabet<Character> inputs2 = target2.getInputAlphabet();
        DFAMembershipOracle<Character> sul = new DFASimulatorOracle<>(target2);
        DFACounterOracle<Character> mqOracle = new DFACounterOracle<>(sul, "membership queries");
        ClassicLStarDFA<Character> lstar =
                new ClassicLStarDFABuilder<Character>().withAlphabet(inputs2) // input alphabet
                        .withOracle(mqOracle) // membership oracle
                        .create();
        DFAWMethodEQOracle<Character> wMethod = new DFAWMethodEQOracle<>(mqOracle, EXPLORATION_DEPTH);
        DFAExperiment<Character> experiment = new DFAExperiment<>(lstar, wMethod, inputs2);

        experiment.setProfile(true);
        experiment.setLogModels(true);

        // run experiment
        experiment.run();

        // get learned model
        DFA<?, Character> result = experiment.getFinalHypothesis();

        Visualization.visualize(result, inputs2);
         */
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
