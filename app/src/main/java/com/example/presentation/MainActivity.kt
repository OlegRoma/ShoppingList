package com.example.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.ShopItem
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel  //объявили ViewModel

    private lateinit var shopListAdapter: ShopListAdapter  //добавляем ссылку на class ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()                      //вызываем метод настройки параметров RecyclerView
      viewModel = ViewModelProvider(this)[MainViewModel::class.java]//инициализация ViewModel
        viewModel.shopList.observe(this){                  //подписка на новый список shopList
           shopListAdapter.submitList(it)     //добавляем в adapter List,метод из класса Listадаптер
           // когда мы запускаем этот метод внутри адаптера запускается новый поток,в котором происходят
        // Все вычисления
        }

    }
// настройка параметров RecyclerView
    private  fun setupRecyclerView(){
    //создаём ссылку RecyclerView
      val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)

      shopListAdapter = ShopListAdapter()      //инициализация переменной экземпляра класса ShopListAdapter()
      rvShopList.adapter = shopListAdapter    //и устанавливаем её в adapter RecyclerView
    ///устанавливаем количество ViewHolder в пуле для каждого VIEW_TYPE,
    rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED,ShopListAdapter.MAX_POOL_SIZE)
    rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED,ShopListAdapter.MAX_POOL_SIZE)
    //вызываем интерфейс и делаем его реализацию
   // старая реализация
   //    shopListAdapter.onShopItemLongClickListener = object : ShopListAdapter.OnShopItemLongClickListener{
   //        override fun onShopItemLongClick(shopItem: ShopItem) {
   //          viewModel.changeEnableState(shopItem)  //реализация
   //        } }
//новая реализация интерфейса через лямбда-выражения,функции описаны ниже
    setupLongClickListener()
    setupClickListener()
    setupSwipeListener(rvShopList)


 }





    //удаление по свайпу
    //создаём экземпляра класса типа  ItemTouchHelper.SimpleCallback
    //Первый параметр это перемещение мы его не используем = 0,
    //второй параметр свайп налево-направо
    //переопределить два метода
    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false            //этим методом Мы не пользуемся поэтому возвращаем false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val item = shopListAdapter.currentList[viewHolder.adapterPosition]//получает элемент по которому
                // свайпнули,метод из класса Listадаптер
                viewModel.deleteShopItem(item)  //удаление
            }

        }
        //создаём объект типа ItemTouchHelper и в качестве параметра передаём  созданной ранее callback
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)//прикрепляем созданный объект к RecyclerView
    }
    //новая реализация  через лямбда-выражения вместо интерфейса
    //короткое нажатие
    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            Log.d("main", it.toString())
        }
    }
    //новая реализация  через лямбда-выражения вместо интерфейса
    //длинное нажатие
    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)  //реализация
        }
    }

}