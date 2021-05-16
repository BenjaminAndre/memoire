import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.automata.fsa.impl.compact.CompactDFA;
import net.automatalib.words.PhiChar;
import net.automatalib.words.Word;
import org.junit.Assert;
import org.testng.annotations.Test;

public class FIFOATest {

    //t_0.t_2.s_3 INCORRECT
    Word<PhiChar> trace_1 = Word.fromSymbols(
            new PhiChar(0, false, false),
            new PhiChar(2, false, false),
            new PhiChar(3,false, true)
    );

    //b_0.s_1.t_2 INCORRECT
    Word<PhiChar> trace_2 = Word.fromSymbols(
            new PhiChar(0, true, false),
            new PhiChar(1, false, true),
            new PhiChar(2,false, false)
    );

    //t_1.b_4.b_1.s_3 INCORRECT
    Word<PhiChar> trace_3 = Word.fromSymbols(
            new PhiChar(1, false, false),
            new PhiChar(4, true, false),
            new PhiChar(1,true, false),
            new PhiChar(3,false, true)
    );

    //b_0.s_0 INVALID
    Word<PhiChar> trace_4 = Word.fromSymbols(
            new PhiChar(0, true, false),
            new PhiChar(0, false, true)
    );

    //b_1.t_0.s_1 INVALID
    Word<PhiChar> trace_5 = Word.fromSymbols(
            new PhiChar(1, true, false),
            new PhiChar(0, false, false),
            new PhiChar(1,false, true)
    );

    //b_0.t_3.s_3 VALID
    Word<PhiChar> trace_6 = Word.fromSymbols(
            new PhiChar(0, true, false),
            new PhiChar(3, false, false),
            new PhiChar(3,false, true)
    );

    //b_1.b_4.t_0.s_1 VALID
    Word<PhiChar> trace_7 = Word.fromSymbols(
            new PhiChar(1, true, false),
            new PhiChar(4, true, false),
            new PhiChar(0,false, false),
            new PhiChar(1,false, true)
    );

    CompactFIFOA andres = TestAutomatons.andre_fifoa();
    CompactDFA xyz = TestAutomatons.dfa_xyz();

    @Test
    public void testIsValidAnnotedTrace(){
        Assert.assertFalse(andres.isValidAnnotedTrace(trace_1));
        Assert.assertFalse(andres.isValidAnnotedTrace(trace_2));
        Assert.assertTrue(andres.isValidAnnotedTrace(trace_3));//obviously invalid but fills the definition of correct
        Assert.assertTrue(andres.isValidAnnotedTrace(trace_4));
        Assert.assertTrue(andres.isValidAnnotedTrace(trace_5));
        Assert.assertTrue(andres.isValidAnnotedTrace(trace_6));
        Assert.assertTrue(andres.isValidAnnotedTrace(trace_7));
    }


    @Test
    public void testGetConsumingTransitions() {
        Integer[] consa0 = new Integer[]{2};
        Integer[] consa1 = new Integer[]{5};
        Integer[] consb0 = new Integer[]{6};

        Assert.assertArrayEquals(consa0, andres.getConsummingTransitions('a', '0'));
        Assert.assertArrayEquals(consa1, andres.getConsummingTransitions('a', '1'));
        Assert.assertArrayEquals(consb0, andres.getConsummingTransitions('b', '0'));
    }

    @Test
    public void testGetLeadingTransitions() {
        Integer[] actual = (Integer[])xyz.getLeadingTransitions(1, '1').toArray(new Integer[]{});
        Integer[] expected = new Integer[]{1};
        Assert.assertArrayEquals(actual, expected);
    }

    @Test
    public void testTransitionState() {
        Assert.assertEquals(andres.getTransitionOriginState(0), 0);
        Assert.assertEquals(andres.getTransitionOriginState(1), 0);
        Assert.assertEquals(andres.getTransitionOriginState(2), 1);
        Assert.assertEquals(andres.getTransitionOriginState(3), 1);
        Assert.assertEquals(andres.getTransitionOriginState(4), 2);
        Assert.assertEquals(andres.getTransitionOriginState(5), 2);
        Assert.assertEquals(andres.getTransitionOriginState(6), 3);
    }

    @Test
    public void testValidateTrace() {
        Assert.assertFalse(andres.validateTrace(trace_1));
        Assert.assertFalse(andres.validateTrace(trace_2));
        Assert.assertFalse(andres.validateTrace(trace_3));
        Assert.assertFalse(andres.validateTrace(trace_4));
        Assert.assertFalse(andres.validateTrace(trace_5));
        Assert.assertTrue(andres.validateTrace(trace_6));
        Assert.assertTrue(andres.validateTrace(trace_7));
    }






}
