package com.example.data

import com.example.domain.ShopItem
import com.example.domain.ShopListRepository
import java.lang.RuntimeException

// class изменили на object для SingleTon
object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()//переменная для хранения коллекции элементов
    private var autoIncrement = 0                   //переменная для ID

    //добавить элемент в коллекцию
    override fun addShopItem(shopItem: ShopItem) {//при добавлении нового элемента
        if (shopItem.id == ShopItem.UNDEFINED_ID){// у элемента id=UNDEFINED_ID,то есть нет ID,
            shopItem.id = autoIncrement           //тогда присваиваем id номер
            autoIncrement++
        }
                                              //При редактировании элемента у него есть ID,
        shopList.add(shopItem)                 //просто добавляем элемент
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }
//редактирование элементов коллекции
    override fun editShopItem(shopItem: ShopItem) {
       val oldElement = getShopItem(shopItem.id)  //находим старый элемент по его ID
        shopList.remove(oldElement)             //удалить старый элемент из коллекции
        addShopItem(shopItem)                  //вставить новый элемент в коллекцию
    }


    //возвращает элемент по ID
   //Если элемент с таким shopItemId не будет найден,то есть shopItemId=null
    //приложение упадёт, с данным исключением ("Element with id $shopItemId not found")
    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId }?:throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
       return shopList.toList()                 //создали копию shopList
    }

}