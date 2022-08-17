package com.example.domain

//получить весь список покупок
class GetShopListUseCase(private val shopListRepository: ShopListRepository)  {
    fun getShopList():List<ShopItem>{

        return shopListRepository.getShopList()
    }
}