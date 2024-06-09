package desktoppet.ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import desktoppet.control.World;
import desktoppet.control.loader.AnimalLoader;

import java.awt.*;
import java.io.File;

public class SettingsWindow extends JDialog
{
    public SettingsWindow(World worldRef)
    {
        super();
        setTitle("Settings");
        this.setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);
        setAlwaysOnTop(true);
        setResizable(false);

        // Center the dialog on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // Add a close button and a exit button at the bottom
        JPanel panel = new JPanel();
        // center two buttons
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);
        add(panel, BorderLayout.SOUTH);

        // add load button
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JAR Files", "jar"));
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.showOpenDialog(this);
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files)
            {
                try
                {
                    AnimalLoader.LoadAnimalFromJar(file.getPath(), worldRef);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, ex.getClass().getSimpleName() + ": " + ex.getMessage() + "\nThis is not a valid desktoppet plugin.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(loadButton, BorderLayout.NORTH);
    }
}
