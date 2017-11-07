package cmg.com.mykotlinapp.datamodel

import android.util.Log
import cmg.com.mykotlinapp.network.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

object UserContent {

    val TAG: String = UserContent::class.java.simpleName

    val ITEMS: MutableList<User> = ArrayList<User>()
    val ITEM_MAP: MutableMap<String, User> = HashMap<String, User>()

    val PRELOAD_ITEMS_DELTA: Int = 10;

    var isLoading: Boolean = false

    var updateListeners: MutableList<DataChangeListener> = ArrayList<DataChangeListener>()

    init {
        loadItems(null)
    }

    fun addUpdateListener(listener: DataChangeListener) {
        updateListeners.add(listener)
    }

    fun removeUpdateListener(listener: DataChangeListener) {
        updateListeners.remove(listener)
    }

    private fun notifyUpdated() {
        for (listener in updateListeners) {
            listener.onDataChanged()
        }
    }

    private fun addItem(item: User) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
        notifyUpdated()
    }

    private fun addItems(itemList: List<User>) {
        ITEMS.addAll(itemList)
        var iterator = itemList.iterator();
        while (iterator.hasNext()) {
            var item = iterator.next();
            ITEM_MAP.put(item.id, item)
        }
        notifyUpdated()
    }

    fun loadItems(sinceId: String?) {
        isLoading = true
        Log.d(TAG, "Requesting since " + sinceId)
        API.apiInstance.getUsers(sinceId).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                isLoading = false
                if (response != null && response.isSuccessful) {
                    addItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                isLoading = false
                Log.d("CONTENT ERROR", t!!.message)
            }
        });
    }


    fun loadMore() {
        if (isLoading) {
            return
        }
        loadItems(ITEMS.get(ITEMS.size - 1).id)
    }

    interface DataChangeListener {
        fun onDataChanged()
    }
}
