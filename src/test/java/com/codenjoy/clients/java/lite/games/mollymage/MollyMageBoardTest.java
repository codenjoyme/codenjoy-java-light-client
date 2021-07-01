package com.codenjoy.clients.java.lite.games.mollymage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MollyMageBoardTest {

    @Test
    public void printBoard() {
        MollyMageBoard board = new MollyMageBoard("" +
                "☼☼☼☼☼☼☼☼☼" +
                "☼1 ♣   ♠☼" +
                "☼#2  &  ☼" +
                "☼# 3 ♣ ♠☼" +
                "☼☺  4   ☼" +
                "☼   ♥ H☻☼" +
                "☼x H ҉҉҉☼" +
                "☼& &    ☼" +
                "☼☼☼☼☼☼☼☼☼");

        assertEquals("" +
                /*8*/"☼☼☼☼☼☼☼☼☼\n" +
                /*7*/"☼1 ♣   ♠☼\n" +
                /*6*/"☼#2  &  ☼\n" +
                /*5*/"☼# 3 ♣ ♠☼\n" +
                /*4*/"☼☺  4   ☼\n" +
                /*3*/"☼   ♥ H☻☼\n" +
                /*2*/"☼x H ҉҉҉☼\n" +
                /*1*/"☼& &    ☼\n" +
                /*0*/"☼☼☼☼☼☼☼☼☼\n" +
                    /*012345678*/
                "\n" +
                "Hero at: [1,4]\n" +
                "Other heroes at: [[3,7], [4,3], [5,5], [7,5], [7,7]]\n" +
                "Ghosts at: [[1,1], [3,1], [5,6]]\n" +
                "Potions at: [[1,7], [2,6], [3,5], [4,4], [7,3], [7,5], [7,7]]\n" +
                "Blasts at: [[5,2], [6,2], [7,2]]\n" +
                "Expected blasts at: []", board.toString());
    }
}