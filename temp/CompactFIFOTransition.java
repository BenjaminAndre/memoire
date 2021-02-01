package main.java;

import net.automatalib.automata.transducers.impl.compact.CompactMealyTransition;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class CompactFIFOTransition<I,C> implements Serializable {

    //Int name of transition
    private int memoryIdx;
    //Int name of successor
    private final int succId;
    //Symbol to read/add to channel
    private I symbol;
    //Channel to read/write to
    private C channel;
    // Is it ? or !
    private boolean isWritingTransition;

    CompactFIFOTransition(int succId, C channel, boolean isWritingTransition, I symbol){
        this(-1, succId, channel, isWritingTransition, symbol);
    }

    CompactFIFOTransition(int memoryIdx, int succId, C channel, boolean isWritingTransition, I symbol){
        this.memoryIdx = memoryIdx;
        this.succId = succId;
        this.channel = channel;
        this.isWritingTransition = isWritingTransition;
        this.symbol = symbol;
    }

    public int getSuccId() { return succId; }

    int getMemoryIdx() {return memoryIdx; }

    void setMemoryIdx(int memoryIdx) { this.memoryIdx = memoryIdx;}

    //I guess the default -1 idx is for invalid transitions
    boolean isAutomatonTransition() { return memoryIdx >= 0; }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompactMealyTransition)) {
            return false;
        }
        final CompactFIFOTransition<?,?> other = (CompactFIFOTransition<?,?>) o;
        return  memoryIdx == other.memoryIdx &&
                succId == other.succId &&
                isWritingTransition == other.isWritingTransition &&
                Objects.equals(symbol, other.symbol) &&
                Objects.equals(channel, other.channel);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Integer.hashCode(memoryIdx);
        result = 31 * result + Integer.hashCode(succId);
        result = 31 * result + Objects.hashCode(symbol);
        result = 31 * result + Objects.hashCode(channel);
        result = 31 * result + Boolean.hashCode(isWritingTransition);
        return result;
    }

}
