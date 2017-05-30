package it.smasini.imageresizer.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import it.smasini.imageresizer.files.Renamer;
import it.smasini.imageresizer.images.Resizer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Simone on 29/05/17.
 */
public class ImageResizerForm {
    private JRadioButton radioRes;
    private JRadioButton radioSingleImage;
    private JPanel panel;
    private JButton buttonStartResize;

    private JFrame frame;
    private Project project;

    public ImageResizerForm(Project project) {
        this.project = project;

        ButtonGroup group = new ButtonGroup();
        group.add(radioRes);
        group.add(radioSingleImage);
    }

    public void show() {
        setListener();
        frame = new JFrame("Image Resizer");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(frame.getParent());
        frame.setVisible(true);
    }

    private void setListener(){
        buttonStartResize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Renamer renamer = new Renamer();

                Resizer resizer = new Resizer("/Users/Simone/Desktop/workspace/ic_time_lock.png", radioRes.isSelected(), renamer);
                resizer.scale();
                //Messages.showErrorDialog(project, "Parsing JSON failed, see detail on Event Log", "Error");
                Messages.showInfoMessage(project, "Resizing success!", "Success");
                frame.dispose();
            }
        });
    }
}
