package com.example.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.ShopListRepositoryImpl
import com.example.domain.DeleteShopItemUseCase
import com.example.domain.EditShopItemUseCase
import com.example.domain.GetShopListUseCase
import com.example.domain.ShopItem

//ViewModel реализует всю бизнес логику и реагирует на события в Activity


class MainViewModel : ViewModel(){

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)     //получить список
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)//удалить элемент из списка
    private val editShopItemUseCase = EditShopItemUseCase(repository)    //редактировать элемент

    val shopList = getShopListUseCase.getShopList()    //получаем объект LiveData,
                                                      // взаимодействие ViewModel и Activity
                                                     //происходит через объект LiveData
                            //все кто подписан на LiveData при её изменении получают объекты(данные)


   fun deleteShopItem(shopItem: ShopItem){
       deleteShopItemUseCase.deleteShopItem(shopItem)        //LiveData обновиться

   }

    fun changeEnableState(shopItem: ShopItem){                   //создаём копию объекта
        val newItem = shopItem.copy(enabled = !shopItem.enabled)//поле  enabled меняем на противоположное
        editShopItemUseCase.editShopItem(newItem)               //LiveData обновиться

    }
}