package com.example.domain

//показать элемент по его ID
class GetShopItemUseCase (private val shopListRepository: ShopListRepository){
    fun getShopItem(shopItemId:Int):ShopItem{

        return shopListRepository.getShopItem(shopItemId)

    }
}