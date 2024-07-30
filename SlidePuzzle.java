import javax.swing.*;
import java.awt.*;

import ui.Puzzle;


public class SlidePuzzle {

    public static void main(String[] args) {
        // Run GUI code on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create a frame for selecting dimension
            JFrame dimensionFrame = new JFrame("Select Puzzle Dimension");
            dimensionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dimensionFrame.setSize(300, 150);
            dimensionFrame.setLayout(new FlowLayout());

            // Create a SpinnerModel with a range of 2 to 100
            SpinnerModel model = new SpinnerNumberModel(2, 2, 10, 1);
            JSpinner dimensionSpinner = new JSpinner(model);

            // Create a button to confirm dimension selection
            JButton startButton = new JButton("Start Puzzle");

            // Add components to the frame
            dimensionFrame.add(new JLabel("Select Puzzle Dimension:"));
            dimensionFrame.add(dimensionSpinner);
            dimensionFrame.add(startButton);

            // Set up button action listener
            startButton.addActionListener(e -> {
                int dimension = (Integer) dimensionSpinner.getValue();
                dimensionFrame.dispose(); // Close the dimension selection frame

                // Initialize and show the puzzle with the selected dimension
                new Puzzle(dimension);
            });

            // Make the dimension selection frame visible
            dimensionFrame.setVisible(true);
        });

    } // end of main()

} // end of SlidePuzzle.java