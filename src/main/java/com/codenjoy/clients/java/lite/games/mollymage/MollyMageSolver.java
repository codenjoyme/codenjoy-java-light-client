package com.codenjoy.clients.java.lite.games.mollymage;

import com.codenjoy.clients.java.lite.engine.Solver;

public class MollyMageSolver implements Solver {

    @Override
    public String answer(String message) {
        MollyMageBoard board = new MollyMageBoard(message);
        System.out.println("Board \n" + board);
        String action = nextAction(board).toString();
        System.out.println("\nAnswer: " + action);
        System.out.println("-------------------------------------------------------------");
        return action;
    }

    public MollyMageAction nextAction(MollyMageBoard board) {
        // TODO: write your code here
        return MollyMageAction.ACT;
    }
}
