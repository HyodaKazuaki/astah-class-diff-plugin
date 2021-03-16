package jp.ex_t.kazuaki.change_vision

import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException
import com.change_vision.jude.api.inf.model.IClassDiagram
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate.UnExpectedException
import com.change_vision.jude.api.inf.ui.IWindow
import javax.swing.JOptionPane

class TemplateAction : IPluginActionDelegate {
    private var isWatching = false
    private val projectChangedListener = ProjectChangedListener()
    @Throws(UnExpectedException::class)
    override fun run(window: IWindow) {
        try {
            val api = AstahAPI.getAstahAPI()
            val projectAccessor = api.projectAccessor
            if (isWatching) {
                JOptionPane.showMessageDialog(window.parent, "Watch stop")
                projectAccessor.removeProjectEventListener(projectChangedListener)
            } else {
                val diagramViewManager = api.viewManager.diagramViewManager
                println(diagramViewManager.openDiagrams)
                if (diagramViewManager.openDiagrams.first() !is IClassDiagram)
                    throw Exception()
                JOptionPane.showMessageDialog(window.parent, "Watch start")
                projectAccessor.addProjectEventListener(projectChangedListener)
            }
            isWatching = !isWatching
        } catch (e: ProjectNotFoundException) {
            val message = "Project is not opened.Please open the project or create new project."
            JOptionPane.showMessageDialog(window.parent, message, "Warning", JOptionPane.WARNING_MESSAGE)
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(
                window.parent,
                "Unexpected error has occurred.",
                "Alert",
                JOptionPane.ERROR_MESSAGE
            )
            throw UnExpectedException()
        }
    }
}