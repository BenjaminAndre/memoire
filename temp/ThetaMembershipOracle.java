package main.java;

import de.learnlib.api.oracle.MembershipOracle;
import de.learnlib.api.oracle.SingleQueryOracle;
import de.learnlib.oracle.membership.SimulatorOracle;
import net.automatalib.automata.fsa.DFA;
import net.automatalib.words.Word;

public class ThetaMembershipOracle<I,C>  implements SingleQueryOracleFIFO<I> {

    private final CompactFIFO<I, C> fifo;

    public ThetaMembershipOracle(CompactFIFO<I, C> fifo) {
        this.fifo = fifo;
    }

    // I guess it means returning yes or no.
    @Override
    public Boolean answerQuery(Word<I> prefix, Word<I> suffix) {
        System.out.println("answerQuery");
        return null;
    }
}

interface SingleQueryOracleFIFO<I> extends SingleQueryOracle<I, Boolean>, FIFOMembershipOracle<I> {}
interface FIFOMembershipOracle<I> extends MembershipOracle<I, Boolean> {}




/*

    From SimulatorOracle

    @Override
    public D answerQuery(Word<I> prefix, Word<I> suffix) {
        return automaton.computeSuffixOutput(prefix, suffix);
    }

    @Override
    public void processQueries(Collection<? extends Query<I, D>> queries) {
        MQUtil.answerQueries(this, queries);
    }


 */