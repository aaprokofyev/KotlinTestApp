package cmg.com.mykotlinapp.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmg.com.mykotlinapp.R
import cmg.com.mykotlinapp.adapter.MyUserRecyclerViewAdapter
import cmg.com.mykotlinapp.datamodel.User
import cmg.com.mykotlinapp.datamodel.UserContent
import kotlinx.android.synthetic.main.fragment_user_list.view.*


class UsersFragment : Fragment() {

    val TAG: String = UsersFragment::class.java.simpleName

    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_user_list, container, false)
        recyclerView = view.list

        val context = view.getContext()
        if (mColumnCount <= 1) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, mColumnCount)
        }
        recyclerView.adapter = MyUserRecyclerViewAdapter(mListener)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView!!.childCount
                val totalItemCount = recyclerView.layoutManager.itemCount
                var firstVisibleItem : Int = 0
                when {
                    recyclerView.layoutManager is LinearLayoutManager ->{
                        firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    }
                    recyclerView.layoutManager is GridLayoutManager -> {
                        firstVisibleItem = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    }
                }



                if (firstVisibleItem >= totalItemCount - visibleItemCount - UserContent.PRELOAD_ITEMS_DELTA) {
                    Log.d(TAG, "Scrolled to the bottom. Requesting data")
                    UserContent.loadMore()
                }
            }
        })

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        if (recyclerView.adapter is MyUserRecyclerViewAdapter) {
            (recyclerView.adapter as MyUserRecyclerViewAdapter).removeListener()
        }

    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: User)
    }

    companion object {

        private val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int): UsersFragment {
            val fragment = UsersFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
