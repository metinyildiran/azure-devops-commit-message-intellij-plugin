package com.elicul.azuredevopscommitmessageplugin.provider

import com.elicul.azuredevopscommitmessageplugin.services.CommitMessageService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.LocalChangeList
import com.intellij.openapi.vcs.changes.ui.CommitMessageProvider
import git4idea.GitUtil

class PluginProvider : CommitMessageProvider {

    override fun getCommitMessage(forChangelist: LocalChangeList, project: Project): String? {
        val repositoryManager = GitUtil.getRepositoryManager(project)
        val repository = repositoryManager.repositories.firstOrNull() ?: return null
        val commitMessageService = project.service<CommitMessageService>()

        return commitMessageService.applyBranchNameToCommitMessage(
            repository.currentBranch?.name,
            forChangelist.comment
        )
    }
}
