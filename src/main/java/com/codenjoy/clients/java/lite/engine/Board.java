package com.codenjoy.clients.java.lite.engine;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class Board {

    private Character[] elements;
    private final int size;

    public Board(Set<Character> supportedElements, String message) {
        message = message.replace("board=", "");
        initElementsArray(supportedElements, message);
        this.size = (int) Math.sqrt(elements.length);
    }

    private void initElementsArray(Set<Character> supportedElements, String message) {
        elements = new Character[message.length()];
        for (int i = 0; i < elements.length; i++) {
            Character nextElement = message.charAt(i);
            if (supportedElements.contains(nextElement)) {
                elements[i] = nextElement;
            } else {
                throw new IllegalArgumentException("invalid element: " + nextElement);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Character getAt(Point pt) {
        if (!pt.isValid(size)) {
            throw new IllegalArgumentException("invalid point " + pt);
        }
        return elements[pointToIndex(pt)];
    }

    public Set<Point> find(Character... wanted) {
        return find(Set.of(wanted));
    }

    public Set<Point> find(Set<Character> wanted) {
        Set<Point> points = new TreeSet<>();
        for (int i = 0; i < elements.length; i++) {
            if (wanted.contains(elements[i])) {
                points.add(indexToPoint(i));
            }
        }
        return points;
    }

    public Optional<Point> findFirst(Character... wanted) {
        return findFirst(Set.of(wanted));
    }

    public Optional<Point> findFirst(Set<Character> wanted) {
        for (int i = 0; i < elements.length; i++) {
            if (wanted.contains(elements[i])) {
                return Optional.of(indexToPoint(i));
            }
        }
        return Optional.empty();
    }

    public boolean isAt(Point pt, Character... wanted) {
        return isAt(pt, Set.of(wanted));
    }

    public boolean isAt(Point pt, Set<Character> wanted) {
        if (!pt.isValid(size)) {
            return false;
        }
        return wanted.contains(getAt(pt));
    }

    public Set<Character> findNear(Point pt) {
        Point right = new Point(pt.x() + 1, pt.y());
        Point left = new Point(pt.x() - 1, pt.y());
        Point up = new Point(pt.x(), pt.y() + 1);
        Point down = new Point(pt.x(), pt.y() - 1);
        return Stream.of(right, left, up, down)
                .filter(p -> p.isValid(size))
                .map(this::getAt)
                .collect(toCollection(TreeSet::new));
    }

    public int countNear(Point pt, Character... elements) {
        return countNear(pt, Set.of(elements));
    }

    public int countNear(Point pt, Set<Character> elements) {
        Set<Character> elementsNear = findNear(pt);
        elementsNear.retainAll(elements);
        return elementsNear.size();
    }

    public boolean isNear(Point pt, Character... elements) {
        return isNear(pt, Set.of(elements));
    }

    public boolean isNear(Point pt, Set<Character> elements) {
        return countNear(pt, elements) != 0;
    }

    private int pointToIndex(Point pt) {
        return pointToIndex(pt.x(), pt.y());
    }

    private int pointToIndex(int x, int y) {
        return (size - 1 - y) * size + x;
    }

    private Point indexToPoint(int index) {
        int x = index % size;
        int y = (int) Math.ceil(size - 1 - index / size);
        return new Point(x, y);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                builder.append(elements[pointToIndex(x, y)]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
