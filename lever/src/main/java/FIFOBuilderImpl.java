import com.github.misberner.duzzt.annotations.DSLAction;
import com.github.misberner.duzzt.annotations.GenerateEmbeddedDSL;
import net.automatalib.automata.fsa.MutableFSA;
import net.automatalib.util.automata.builders.AutomatonBuilderImpl;

@GenerateEmbeddedDSL(name = "FSABuilder",
        enableAllMethods = false,
        syntax = "((from (on <<to* loop? to*>>)+)+)* create")
class FIFOBuilderImpl<S, I, A extends MutableFSA<S, ? super I>> extends AutomatonBuilderImpl<S, I, S, Boolean, Void, A> {

    FSABuilderImpl(A automaton) {
        super(automaton);
    }

    @DSLAction
    public void withAccepting(Object stateId) {
        S state = getState(stateId);
        automaton.setAccepting(state, true);
    }
}