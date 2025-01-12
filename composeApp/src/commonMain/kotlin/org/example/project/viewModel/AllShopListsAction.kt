package org.example.project.viewModel

sealed class AllShopListsAction {
    data class NameInputChanged(val key: String) : AllShopListsAction()
    data class CreateShoplist(val key: String):AllShopListsAction()
}