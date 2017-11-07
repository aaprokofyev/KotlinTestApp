package cmg.com.mykotlinapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cmg.com.mykotlinapp.R

import cmg.com.mykotlinapp.ui.UsersFragment.OnListFragmentInteractionListener
import cmg.com.mykotlinapp.datamodel.User
import cmg.com.mykotlinapp.datamodel.UserContent


class MyUserRecyclerViewAdapter(private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder>(), UserContent.DataChangeListener {
    override fun onDataChanged() {
        notifyDataSetChanged()
    }

    init {
        UserContent.addUpdateListener(this)
    }

    fun removeListener(){
        UserContent.removeUpdateListener(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = UserContent.ITEMS.get(position)
        holder.mIdView.setText(UserContent.ITEMS.get(position).id)
        holder.mContentView.setText(UserContent.ITEMS.get(position).login)

        holder.mView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem!!)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return UserContent.ITEMS.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        var mItem: User? = null

        init {
            mIdView = mView.findViewById<View>(R.id.id) as TextView
            mContentView = mView.findViewById<View>(R.id.content) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.getText() + "'"
        }
    }
}
