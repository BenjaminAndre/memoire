import net.automatalib.automata.ca.impl.compact.CompactFIFOA;
import net.automatalib.words.Alphabet;
import net.automatalib.words.PhiChar;
import org.junit.Assert;
import org.testng.annotations.Test;

public class AlphabetTest {


    @Test
    public void testGetAnnotationAlphabet(){
        CompactFIFOA cfa = TestAutomatons.simplest_fifoa();
        Alphabet<PhiChar> actual = cfa.getAnnotationAlphabet();
        PhiChar[] expected = new PhiChar[] {
            // Not putting in the tau or receive in the alf language
            new PhiChar(0,false, false),
            new PhiChar(0, true, false),
            new PhiChar(0, false, true),
            new PhiChar(1, false, true),
        };
        Assert.assertArrayEquals(actual.toArray(), expected);


    }
}
