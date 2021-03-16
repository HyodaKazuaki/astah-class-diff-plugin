package jp.ex_t.kazuaki.change_vision

import com.change_vision.jude.api.inf.model.*
import com.change_vision.jude.api.inf.presentation.IPresentation
import com.change_vision.jude.api.inf.project.ProjectEvent
import com.change_vision.jude.api.inf.project.ProjectEventListener

enum class Operation {
    ADD,
    MODIFY,
    REMOVE
}

class ProjectChangedListener: ProjectEventListener {
    override fun projectChanged(e: ProjectEvent) {
        print("========== ")
        print("Begin Transaction")
        println(" ==========")
        val projectEditUnit = e.projectEditUnit.filter { it.entity != null }
        projectEditUnit
            .forEach {
                when (val entity = it.entity) {
                    is IClassDiagram -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity.name}(IClassDiagram)")
                    }
                    is IClass -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity.name}(IClass)")
                    }
                    is IAssociation -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity.name}(IAssociation)")
                    }
                    is IAttribute -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity.name}(IAttribute)")
                    }
                    is IOperation -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity.name}(IOperation)")
                    }
                    is IModel -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity.name}(IModel)")
                    }
                    is IPresentation -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity.label}(IPresentation)")
                    }
                    else -> {
                        println("Op: ${Operation.values()[it.operation]} -> ${entity}(Unknown)")
                    }
                }
        }
        print("========== ")
        print("End Transaction")
        println(" ==========")
    }

    override fun projectClosed(e: ProjectEvent) {
    }

    override fun projectOpened(e: ProjectEvent) {
    }
}