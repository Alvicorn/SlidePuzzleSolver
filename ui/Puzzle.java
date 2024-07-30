/**
 * Puzzle.java
 *
 * @author Alvin Tsang
 *
 * UI elements for the slide puzzle.
 */
package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import solver.Solver;


public class Puzzle implements ActionListener, KeyListener {

    private final PuzzleLogic puzzleLogic;
    private final int dimension;
    private static final int HEIGHT = 400;
    private static final int WIDTH = 400;
    private final JButton[][] board;
    private final JFrame frame = new JFrame("Sliding Puzzle Game");
    private final JPanel panel = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();

    public Puzzle(int dimension) {
        this.puzzleLogic = new PuzzleLogic(dimension);
        this.dimension = dimension;
        this.board = new JButton[this.dimension][this.dimension];
        initializeUI();
    }

    /**
     * Initialize the UI elements of the game
     */
    private void initializeUI() {
        // Allow for keyboard movement
        this.frame.addKeyListener(this);
        this.frame.setFocusable(true);
        this.frame.requestFocus();

        setupMenuBar();
        initializeBoard();

        // Set up the frame
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    } // end of initializeUI()

    /**
     * Set up the menu bar
     */
    private void setupMenuBar() {
        JMenu helpMenu = new JMenu("Menu");
        addSolveItemToMenu(helpMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
    } // end of setupMenuBar()

    /**
     * Set up the solve button in the menu bar
     * @param menu Parent menu selector
     */
    private void addSolveItemToMenu(JMenu menu) {
        // solve option
        JMenuItem solveItem = new JMenuItem("Solve");
        menu.add(solveItem);
        Solver solver = new Solver(dimension);

        solveItem.addActionListener(e -> {
            System.out.println("Attempting to solve...");
            disableButtons();
            ArrayList<Integer> solution = solver.solve(puzzleLogic.getPuzzleState());

            // Use a Swing Timer to handle periodic updates
            Timer timer = new Timer(500, new ActionListener() {
                private int currentIndex = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentIndex < solution.size()) {
                        int action = solution.get(currentIndex++);
                        executeAction(action);
                    } else {
                        // Stop the timer when all actions are executed
                        ((Timer) e.getSource()).stop();
                        JOptionPane.showMessageDialog(null, "Puzzle solved. Better luck next time!");
                    }
                }
            });
            timer.start();
        });
    } // addSolveItemToMenu()

    /**
     * Disable the game tiles, preventing the user from changing the game board.
     */
    private void disableButtons() {
        for (int index = 0; index < this.puzzleLogic.getSize(); index++) {
            int row = index / this.dimension;  // row number from index
            int col = index % this.dimension; // column number from index
            board[row][col].setEnabled(false);
        }
    } // disableButtons()

    /**
     * Gives index value corresponding to [row,col] of a square
     * @param row row
     * @param col column
     * @return the index of the corresponding to the row and column
     */
    private int getIndex(int row, int col) {return ((row * this.dimension) + col);} // end of getIndex()

    /**
     * Generates the UI for the game
     */
    public void initializeBoard() {
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
            board[row][col].setEnabled(true);
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
    } // end of initializeBoard()

    /**
     * If any button in the board is pressed, it will perform the
     * required actions associated with the button. Actions like
     * checking isAdjacent(), swapping using swapWithEmpty() and also
     * checks to see whether the game is finished or not.
     *
     * @param event, event performed by the player
     * @throws IllegalArgumentException, if the <tt>index = -1 </tt>
     */
    public void actionPerformed(ActionEvent event) throws IllegalArgumentException {
        JButton buttonPressed = (JButton) event.getSource();
        int index = indexOf(buttonPressed.getText());
        if (index == -1) {
            throw (new IllegalArgumentException("Index should be between 0-15"));
        }

        if (this.makeMove(index)) { // make a move on the board
            // If the game is finished, "You Win the Game" dialog will appear
            if (this.puzzleLogic.isSolved()) {
                JOptionPane.showMessageDialog(null, "You Win The Game.");
            }
        }
    } // end of actionPerformed()

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {executeAction(e.getKeyCode());}

    /**
     * Gives the index by processing the text on square
     * @param cellNum, number on the button
     * @return the index of the button
     */
    private int indexOf(String cellNum) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getText().equals(cellNum)) {
                    return (getIndex(row, col));
                }
            }
        }
        return -1;   // Wrong input returns -1
    } // end of indexOf()

    /**
     * Execute an action given a keyCode. Make the action to visually show it on the UI.
     * @param keyCode direction to move the empty cell
     */
    private void executeAction(int keyCode) {
        int emptyCell = this.puzzleLogic.getEmptyCell();
        switch (keyCode) {
            case KeyEvent.VK_DOWN:
                if (this.makeMove(emptyCell + this.dimension)) { // target cell is exactly 1 row above
                    if (this.puzzleLogic.isSolved()) { // game is finished
                        disableButtons();
                        JOptionPane.showMessageDialog(null, "You Win The Game.");
                    }
                }
                break;
            case KeyEvent.VK_UP:
                if (this.makeMove(emptyCell - this.dimension)) { // target cell is exactly 1 row below
                    if (this.puzzleLogic.isSolved()) { // game is finished
                        disableButtons();
                        JOptionPane.showMessageDialog(null, "You Win The Game.");
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (this.makeMove(emptyCell + 1)) { // target cell is exactly 1 column to the right
                    if (this.puzzleLogic.isSolved()) { // game is finished
                        disableButtons();
                        JOptionPane.showMessageDialog(null, "You Win The Game.");
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if (this.makeMove(emptyCell - 1)) { // target cell is exactly 1 column to the left
                    if (this.puzzleLogic.isSolved()) { // game is finished
                        disableButtons();
                        JOptionPane.showMessageDialog(null, "You Win The Game.");
                    }
                }
                break;
        }
    } // executeAction()

    /**
     * Checks the row or column with empty square
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

} // end of Puzzle.java
