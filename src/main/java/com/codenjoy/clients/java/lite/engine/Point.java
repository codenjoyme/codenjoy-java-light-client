package com.codenjoy.clients.java.lite.engine;

import java.util.Objects;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isValid(int boardSize) {
        return (x >= 0 && x <= boardSize) && (y >= 0 && y <= boardSize);
    }

    @Override
    public int compareTo(Point that) {
        return this.toString().compareTo(that.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]", x, y);
    }
}
