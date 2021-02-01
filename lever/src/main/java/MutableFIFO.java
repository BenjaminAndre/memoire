import net.automatalib.automata.MutableDeterministic;
import net.automatalib.automata.concepts.MutableStateOutput;
import net.automatalib.automata.transducers.MooreMachine;

public interface MutableFIFO<S, I, T,SP, TP>
        extends FIFO<S, I, T, SP, TP>, MutableDeterministic<S, I, T, SP, TP>{

}