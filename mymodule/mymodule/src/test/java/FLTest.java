import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.impl.compact.CompactDFA;
import net.automatalib.util.automata.ca.FIFOAs;
import net.automatalib.util.automata.fsa.DFAs;
import net.automatalib.words.PhiChar;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class FLTest {


    @Test
    public void testSigmaStarThetaSSigmaStarTP() {
        //Simulating a call for a?0 barred, ending in q3 ; should cut the fifoa in half

        CompactFIFOA andres = TestAutomatons.andre_fifoa();
        CompactDFA<PhiChar> sststp = FIFOAs.sigmaStarThetaSSigmaStarTP(andres, 'a','0' , 3,andres.getAnnotationAlphabet());

        List<PhiChar> path_1 = Arrays.asList(
            new PhiChar(0, true, false),
            new PhiChar(3, false, true)
        );
        Assert.assertTrue(sststp.computeOutput(path_1));

        List<PhiChar> path_2 = Arrays.asList(
            new PhiChar(1, false, false),
            new PhiChar(4, false, false),
            new PhiChar(2, false, true)
        );
        Assert.assertFalse(sststp.computeOutput(path_2));
    }

    @Test
    public void testSigmaStarTP() {
        //Simulating a call for a?0 barred, ending in q3 ; should cut the fifoa in half

        CompactFIFOA andres = TestAutomatons.andre_fifoa();
        CompactDFA<PhiChar> sststp = FIFOAs.sigmaStarTP(3,andres.getAnnotationAlphabet());

        List<PhiChar> path_1 = Arrays.asList(
                new PhiChar(0, true, false),
                new PhiChar(3, false, true)
        );
        Assert.assertTrue(sststp.computeOutput(path_1));

        List<PhiChar> path_2 = Arrays.asList(
                new PhiChar(0, false, false),
                new PhiChar(3, false, false),
                new PhiChar(1, false, true)
        );
        Assert.assertFalse(sststp.computeOutput(path_2));
    }

    @Test
    public void testAPlusAPrime() {
        CompactFIFOA mono = TestAutomatons.mono_channel();
        CompactDFA monoDFA = TestAutomatons.dfa_mono(mono);

        // As if we were considering theta_4  : a?1
        CompactDFA apap = FIFOAs.aPlusAPrime(mono, 'a', '1', 2, 0, monoDFA);
        CompactDFA expected = TestAutomatons.simplified_dfa_mono_aaprime(mono);

        CompactDFA xored = DFAs.xor(apap, expected, apap.getInputAlphabet());
        boolean empty = DFAs.acceptsEmptyLanguage(xored);
        Assert.assertTrue(empty);
    }

    @Test
    public void testFuseDFAs() {
        CompactDFA<Character> x = TestAutomatons.dfa_x();
        CompactDFA<Character> y = TestAutomatons.dfa_y();
        CompactDFA<Character> z = TestAutomatons.dfa_z();
        CompactDFA<Character> xyz = TestAutomatons.dfa_xyz();
        List<CompactDFA<Character>> ins = Arrays.asList(x,y,z);
        CompactDFA<Character> fused = FIFOAs.fuseDFAs(ins);
        CompactDFA xored = DFAs.xor(xyz, fused, xyz.getInputAlphabet());
        boolean empty = DFAs.acceptsEmptyLanguage(xored);
        Assert.assertTrue(empty);
    }

    @Test
    public void testReverseFL(){}

    @Test
    public void testReversehcjd() {}


}
