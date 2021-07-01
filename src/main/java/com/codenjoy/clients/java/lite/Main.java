package com.codenjoy.clients.java.lite;

import com.codenjoy.clients.java.lite.engine.Solver;
import com.codenjoy.clients.java.lite.engine.WebSocketRunner;
import com.codenjoy.clients.java.lite.games.mollymage.MollyMageSolver;

import java.util.NoSuchElementException;

public class Main {

    private static String game = "mollymage";
    private static String url = "http://localhost:8080/codenjoy-contest/board/player/0?code=000000000000";

    public static void main(String[] args) {
        if (args.length == 2) {
            game = args[0];
            url = args[1];
        }
        Solver solver = determineGameSolver(game);
        new WebSocketRunner(url).run(solver);
    }


    private static Solver determineGameSolver(String game) {
        switch (game) {
            case "mollymage":
                return new MollyMageSolver();
            default:
                throw new NoSuchElementException("no solver for game: " + game);
        }
    }
}
