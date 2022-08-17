package com.example.domain

data class ShopItem(
    var id : Int = UNDEFINED_ID,
    val name :String,
    val count :Int,
    val enabled :Boolean
)
{       //создание константы
    companion object{
        const val UNDEFINED_ID = -1
    }

}
