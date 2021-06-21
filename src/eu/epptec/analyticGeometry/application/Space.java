package eu.epptec.analyticGeometry.application;

import eu.epptec.analyticGeometry.shapes.Shape;

import java.util.*;

public class Space {
    private Map<String, Shape> shapeMap = new HashMap<>();

    public Map<String, Shape> getShapeMap() {
        return shapeMap;
    }

    public Shape getShape(String name) {
        return shapeMap.get(name);
    }

    public void addShape(String name, Shape shape) {
        shapeMap.put(name, shape);
    }

    public void removeShape(String name) {
        shapeMap.remove(name);
    }

    @Override
    public String toString() {
        String outputStr;
        outputStr = "{";
        List<String> shapeStrList = new LinkedList<>();
        shapeMap.entrySet().forEach(entry -> shapeStrList.add(entry.getKey() + ": " + entry.getValue().toString()));

        StringJoiner joiner = new StringJoiner("\n");
        shapeStrList.forEach(shapeStr -> joiner.add(shapeStr));
        outputStr += joiner.toString();
        outputStr += "}";
        return outputStr;
    }
}
