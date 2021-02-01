package main.java;/* Copyright (C) 2013-2020 TU Dortmund
 * This file is part of AutomataLib, http://www.automatalib.net/.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Map;

import net.automatalib.automata.transducers.MooreMachine;
import net.automatalib.automata.visualization.AutomatonVisualizationHelper;

public class FIFOVisualizationHelper<S, I, T, TP>
        extends AutomatonVisualizationHelper<S, I, T, FIFO<S, I, T, TP>> {

    public FIFOVisualizationHelper(FIFO<S, I, T, TP> automaton) {
        super(automaton);
    }

    @Override
    public boolean getNodeProperties(S node, Map<String, String> properties) {
        if (!super.getNodeProperties(node, properties)) {
            return false;
        }
        final StringBuilder labelBuilder = new StringBuilder();
        labelBuilder.append(String.valueOf(node)).append(" / ");
        properties.put(NodeAttrs.LABEL, labelBuilder.toString());
        return true;
    }

}