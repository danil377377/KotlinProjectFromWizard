package org.example.project.viewModel

sealed class WelkomeScreenAction {
    data class KeyInputChanged(val key: String) : WelkomeScreenAction()
    object ContinueWithSavedKey : WelkomeScreenAction()
    object ContinueWithNewKey : WelkomeScreenAction()
}