package it.smasini.imageresizer.gui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import it.smasini.imageresizer.files.Renamer;
import it.smasini.imageresizer.images.Resizer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Simone on 29/05/17.
 */
public class ImageResizerForm {
    private JRadioButton radioRes;
    private JRadioButton radioSingleImage;
    private JPanel panel;
    private JButton buttonStartResize;
    private JTextField textFieldInputImages;
    private JButton buttonSelectImage;
    private JTextField textFieldResFolder;
    private JButton buttonSelectResFolder;
    private JLabel labelOutput;
    private JCheckBox checkBoxRename;
    private JPanel panelRename;
    private JCheckBox replaceCheckBox;
    private JTextField textFieldPattern;
    private JTextField textFieldReplaceWith;
    private JCheckBox addAtStartCheckBox;
    private JTextField textFieldAddStart;
    private JCheckBox addAtEndCheckBox;
    private JTextField textFieldAddEnd;
    private JPanel panelSettingSingleImage;
    private JComboBox comboBoxTypeScale;
    private JTextField textFieldPxW;
    private JTextField textFieldPxH;
    private JLabel labelHeigth;
    private JLabel labelWidth;

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
                renamer.setAddAtStart(addAtStartCheckBox.isSelected());
                renamer.setAddAtEnd(addAtEndCheckBox.isSelected());
                renamer.setReplace(replaceCheckBox.isSelected());
                renamer.setStartString(textFieldAddStart.getText());
                renamer.setEndString(textFieldAddEnd.getText());
                renamer.setReplacePattern(textFieldPattern.getText());
                renamer.setReplaceWith(textFieldReplaceWith.getText());

                Resizer resizer = new Resizer("/Users/Simone/Desktop/workspace/ic_time_lock.png", radioRes.isSelected(), renamer);
                resizer.scale();
                //Messages.showErrorDialog(project, "Parsing JSON failed, see detail on Event Log", "Error");
                Messages.showInfoMessage(project, "Resizing success!", "Success");
                frame.dispose();
            }
        });
        ChangeListener radioChange = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setOutputText();
            }
        };
        radioRes.addChangeListener(radioChange);
        radioSingleImage.addChangeListener(radioChange);
        checkBoxRename.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(checkBoxRename.isSelected()){
                    panelRename.setVisible(true);
                }else{
                    panelRename.setVisible(false);
                }
            }
        });
        buttonSelectImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChoicerForImages();
            }
        });
        buttonSelectResFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChoicerForDirectory();
            }
        });
        comboBoxTypeScale.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean percentage = comboBoxTypeScale.getSelectedItem().toString().equals("%");
                if(percentage){
                    labelWidth.setText("percentage");
                    textFieldPxH.setVisible(false);
                    labelHeigth.setVisible(false);
                }else{
                    labelWidth.setText("Width");
                    textFieldPxH.setVisible(true);
                    labelHeigth.setVisible(true);
                }
            }
        });
    }

    private void setOutputText(){
        if(radioRes.isSelected()){
            labelOutput.setText("Res Folder");
            panelSettingSingleImage.setVisible(false);
        }else{
            labelOutput.setText("Output Folder");
            panelSettingSingleImage.setVisible(true);
        }
    }

    private void showFileChoicerForImages() {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, true);
        VirtualFile virtualFile = FileChooser.chooseFile(descriptor, WindowManager.getInstance().findVisibleFrame(), project, null);
        if (virtualFile != null) {
            //PsiDirectory directory = PsiDirectoryFactory.getInstance(project).createDirectory(virtualFile);

            /*mainClassName = directory.getName();
            char[] chars = mainClassName.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            rootName.setText(String.valueOf(chars));*/

            //parser.reset(project, directory);
            String path = virtualFile.getPath();
            //String pkg = "";
            textFieldInputImages.setText(path);

            /*if (path.contains("java")) {
                pkg = path.split("java/")[1].replaceAll("/", ".");
            } else if (path.contains("src")) {
                pkg = path.split("src/")[1].replaceAll("/", ".");
            }
            basePath = path + "/";
            pkgText.setText(pkg);

            */
            //Logger.info("Target path " + path);
        } else {
            //Logger.warn("Empty target path!");
        }
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    private void showFileChoicerForDirectory() {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        VirtualFile virtualFile = FileChooser.chooseFile(descriptor, WindowManager.getInstance().findVisibleFrame(), project, null);
        if (virtualFile != null) {
            //PsiDirectory directory = PsiDirectoryFactory.getInstance(project).createDirectory(virtualFile);
            String path = virtualFile.getPath();
            textFieldResFolder.setText(path);
            //Logger.info("Target path " + path);
        } else {
            //Logger.warn("Empty target path!");
        }
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

}
