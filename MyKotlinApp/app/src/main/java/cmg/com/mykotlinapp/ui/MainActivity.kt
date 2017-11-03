package cmg.com.mykotlinapp.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import cmg.com.mykotlinapp.R
import cmg.com.mykotlinapp.datamodel.User
import cmg.com.mykotlinapp.datamodel.UserContent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UsersFragment.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(item: User) {
        //do nothing
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_users -> {
                val userListFragment = UsersFragment.newInstance(1)
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, userListFragment, UsersFragment::TAG.toString())
                        .commit();
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
