package org.example.project.viewModel

sealed class ShoppingListAction {
    data class NameInputChanged(val name: String) : ShoppingListAction()
    data class AddToShoppingList(val listId:String, val name:String, val n:String): ShoppingListAction()
}