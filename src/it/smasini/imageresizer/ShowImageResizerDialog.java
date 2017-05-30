package it.smasini.imageresizer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import it.smasini.imageresizer.gui.ImageResizerForm;

/**
 * Created by Simone on 29/05/17.
 */
public class ShowImageResizerDialog extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        //Logger.init();
        ImageResizerForm form = new ImageResizerForm(project);
        form.show();
    }
}
