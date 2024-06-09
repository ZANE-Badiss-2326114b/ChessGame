package fr.univamu.iut.s201_chess;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessGameControllerTest {

    @Before
    public void setUp() {
        ChessGameController.restartGame();
    }

    @Test
    public void testSwitchTurn() {
        assertEquals(PieceColor.WHITE, ChessGameController.getTurnColor());
        ChessGameController.switchTurn();
        assertEquals(PieceColor.BLACK, ChessGameController.getTurnColor());
        ChessGameController.switchTurn();
        assertEquals(PieceColor.WHITE, ChessGameController.getTurnColor());
    }

    @Test
    public void testRestartGame() {
        ChessGameController.switchTurn();
        ChessGameController.restartGame();
        assertEquals(PieceColor.WHITE, ChessGameController.getTurnColor());
    }

    @Test
    public void testUpdateTileColors() {
        Tile[][] board = ChessGameController.getBoard();
        for (int y = 0; y < ChessGame.HEIGHT; y++) {
            for (int x = 0; x < ChessGame.WIDTH; x++) {
                board[x][y] = new Tile((x + y) % 2 == 0, x, y);
            }
        }

        ChessGameController.updateTileColors();

        for (int y = 0; y < ChessGame.HEIGHT; y++) {
            for (int x = 0; x < ChessGame.WIDTH; x++) {
                Tile tile = board[x][y];
                boolean light = (x + y) % 2 == 0;
                assertEquals(light ? "#feb" : "#582", tile.getFill().toString());
            }
        }
    }
}
