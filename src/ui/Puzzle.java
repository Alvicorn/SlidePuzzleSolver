package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


public class Puzzle implements ActionListener, KeyListener {

    private final PuzzleLogic puzzleLogic;
    private final int dimension;
    private static final int HEIGHT = 400;
    private static final int WIDTH = 400;

    private final JButton[][] board;
    private final JFrame frame;
    private final JPanel panel;

    public Puzzle(int dimension) {
        this.puzzleLogic = new PuzzleLogic(dimension);
        this.dimension = dimension;

        // UI components
        this.board = new JButton[this.dimension][this.dimension];
        this.frame = new JFrame("Sliding Puzzle Game");
        this.panel = new JPanel();

        // allow for keyboard movement
        this.frame.addKeyListener(this);
        this.frame.setFocusable(true);
        this.frame.requestFocus();
    } // end of constructor

    /**
     * Gives index value corresponding to [row,col] of a square.
     *
     * @param row Target row.
     * @param col Target column.
     * @return The index of the corresponding to the row and column.
     */
    private int getIndex(int row, int col) {
        return ((row * this.dimension) + col);
    } // end of getIndex()

    /**
     * Generates the UI for the game.
     *
     * @return The puzzle state.
     */
    public ArrayList<Integer> initializeBoard() {
        ArrayList<Integer> puzzle = this.puzzleLogic.generatePuzzle();

        // Assigns unique random number to each square
        for (int index = 0; index < this.puzzleLogic.getSize(); index++) {
            int row = index / this.dimension;  // row number from index
            int col = index % this.dimension; // column number from index
            board[row][col] = new JButton(String.valueOf(puzzle.get(index)));

            // Initializes the empty square and hide it
            if (puzzle.get(index) == 0) {
                this.puzzleLogic.setEmptyCell(index);
                board[row][col].setVisible(false);
            }

            // Decorating each square
            board[row][col].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            board[row][col].setBackground(Color.BLACK);
            board[row][col].setForeground(Color.GREEN);
            board[row][col].addActionListener(this);
            panel.add(board[row][col]);
        }

        // Initializes the Frame
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);

        // Initializes the panel
        panel.setLayout(new GridLayout(this.dimension, this.dimension));
        panel.setBackground(Color.GRAY);

        // Initializes the content pane
        java.awt.Container content = frame.getContentPane();
        content.add(panel, BorderLayout.CENTER);
        content.setBackground(Color.GRAY);
        frame.setVisible(true);
        return this.puzzleLogic.getPuzzleState();
    } // end of initializeBoard()

    /**
     * If any button in the board is pressed, it will perform the
     * required actions associated with the button. Actions like
     * checking isAdjacent(), swapping using swapWithEmpty() and also
     * checks to see whether the game is finished or not.
     *
     * @param event Event performed by the player.
     * @throws IllegalArgumentException If the index = -1
     */
    public void actionPerformed(ActionEvent event) throws IllegalArgumentException {
        JButton buttonPressed = (JButton) event.getSource();
        int index = indexOf(buttonPressed.getText());
        if (index == -1) {
            throw (new IllegalArgumentException("Index out of bounds"));
        }

        if (this.makeMove(index)) { // make a move on the board
            // If the game is finished, "You Win the Game" dialog will appear
            if (this.puzzleLogic.isSolved()) {
                this.showDialog("You Win!");
            }
        }
    } // end of actionPerformed()

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        int emptyCell = this.puzzleLogic.getEmptyCell();
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP -> {
                if (this.makeMove(emptyCell + this.dimension) && // target cell is exactly 1 row above
                        this.puzzleLogic.isSolved()) { // game is finished
                    this.showDialog("You Win!");
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (this.makeMove(emptyCell - this.dimension) && // target cell is exactly 1 row below
                        this.puzzleLogic.isSolved()) { // game is finished
                    this.showDialog("You Win!");
                }
            }
            case KeyEvent.VK_LEFT -> {
                if (this.makeMove(emptyCell + 1) && // target cell is exactly 1 column to the right
                        this.puzzleLogic.isSolved()) { // game is finished
                    this.showDialog("You Win!");
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (this.makeMove(emptyCell - 1) && // target cell is exactly 1 column to the left
                        this.puzzleLogic.isSolved()) { // game is finished
                    this.showDialog("You Win!");
                }
            }
        }
    } // end of keyReleased()

    /**
     * Gives the index by processing the text on square.
     *
     * @param cellNum Number on the button.
     * @return The index of the button.
     */
    private int indexOf(String cellNum) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getText().equals(cellNum)) {
                    return (getIndex(row, col));
                }
            }
        }
        return -1; // wrong input returns -1
    } // end of indexOf()

    /**
     * Checks the row or column with empty square.
     *
     * @return true, if we pressed the button in same row or column
     *              as empty square
     *         false, otherwise
     */
    private boolean makeMove(int index) {
        int oldEmptyCell = this.puzzleLogic.getEmptyCell();

        // validate that the moving piece directly beside the blank
        boolean isValid = this.puzzleLogic.validMove(index);
        if (!isValid) {
            return false;
        }
        // location of the old empty cell
        int oldRow = oldEmptyCell / this.dimension;
        int oldCol = oldEmptyCell % this.dimension;

        // location of the new empty cell
        int newRow = index / this.dimension;
        int newCol = index % this.dimension;

        // update board
        board[oldRow][oldCol].setText(board[newRow][newCol].getText());

        // Swap the empty square with the given square
        board[oldRow][oldCol].setVisible(true);
        board[newRow][newCol].setText(Integer.toString(0));
        board[newRow][newCol].setVisible(false);
        this.puzzleLogic.setEmptyCell(getIndex(newRow, newCol));
        return true;
    } // end of makeMove()

    private void showDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

} // end of Puzzle.java