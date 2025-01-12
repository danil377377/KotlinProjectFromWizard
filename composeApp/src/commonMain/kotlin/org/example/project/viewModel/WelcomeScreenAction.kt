package org.example.project.viewModel

sealed class WelcomeScreenAction {
    data class KeyInputChanged(val key: String) : WelcomeScreenAction()
    object ContinueWithSavedKey : WelcomeScreenAction()
    object ContinueWithNewKey : WelcomeScreenAction()
}