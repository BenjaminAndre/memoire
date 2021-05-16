import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.impl.compact.CompactDFA;
import net.automatalib.util.automata.builders.AutomatonBuilders;
import net.automatalib.words.Alphabet;
import net.automatalib.words.PhiChar;
import net.automatalib.words.impl.Alphabets;

public class TestAutomatons {

    static CompactFIFOA<Character, Character> simplest_fifoa() {
        // input alphabet contains characters 'a'..'b'
        Alphabet<Character> channelNames = Alphabets.characters('a', 'a');
        Alphabet<Character> sigma = Alphabets.characters('0', '0');

        // @formatter:off
        // create automaton
        // "(from((loop|to) (on(write|read)))+)* withInitial create"
        return AutomatonBuilders.newFIFOA(channelNames, sigma)
                .from("q0")
                .on('a').write('0').to("q1")
                .from("q1")
                .on('a').read('0').to("q0")
                .withInitial("q0")
                .create();
    }

    /**
     * creates example from Andre's master thesis
     *
     * @return example dfa
     */
    static CompactFIFOA<Character,Character> andre_fifoa() {
        // input alphabet contains characters 'a'..'b'
        Alphabet<Character> channelNames = Alphabets.characters('a', 'b');
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

    static CompactDFA<PhiChar> andre_alf_fifoa(CompactFIFOA f) {
        // input alphabet contains characters 'a'..'b'
        Alphabet<PhiChar> sigma = f.getAnnotationAlphabet();

        // @formatter:off
        // create automaton
        return AutomatonBuilders.newDFA(sigma)
                .withInitial(0)
                .from(0)
                .on(sigma.getSymbol(0)).to(1)
                .on(sigma.getSymbol(1)).to(0)
                .on(sigma.getSymbol(2)).to(0)
                .on(sigma.getSymbol(3)).to(0)
                .on(sigma.getSymbol(4)).to(0)
                .on(sigma.getSymbol(5)).to(0)
                .on(sigma.getSymbol(6)).to(0)
                .on(sigma.getSymbol(7)).to(0)
                .on(sigma.getSymbol(8)).to(0)
                .on(sigma.getSymbol(9)).to(0)
                .on(sigma.getSymbol(10)).to(0)
                .on(sigma.getSymbol(11)).to(0)
                .from(1)
                .on(sigma.getSymbol(0)).to(1)
                .on(sigma.getSymbol(1)).to(1)
                .on(sigma.getSymbol(2)).to(1)
                .on(sigma.getSymbol(3)).to(1)
                .on(sigma.getSymbol(4)).to(1)
                .on(sigma.getSymbol(5)).to(1)
                .on(sigma.getSymbol(6)).to(1)
                .on(sigma.getSymbol(7)).to(1)
                .on(sigma.getSymbol(8)).to(1)
                .on(sigma.getSymbol(9)).to(1)
                .on(sigma.getSymbol(10)).to(1)
                .on(sigma.getSymbol(11)).to(1)
                .withAccepting(1)
                .create();

        // @formatter:on
    }

    static CompactDFA<PhiChar> andre_cdfa(CompactFIFOA f) {
        // input alphabet contains characters 'a'..'b'
        Alphabet<PhiChar> sigma = f.getAnnotationAlphabet();

        // @formatter:off
        // create automaton
        return AutomatonBuilders.newDFA(sigma)
                .withInitial(0)
                .from(0)
                .on(sigma.getSymbol(0)).to(2)
                .on(sigma.getSymbol(1)).to(2)
                .on(sigma.getSymbol(2)).to(2)
                .on(sigma.getSymbol(3)).to(2)
                .on(sigma.getSymbol(4)).to(2)
                .on(sigma.getSymbol(5)).to(2)
                .on(sigma.getSymbol(6)).to(2)
                .on(sigma.getSymbol(7)).to(2)
                .on(sigma.getSymbol(8)).to(1)
                .on(sigma.getSymbol(9)).to(2)
                .on(sigma.getSymbol(10)).to(2)
                .on(sigma.getSymbol(11)).to(2)
                .from(1)
                .on(sigma.getSymbol(0)).to(2)
                .on(sigma.getSymbol(1)).to(2)
                .on(sigma.getSymbol(2)).to(2)
                .on(sigma.getSymbol(3)).to(2)
                .on(sigma.getSymbol(4)).to(2)
                .on(sigma.getSymbol(5)).to(2)
                .on(sigma.getSymbol(6)).to(2)
                .on(sigma.getSymbol(7)).to(2)
                .on(sigma.getSymbol(8)).to(2)
                .on(sigma.getSymbol(9)).to(2)
                .on(sigma.getSymbol(10)).to(2)
                .on(sigma.getSymbol(11)).to(2)
                .from(2)
                .on(sigma.getSymbol(0)).to(2)
                .on(sigma.getSymbol(1)).to(2)
                .on(sigma.getSymbol(2)).to(2)
                .on(sigma.getSymbol(3)).to(2)
                .on(sigma.getSymbol(4)).to(2)
                .on(sigma.getSymbol(5)).to(2)
                .on(sigma.getSymbol(6)).to(2)
                .on(sigma.getSymbol(7)).to(2)
                .on(sigma.getSymbol(8)).to(2)
                .on(sigma.getSymbol(9)).to(2)
                .on(sigma.getSymbol(10)).to(2)
                .on(sigma.getSymbol(11)).to(2)
                .withAccepting(1)
                .create();

        // @formatter:on
    }


    /**
     * creates example from Angluin's seminal paper.
     *
     * @return example dfa
     */
    static CompactDFA<Character> angluin_dfa() {
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

    static CompactDFA<Character> dfa_x() {
        Alphabet<Character> sigma = Alphabets.characters('0', '1');

        return AutomatonBuilders.newDFA(sigma)
                .withInitial("q0")
                .from("q0")
                .on('0').to("q1")
                .withAccepting("q1")
                .create();
    }

    static CompactDFA<Character> dfa_y() {
        Alphabet<Character> sigma = Alphabets.characters('0', '1');

        return AutomatonBuilders.newDFA(sigma)
                .withInitial("q0")
                .from("q0")
                .on('1').to("q1")
                .withAccepting("q1")
                .create();
    }

    static CompactDFA<Character> dfa_z() {
        Alphabet<Character> sigma = Alphabets.characters('0', '1');

        return AutomatonBuilders.newDFA(sigma)
                .withInitial("q0")
                .from("q0")
                .on('0').to("q1")
                .on('1').to("q1")
                .from("q1")
                .on('1').to("q2")
                .withAccepting("q2")
                .create();
    }

    static CompactDFA<Character> dfa_xyz() {
        Alphabet<Character> sigma = Alphabets.characters('0', '1');

        return AutomatonBuilders.newDFA(sigma)
                .withInitial("q0")
                .from("q0")
                .on('0').to("q1")
                .on('1').to("q1")
                .from("q1")
                .on('1').to("q2")
                .withAccepting("q1")
                .withAccepting("q2")
                .create();
    }

    static CompactFIFOA<Character,Character> mono_channel() {
        Alphabet<Character> channelNames = Alphabets.characters('a', 'a');
        Alphabet<Character> sigma = Alphabets.characters('0', '1');

        return AutomatonBuilders.newFIFOA(channelNames, sigma)
                .from("q0")
                .on('a').write('0').to("q1")
                .on('a').write('1').to("q2")

                .from("q1")
                .on('a').read('0').to("q0")
                .on('a').write('1').to("q2")

                .from("q2")
                .on('a').read('1').to("q0")

                .withInitial("q0")
                .create();
    }

    static CompactDFA<PhiChar> dfa_mono(CompactFIFOA f) {
        Alphabet<PhiChar> sigma = f.getAnnotationAlphabet();

        // @formatter:off
        // create automaton
        return AutomatonBuilders.newDFA(sigma)
                .withInitial(0)

                .from(0)
                .on(sigma.getSymbol(1)).loop()
                .on(sigma.getSymbol(3)).loop()
                .on(sigma.getSymbol(0)).to(1)
                .on(sigma.getSymbol(2)).to(2)
                .on(sigma.getSymbol(6)).to(3)

                .from(1)
                .on(sigma.getSymbol(4)).to(2)
                .on(sigma.getSymbol(5)).to(0)
                .on(sigma.getSymbol(7)).to(3)

                .from(2)
                .on(sigma.getSymbol(8)).to(3)

                .withAccepting(3)
                .create();
    }

    static CompactDFA<PhiChar> simplified_dfa_mono_aaprime(CompactFIFOA f) {
        Alphabet<PhiChar> sigma = f.getAnnotationAlphabet();

        // @formatter:off
        // create automaton
        return AutomatonBuilders.newDFA(sigma)
                .withInitial(0)

                .from(0)
                .on(sigma.getSymbol(1)).loop()
                .on(sigma.getSymbol(0)).to(2)
                .on(sigma.getSymbol(3)).to(1)//0'

                .from(2)//old 1
                .on(sigma.getSymbol(5)).to(1)

                .from(1)
                .on(sigma.getSymbol(1)).loop()
                .on(sigma.getSymbol(3)).loop()
                .on(sigma.getSymbol(0)).to(3)
                .on(sigma.getSymbol(2)).to(5)
                .on(sigma.getSymbol(6)).to(7)

                .from(3)
                .on(sigma.getSymbol(4)).to(5)
                .on(sigma.getSymbol(5)).to(1)
                .on(sigma.getSymbol(7)).to(7)

                .from(5)
                .on(sigma.getSymbol(6)).to(7)

                .withAccepting(7)
                .create();
    }

}
