package main.java;

import net.automatalib.automata.MutableAutomaton;
import net.automatalib.automata.base.compact.AbstractCompact;
import net.automatalib.automata.base.compact.AbstractCompactDeterministic;
import net.automatalib.automata.fsa.MutableDFA;
import net.automatalib.words.Alphabet;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;


// Implements some interfaces to, I dunno, serialize and such ?
// I is the INPUT type (alphabet)
// C is the CHANNEL type (names of channels)
// TP are the TRANSITION PROPERTIES : triplets [Channel, Writing?, Symbol] TP is generic for the types of the three.
// First has to be of type C, second of type boolean, third of type I. Wait. TP is useless ?
// Because of Java rigidity, TP is a list : a channel, a boolean and a symbol
// Cheated a bit by using Integer as state property despite having no SP
public class CompactFIFO<I,C> extends AbstractCompactDeterministic<I, CompactFIFOTransition<I,C>, Integer, List> {

    // Based on transition id, returns successor
    private int[] successors;
    // Based on transition id, returns channel
    private Object[] channels; // type C
    // Based on transition id, returns isWritingTransition
    private Object[] writings; // type Boolean
    // Based on transition id, return the symbol manipulated
    private Object[] symbols; // type I



    // Channels. an object of type C is the identifier of channel (probably a character or integer)
    // Each channel can manage O type symbols (often, characters)
    private HashMap<C, Queue<I>> channelContents;


    // StateCapacity and resize factor are used for the dynamic resizing array holding the states
    public CompactFIFO(Alphabet<I> alphabet, List<C> channelNames, int stateCapacity, float resizeFactor) {
        super(alphabet, stateCapacity, resizeFactor);
        final int size = stateCapacity * numInputs();

        this.successors = new int[size];
        this.channels = new Object[size];
        this.writings = new Object[size];//could also be int but makes less sense to me. allows for null
        this.symbols = new Object[size];

        //Like alphabet, must be initialized before building.
        for(C channelName : channelNames){
            channelContents.put(channelName, new LinkedList<I>());
        }

        //All transitions invalid by default I guess
        Arrays.fill(successors, 0, size, AbstractCompact.INVALID_STATE);
    }

    public CompactFIFO(Alphabet<I> alphabet, List<C> channelNames, int stateCapacity){
        this(alphabet, channelNames, stateCapacity, DEFAULT_RESIZE_FACTOR);
    }

    public CompactFIFO(Alphabet<I> alphabet, List<C> channelNames) {
        this(alphabet, channelNames, DEFAULT_INIT_CAPACITY);
    }



    //Channels are not modified but which transition uses which is modified
    @Override
    protected void updateTransitionStorage(Payload payload) {
        this.successors = updateTransitionStorage(this.successors, AbstractCompact.INVALID_STATE, payload);
        this.channels = updateTransitionStorage(this.channels, null, payload);
        this.writings = updateTransitionStorage(this.writings, null, payload);
        this.symbols = updateTransitionStorage(this.symbols, null, payload);
    }

    //Self explanatory
    @Override
    public void removeAllTransitions(Integer state) {
        final int lower = state * numInputs();
        final int upper = lower + numInputs();
        Arrays.fill(successors, lower, upper, AbstractCompact.INVALID_STATE);
        Arrays.fill(channels, lower, upper, null);
        Arrays.fill(writings, lower, upper, null);
        Arrays.fill(symbols, lower, upper, null);
    }

    //Conversion human readable -> int on compact storage
    @Override
    public int getIntSuccessor(CompactFIFOTransition<I,C> transition) {
        return transition.getSuccId();
    }


    // The TP is a triplet Channel Boolean Symbol (CBS). Setting it means updating the corresponding arrays
    @Override
    public void setTransitionProperty(CompactFIFOTransition<I,C> transition, List property) {
        final int idx = transition.getMemoryIdx();
        channels[idx] = property.get(0);
        writings[idx] = property.get(1);
        symbols[idx]  = property.get(2);

    }



    @Override
    public List getTransitionProperty(CompactFIFOTransition<I,C> transition) {
        final int idx = transition.getMemoryIdx();
        List al =  new ArrayList();
        al.add(channels[idx]);
        al.add(writings[idx]);
        al.add(symbols[idx]);
        return al;
    }

    // TODO shouldn't we then just store the list as object (TP) and the transition handles the details ?
    @Override
    public CompactFIFOTransition<I, C> createTransition(int successor, List property) {
        CompactFIFOTransition<I,C> cft = new CompactFIFOTransition<I,C>(successor, (C)property.get(0), (Boolean)property.get(1), (I)property.get(2));
        return cft;
    }



    @Override
    public @Nullable CompactFIFOTransition<I,C> getTransition(int state, int input) {
        final int idx = toMemoryIndex(state, input);// get transition id
        final int succ = successors[idx];// follow transition id to get state id

        if (succ == AbstractCompact.INVALID_STATE) {
            return null;
        }

        @SuppressWarnings("unchecked")
        final C channel = (C) channels[idx];
        @SuppressWarnings("unchecked")
        final Boolean writing = (Boolean) writings[idx];
        @SuppressWarnings("unchecked")
        final I symbol = (I) symbols[idx];

        //How and were to store and retrieve CBO ?
        return new CompactFIFOTransition<>(idx, succ, channel, writing, symbol);
    }

    //TODO do we handle nulls correctly hereafter
    @Override
    public void setTransition(int state, int input, @Nullable CompactFIFOTransition<I,C> transition) {
        if (transition == null) {
            setTransition(state, input, AbstractCompact.INVALID_STATE, null);
        } else {
            List property = getTransitionProperty(transition);
            setTransition(state, input, transition.getSuccId(), property);
            transition.setMemoryIdx(toMemoryIndex(state, input));
        }
    }

    //TODO différence avec setTransitionProperty (même code)
    @Override
    public void setTransition(int state, int input, int successor, List property) {
        final int idx = toMemoryIndex(state, input);
        successors[idx] = successor;
        if(property != null) {
            channels[idx] = property.get(0);
            writings[idx] = property.get(1);
            symbols[idx] = property.get(2);
        }
    }


    @Override
    public void setStateProperty(int state, @Nullable Integer property) {
    }

    @Override
    public Integer getStateProperty(int state) {
        return null;
    }

}
