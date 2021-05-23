import de.learnlib.api.oracle.MembershipOracle;
import de.learnlib.api.query.DefaultQuery;
import de.learnlib.oracle.equivalence.ALFEQOracle;
import de.learnlib.oracle.membership.FIFOTraceSimulatorOracle;
import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.impl.compact.CompactDFA;
import net.automatalib.serialization.dot.GraphDOT;
import net.automatalib.util.automata.fsa.DFAs;
import net.automatalib.words.PhiChar;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ALFEQOracleTest {

    //Tests too much. Isn't a helpfull test
    CompactFIFOA mono = TestAutomatons.mono_channel();
    CompactDFA alf = TestAutomatons.dfa_mono(mono);

    MembershipOracle<PhiChar, Boolean> sul = new FIFOTraceSimulatorOracle(mono);

    ALFEQOracle monoeq = new ALFEQOracle(mono, sul, new ArrayList<Integer>());

    @Test
    public void testFixPointCounterExemple() {
        //Tests too much. Isn't a helpfull test
        try {
            GraphDOT.write(mono, mono.getAlphabet(), System.out);
            GraphDOT.write(alf, alf.getAlphabet(), System.err);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultQuery answer = monoeq.findCounterExample(DFAs.complete(alf, alf.getAlphabet()), alf.getAlphabet());
        Assert.assertNull(answer);

    }

    @Test
    public void testGetUnsafePath() {}

    @Test
    public void testIsPathValid() {}
}
