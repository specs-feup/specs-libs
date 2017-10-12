/*
 * Copyright 2011 SPeCS Research Group.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package pt.up.fe.specs.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Each key is associated to a list of values.
 * 
 * @author Joao Bispo
 */
public class MultiMap<K, V> {

    @FunctionalInterface
    public interface MultiMapProvider<K, V> {
        Map<K, List<V>> newInstance();
    }

    // --------------------------------------------------------------------- attributes
    // ---------- - - - - - - - - - - - - - - - - - - - - - - - - - - ---------- static

    // ---------- - - - - - - - - - - - - - - - - - - - - - - - - - ----------- dynamic

    private final Map<K, List<V>> map;

    // ----------------------------------------------------------------- public_Methods
    // ---------- - - - - - - - - - - - - - - - - - - - - - - - ----------- constructor

    public MultiMap() {
        this.map = new HashMap<>();
    }

    public MultiMap(MultiMapProvider<K, V> mapProvider) {
        this.map = mapProvider.newInstance();
    }

    // ---------- - - - - - - - - - - - - - - - - - - - - - - - - - - ---------- static

    // ---------- - - - - - - - - - - - - - - - - - - - - - - - - - ----------- dynamic

    /**
     * Returns the values associated to the parameter key.
     * 
     * @param key
     *            The key the user wants the values of.
     * @return the values associated to the parameter key.
     */
    public List<V> get(K key) {
        return this.map.getOrDefault(key, Collections.emptyList());
    }

    /**
     * Attributes the parameter value to the parameter key.
     * 
     * @param key
     *            the key the user wants to attribute a value to.
     * @param value
     *            the value the user wants to attribute to the key.
     */
    public void put(K key, V value) {
        List<V> values = this.map.get(key);
        if (values == null) {
            values = new ArrayList<>();
            this.map.put(key, values);
        }

        values.add(value);
    }

    /**
     * Attributes the parameter values to the parameter key.
     * 
     * @param key
     *            the key the user wants to attribute a values to.
     * @param values
     *            a List containing all the values the user wants to attribute to the key.
     */
    public void put(K key, List<V> values) {

        this.map.put(key, new ArrayList<>(values));

    }

    /**
     * TODO
     * 
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (K key : this.map.keySet()) {
            List<V> values = this.map.get(key);
            builder.append(key).append(": ");
            if (values.isEmpty()) {
                builder.append("(empty)");
            } else {
                builder.append(values.get(0));
            }

            for (int i = 1; i < values.size(); i++) {
                builder.append(", ").append(values.get(i));
            }
            builder.append("\n");
        }

        return builder.toString();

    }

    public Map<K, List<V>> getMap() {
        return this.map;
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public static <K, V> MultiMap<K, V> newInstance() {
        return new MultiMap<>();
    }

    public List<V> remove(K key) {
        return this.map.remove(key);
    }

    public Collection<List<V>> values() {
        return map.values();
    }

    public List<V> flatValues() {
        return map
                .values()
                .stream()
                .flatMap(v -> v.stream())
                .collect(Collectors.toList());
    }

    public Set<Entry<K, List<V>>> entrySet() {
        return map.entrySet();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MultiMap && equals((MultiMap<?, ?>) obj);
    }

    public boolean equals(MultiMap<?, ?> obj) {
        if (obj == null) {
            return false;
        }

        return this.map.equals(obj.map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    public void clear() {
        map.clear();
    }
}
