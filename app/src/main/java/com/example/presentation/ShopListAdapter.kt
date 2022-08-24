package com.example.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.ShopItem
import com.example.shoppinglist.R
//вместо RecyclerView подключаем ListAdapter.он быстрее и удобней
//ListAdapter из пакета RecyclerView,в его конструктор передаём созданный класс ShopItemDiffCallback()
// ListAdapter скрывает всю логику работы со списком
class ShopListAdapter:androidx.recyclerview.widget.ListAdapter<ShopItem,ShopItemViewHolder>(ShopItemDiffCallback()) {
    //старая реализация через интерфейс
    //создаём переменную типа интерфейс для длинного нажатия
   // var onShopItemLongClickListener:OnShopItemLongClickListener? = null

    //новая реализация вместо интерфейса через лямбда-выражения
    //длинное нажатие
   var onShopItemLongClickListener:((ShopItem)->Unit)? = null//переменная содержит функцию
  //короткое нажатие
    var onShopItemClickListener:((ShopItem)->Unit)? = null//переменная содержит функцию
                                                     //   которая принимает ShopItem и ничего не возвращает

    //метод определяет тип ViewType,который используется в методе onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)                   //получили элемент,метод из класса адаптер

        return if (item.enabled){
            VIEW_TYPE_ENABLED
        }else{
            VIEW_TYPE_DISABLED
        }
    }



//метод создает View видимых на экране смартфона и возвращает ShopItemViewHolder.То есть View и его атрибуты
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
    val  layout = when(viewType){  //получаем  layout в зависимости от типа viewType
        VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
        VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
        else ->throw RuntimeException("неустановленный viewType: $viewType")
    }
//устанавливаем layout во view
    val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
     return ShopItemViewHolder(view)
    }

//присваивает атрибутам нужное значение
    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)   //получаем элемент по его позиции,метод из класса адаптер
    holder.tvName.text = shopItem.name                  //устанавливаем в атрибуты нужное значение
    holder.tvCount.text = shopItem.count.toString()

    holder.view.setOnLongClickListener{                 //устанавливаем слушатель клика
        //старая реализация интерфейса
        //onShopItemLongClickListener?.onShopItemLongClick(shopItem)//вызываем метод interface
        //новая реализация  через лямбда-выражения
        onShopItemLongClickListener?.invoke(shopItem)//cработает Если переменная не равна null
        true
    }

    holder.view.setOnClickListener {
        onShopItemClickListener?.invoke(shopItem)
    }

    }
    //при работе с  ListAdapter этот метод можно не переопределить
////возвращает количество элементов коллекции
//    override fun getItemCount(): Int {
//       return shoplist.size
//    }

//    //создаём интерфейс для длинного нажатия на View, реализация будет в Activity
//    interface OnShopItemLongClickListener{
//        fun onShopItemLongClick(shopItem: ShopItem)
//    }

    companion object{
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_POOL_SIZE = 5   //количество viewholder в пуле это дополнительные viewholder
    // скрытые от пользователя которые создаются для скролла страницы
      // ,как дополнение к тем которые видят пользователь,
      //параметр влияет на производительность,и позволяет не вызывать метод onCreateViewHolder
     // при скролле страницы
    }
}