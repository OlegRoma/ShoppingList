package com.example.domain

//редактировать элемент
class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem){
       shopListRepository.editShopItem(shopItem)
    }
}