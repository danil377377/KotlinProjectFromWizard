package org.example.project.viewModel

sealed class ShoppingListAction {
    data class NameInputChanged(val name: String) : ShoppingListAction()
    data class QuantityInputChanged(val quantity: String) : ShoppingListAction()
    data class AddToShoppingList(val listId:String, val name:String, val n:String): ShoppingListAction()
    data class RemoveFromShoppingList(val listId:String, val itemId:String): ShoppingListAction()
}