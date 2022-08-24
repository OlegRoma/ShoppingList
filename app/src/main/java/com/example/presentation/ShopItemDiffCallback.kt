package com.example.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.ShopItem
//
//этот класс будет сравнивать отдельные элементы(объекты) типа ShopItem

class ShopItemDiffCallback:DiffUtil.ItemCallback<ShopItem>() {
    //сравниваем элементы по их ID и узнаю это один и тот же объект или разные
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
       return oldItem.id == newItem.id
    }
    //сравниваем элементы по всем полям объекта,Это dataclass и здесь под капотом equals()
    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
       return oldItem == newItem
    }
}