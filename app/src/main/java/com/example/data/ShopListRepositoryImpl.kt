package com.example.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.ShopItem
import com.example.domain.ShopListRepository
import java.lang.RuntimeException

// class изменили на object для SingleTon
object ShopListRepositoryImpl: ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>() //создать объект LiveData
    private val shopList = sortedSetOf<ShopItem>({ o1,o2 -> o1.id.compareTo(o2.id)})//переменная для хранения коллекции элементов
    //mutableListOf<ShopItem>() заменили на sortedSetOf<ShopItem> он создаёт сортированный список б
    // в качестве параметра передали компаратор, Каким образом сортировать список{ o1,o2 -> o1.id.compareTo(o2.id)}
    //по значениям id
    private var autoIncrement = 0                   //переменная для ID

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name $i",i,true)
            addShopItem(item)
        }
    }

    //добавить элемент в коллекцию
    override fun addShopItem(shopItem: ShopItem) {//при добавлении нового элемента
        if (shopItem.id == ShopItem.UNDEFINED_ID){// у элемента id=UNDEFINED_ID,то есть нет ID,
            shopItem.id = autoIncrement           //тогда присваиваем id номер
            autoIncrement++
        }                                //При редактировании элемента, ID должен сохраниться

        shopList.add(shopItem)                 //просто добавляем элемент
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
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

    override fun getShopList(): LiveData<List<ShopItem>> {

       return shopListLD                    //возвращает LiveData
    }

    private fun updateList(){                        //устанавливаем новое значение LiveData
        shopListLD.value = shopList.toList()         //создали копию shopList
    }

}