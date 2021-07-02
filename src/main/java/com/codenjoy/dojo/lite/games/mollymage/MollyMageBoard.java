package com.codenjoy.dojo.lite.games.mollymage;

import com.codenjoy.dojo.lite.engine.Board;
import com.codenjoy.dojo.lite.engine.Point;

import java.util.*;

public class MollyMageBoard {

    private final Board board;

    public MollyMageBoard(String message) {
        this.board = new Board(MollyMageElement.elements, message);
    }

    public Character getAt(Point pt) {
        if (pt.isValid(board.getSize())) {
            return MollyMageElement.WALL.ch();
        }
        return board.getAt(pt);
    }

    public Point findHero() {
        Set<Point> points = board.find(MollyMageElement.HERO.ch(),
                MollyMageElement.POTION_HERO.ch(),
                MollyMageElement.DEAD_HERO.ch());
        if (points.isEmpty()) {
            throw new NoSuchElementException("hero element has not been found");
        }
        return points.iterator().next();
    }

    public boolean isGameOver() {
        return !board.find(MollyMageElement.DEAD_HERO.ch()).isEmpty();
    }

    public Set<Point> findOtherHeroes() {
        return board.find(MollyMageElement.OTHER_HERO.ch(),
                MollyMageElement.OTHER_POTION_HERO.ch(),
                MollyMageElement.OTHER_DEAD_HERO.ch());
    }

    public Set<Point> findBarriers() {
        Set<Point> points = new TreeSet<>();
        points.addAll(findWalls());
        points.addAll(findGhosts());
        points.addAll(findTreasureBoxes());
        points.addAll(findPotions());
        points.addAll(findOtherHeroes());
        return points;
    }

    public Set<Point> findWalls() {
        return board.find(MollyMageElement.WALL.ch());
    }

    public Set<Point> findGhosts() {
        return board.find(MollyMageElement.GHOST.ch());
    }

    public Set<Point> findTreasureBoxes() {
        return board.find(MollyMageElement.TREASURE_BOX.ch());
    }

    public Set<Point> findPotions() {
        return board.find(MollyMageElement.POTION_TIMER_1.ch(),
                MollyMageElement.POTION_TIMER_2.ch(),
                MollyMageElement.POTION_TIMER_3.ch(),
                MollyMageElement.POTION_TIMER_4.ch(),
                MollyMageElement.POTION_TIMER_5.ch(),
                MollyMageElement.POTION_HERO.ch(),
                MollyMageElement.OTHER_POTION_HERO.ch());
    }

    public Set<Point> findBlasts() {
        return board.find(MollyMageElement.BOOM.ch());
    }

    public Set<Point> predictFutureBlasts() {
        // TODO: implement
        return Collections.emptySet();
    }

    public Set<Point> findPerks() {
        return board.find(MollyMageElement.POTION_COUNT_INCREASE.ch(),
                MollyMageElement.POTION_REMOTE_CONTROL.ch(),
                MollyMageElement.POTION_IMMUNE.ch(),
                MollyMageElement.POTION_BLAST_RADIUS_INCREASE.ch());
    }

    @Override
    public String toString() {
        return board.toString() +
                "\nHero at: " + findHero() +
                "\nOther heroes at: " + findOtherHeroes() +
                "\nGhosts at: " + findGhosts() +
                "\nPotions at: " + findPotions() +
                "\nBlasts at: " + findBlasts() +
                "\nExpected blasts at: " + predictFutureBlasts();
    }
}
