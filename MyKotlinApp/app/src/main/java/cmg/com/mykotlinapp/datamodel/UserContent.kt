package cmg.com.mykotlinapp.datamodel

import android.util.Log
import cmg.com.mykotlinapp.network.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object UserContent {

    val ITEMS: MutableList<User> = ArrayList<User>()
    val ITEM_MAP: MutableMap<String, User> = HashMap<String, User>()

    init {
        loadItems(null)
    }

    private fun addItem(item: User) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun addItems(itemList: List<User>) {
        ITEMS.addAll(itemList)
        var iterator = itemList.iterator();
        while (iterator.hasNext()) {
            var item = iterator.next();
            ITEM_MAP.put(item.id, item)
        }
    }

    fun loadItems(sinceId: String?) {
        API.apiInstance.getUsers(sinceId).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                if (response != null && response.isSuccessful) {
                    addItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                Log.d("CONTENT ERROR", t!!.message)
            }
        });
    }
}
