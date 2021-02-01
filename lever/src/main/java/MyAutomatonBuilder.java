import net.automatalib.automata.MutableAutomaton;
import net.automatalib.automata.base.compact.AbstractCompactDeterministic;
import net.automatalib.automata.transducers.MutableMooreMachine;
import net.automatalib.automata.transducers.impl.compact.CompactMoore;
import net.automatalib.util.automata.builders.AutomatonBuilder;
import net.automatalib.util.automata.builders.MooreBuilder;
import net.automatalib.words.Alphabet;

import java.util.List;

public class MyAutomatonBuilder {


    public static <S, I, C, T,SP,TP>
        AutomatonBuilder<S, I, T, SP, TP, MutableAutomaton<S, ? super I, T, ? super SP, ? super TP>>
        newFIFOAutomaton(Alphabet<I> alphabet, List<C> channels)
    {
        return forAutomaton(new CompactFIFO<I,C>(alphabet, channels));
    }

    public static <S, I, T, SP, TP, A extends MutableAutomaton<S, ? super I, T, ? super SP, ? super TP>> AutomatonBuilder<S, I, T, SP, TP, A> forAutomaton(
            A fifo) {
        return new AutomatonBuilder<>(fifo);
    }

}

/*
    From Moore

    public static <I, O> MooreBuilder<Integer, I, Integer, O, CompactMoore<I, O>> newMoore(Alphabet<I> alphabet) {
        return forMoore(new CompactMoore<>(alphabet));
    }

    public static <S, I, T, O, A extends MutableMooreMachine<S, ? super I, T, ? super O>> MooreBuilder<S, I, T, O, A> forMoore(
            A moore) {
        return new MooreBuilder<>(moore);
    }


 */