package com.example.domain

import androidx.lifecycle.LiveData

//получить весь список покупок
class GetShopListUseCase(private val shopListRepository: ShopListRepository)  {
    fun getShopList():LiveData<List<ShopItem>>{
       return shopListRepository.getShopList()
    }
}