import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.impl.compact.CompactDFA;
import net.automatalib.util.automata.ca.FIFOAs;
import net.automatalib.util.automata.fsa.DFAs;
import net.automatalib.words.PhiChar;
import net.automatalib.words.Word;
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
    public void testReverseFL(){
        CompactFIFOA fifoa = TestAutomatons.mono_channel();
        CompactDFA l = TestAutomatons.dfa_mono(fifoa);
        int qc = 0;

        //Words to test
        PhiChar[] input1 = new PhiChar[] {
                new PhiChar(0,false, false),
                new PhiChar(1, false, true),
        };
        PhiChar[] expected1 = new PhiChar[] {
                new PhiChar(0, false, true)
        };

        PhiChar[] input2 = new PhiChar[] {
                new PhiChar(1,false, false),
                new PhiChar(2, false, true),
        };
        PhiChar[] expected2 = new PhiChar[] {
                new PhiChar(0, false, true)
        };

        PhiChar[] input3 = new PhiChar[] {
                new PhiChar(1,true, false),
                new PhiChar(0, false, true),
        };
        PhiChar[] expected3 = new PhiChar[] {
                new PhiChar(1,false, false),
                new PhiChar(2, false, true)
        };

        PhiChar[] input4 = new PhiChar[] {
                new PhiChar(0,false, false),
                new PhiChar(3,true, false),
                new PhiChar(0, false, true),
        };
        PhiChar[] expected4 = new PhiChar[] {
                new PhiChar(0,false, false),
                new PhiChar(3,false, false),
                new PhiChar(2, false, true),
        };

        PhiChar[] input5 = new PhiChar[] {
                new PhiChar(0,true, false),
                new PhiChar(0, false, true),
        };
        PhiChar[] expected5 = new PhiChar[] {
                new PhiChar(0,false, false),
                new PhiChar(1, false, true),
        };

        Assert.assertEquals(Word.fromSymbols(expected1), FIFOAs.reverseFL(fifoa, l, Word.fromSymbols(input1)));
        Assert.assertEquals(Word.fromSymbols(expected2), FIFOAs.reverseFL(fifoa, l, Word.fromSymbols(input2)));
        Assert.assertEquals(Word.fromSymbols(expected3), FIFOAs.reverseFL(fifoa, l, Word.fromSymbols(input3)));
        Assert.assertEquals(Word.fromSymbols(expected4), FIFOAs.reverseFL(fifoa, l, Word.fromSymbols(input4)));
        Assert.assertEquals(Word.fromSymbols(expected5), FIFOAs.reverseFL(fifoa, l, Word.fromSymbols(input5)));

    }

    @Test
    public void testReversehcjd() {

    }


}
