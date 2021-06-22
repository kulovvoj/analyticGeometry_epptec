package eu.epptec.analyticGeometry.application;

import eu.epptec.analyticGeometry.shapes.Shape;

import java.util.*;

public class Space {
    final private Map<String, Shape> shapeMap = new HashMap<>();

    private void checkExistence(String name) {
        if (!shapeMap.containsKey(name))
            throw new NoSuchElementException("Shape with the name \"" + name + "\" doesn't exist.");
    }

    public Shape getShape(String name) {
        checkExistence(name);
        return shapeMap.get(name);
    }

    public void addShape(String name, Shape shape) {
        shapeMap.put(name, shape);
    }

    public void removeShape(String name) {
        checkExistence(name);
        shapeMap.remove(name);
    }

    @Override
    public String toString() {
        String outputStr;
        outputStr = "{";
        List<String> shapeStrList = new LinkedList<>();
        shapeMap.forEach((key, value) -> shapeStrList.add(key + ": " + value.toString()));

        StringJoiner joiner = new StringJoiner(",\n");
        shapeStrList.forEach(joiner::add);
        outputStr += joiner.toString();
        outputStr += "}";
        return outputStr;
    }
}
