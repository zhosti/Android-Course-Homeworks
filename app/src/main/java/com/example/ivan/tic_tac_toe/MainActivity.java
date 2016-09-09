package com.example.ivan.tic_tac_toe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    int board[][];
    Button tiles[][];
    TextView textView;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBoard();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        setBoard();
        return true;
    }

    // Set up the game board.
    private void setBoard() {

        tiles = new Button[4][4];
        board = new int[4][4];

        textView = (TextView) findViewById(R.id.dialogue);

        tiles[1][3] = (Button) findViewById(R.id.one);
        tiles[1][2] = (Button) findViewById(R.id.two);
        tiles[1][1] = (Button) findViewById(R.id.three);

        tiles[2][3] = (Button) findViewById(R.id.four);
        tiles[2][2] = (Button) findViewById(R.id.five);
        tiles[2][1] = (Button) findViewById(R.id.six);

        tiles[3][3] = (Button) findViewById(R.id.seven);
        tiles[3][2] = (Button) findViewById(R.id.eight);
        tiles[3][1] = (Button) findViewById(R.id.nine);

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++)
                board[i][j] = 2;
        }

        textView.setText("Click a button to start.");

        // add the click listeners for each button
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {

                tiles[i][j].setOnClickListener(new MyClickListener(i, j));

                if(!tiles[i][j].isEnabled()) {
                    tiles[i][j].setText(" ");
                    tiles[i][j].setEnabled(true);
                }
            }
        }
    }

    class MyClickListener implements View.OnClickListener {
        int x;
        int y;


        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            if (tiles[x][y].isEnabled()) {
                tiles[x][y].setEnabled(false);
                tiles[x][y].setText("O");
                board[x][y] = 0;
                textView.setText("");

                if (!checkBoard()) {
                   takeTurn();
                }
            }
        }
    }

    public void takeTurn() {

        Random rand = new Random();

        int a = rand.nextInt(4);
        int b = rand.nextInt(4);

        while(a==0 || b==0 || board[a][b]!=2) {
            a = rand.nextInt(4);
            b = rand.nextInt(4);
        }
        markSquare(a,b);

    }


    private void markSquare(int x, int y) {
        tiles[x][y].setEnabled(false);
        tiles[x][y].setText("X");
        board[x][y] = 1;
        checkBoard();
    }

    private boolean checkWinner(int[][] board, int size, int player) {
        // check each column
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (board[y].equals(player)) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // check each row
        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (board[y].equals(player)) {
                    total++;
                }
            }
            if (total >= size) {
                return true; // they win
            }
        }

        // forward diag
        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y && board[y].equals(player)) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // they win
        }

        // backward diag
        total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && board[y].equals(player)) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true; // computer win
        }

        return false; // nobody won
    }

    // check the board to see if someone has won
    private boolean checkBoard() {
        boolean gameOver = false;

        if ((board[1][1] == 0 && board[2][2] == 0 && board[3][3] == 0)
                || (board[1][3] == 0 && board[2][2] == 0 && board[3][1] == 0)
                || (board[1][2] == 0 && board[2][2] == 0 && board[3][2] == 0)
                || (board[1][3] == 0 && board[2][3] == 0 && board[3][3] == 0)
                || (board[1][1] == 0 && board[1][2] == 0 && board[1][3] == 0)
                || (board[2][1] == 0 && board[2][2] == 0 && board[2][3] == 0)
                || (board[3][1] == 0 && board[3][2] == 0 && board[3][3] == 0)
                || (board[1][1] == 0 && board[2][1] == 0 && board[3][1] == 0)) {

            textView.setText("Game over. You win!");
            gameOver = true;
        } else if ((board[1][1] == 1 && board[2][2] == 1 && board[3][3] == 1)
                || (board[1][3] == 1 && board[2][2] == 1 && board[3][1] == 1)
                || (board[1][2] == 1 && board[2][2] == 1 && board[3][2] == 1)
                || (board[1][3] == 1 && board[2][3] == 1 && board[3][3] == 1)
                || (board[1][1] == 1 && board[1][2] == 1 && board[1][3] == 1)
                || (board[2][1] == 1 && board[2][2] == 1 && board[2][3] == 1)
                || (board[3][1] == 1 && board[3][2] == 1 && board[3][3] == 1)
                || (board[1][1] == 1 && board[2][1] == 1 && board[3][1] == 1)) {

            textView.setText("Game over. You lost!");
            gameOver = true;
        } else {
            boolean empty = false;
            for(int i = 1; i <= 3; i++) {
                for(int j = 1; j <= 3; j++) {
                    if(board[i][j] == 2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;
                textView.setText("Game over. It's a draw!");
            }
        }
        return gameOver;
    }
}

