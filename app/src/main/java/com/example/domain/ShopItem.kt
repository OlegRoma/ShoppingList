package com.example.domain

data class ShopItem(

    val name :String,
    val count :Int,
    val enabled :Boolean,
    var id : Int = UNDEFINED_ID
)
{       //создание константы
    companion object{
        const val UNDEFINED_ID = -1
    }

}
