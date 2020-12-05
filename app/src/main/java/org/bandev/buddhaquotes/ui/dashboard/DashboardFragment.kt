package org.bandev.buddhaquotes.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.ListMenuAdapter
import org.bandev.buddhaquotes.ListMenuItem
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.ScrollingAdapter

class DashboardFragment : Fragment(), ScrollingAdapter.OnItemClickFinder {
    override fun onLikeClick(position: Int, text: String) {
        TODO("Not yet implemented")
    }

    override fun onShareClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onBinClick(position: Int, text: String) {
        TODO("Not yet implemented")
    }

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var scrollingList: ArrayList<ListMenuItem>
    private lateinit var adapter: ListMenuAdapter
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String>
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = root.findViewById(R.id.recycler_view)


        refresh()

        return root
    }

    fun refresh() {
        val pref = requireContext().getSharedPreferences("List_system", 0)
        favs = arrayOf(pref.getString("MASTER_LIST", "Favourites"))
        array = (favs[0] ?: return).split("//".toRegex()).toTypedArray()
        array = array.distinct().toTypedArray()

        scrollingList = generateDummyList(array.size)
        adapter = ListMenuAdapter(scrollingList, this)



        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ListMenuAdapter(scrollingList, this@DashboardFragment)
            setHasFixedSize(false)
        }

    }

    private fun generateDummyList(max: Int): ArrayList<ListMenuItem> {

        val list = ArrayList<ListMenuItem>()

        var i = 0
        while (i != max) {
            var special = false
            val pref = requireContext().getSharedPreferences("List_system", 0)
            val array2 = pref.getString(array[i], "")!!.split("//")
            val count: Int = array2.size - 1
            if (array[i] == "Favourites") {
                special = true
            }
            var summary = ""
            summary = if (count != 1) {
                "$count items"
            } else {
                "$count item"
            }
            val item = ListMenuItem(array[i], summary, special)
            list += item
            i++
        }
        return list
    }
}