package org.bandev.buddhaquotes.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.adapters.ListRecycler
import org.bandev.buddhaquotes.adapters.QuoteRecycler
import org.bandev.buddhaquotes.fragments.ListsFragment
import org.bandev.buddhaquotes.items.ListItem

class DashboardFragment : Fragment(), QuoteRecycler.OnItemClickFinder {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var scrollingList: ArrayList<ListItem>
    private lateinit var adapter: ListRecycler
    private lateinit var favs: Array<String?>
    private lateinit var array: Array<String>
    private lateinit var recyclerView: RecyclerView

    override fun onLikeClick(position: Int, text: String) {
        TODO("Not yet implemented")
    }

    override fun onCardClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onBinClick(position: Int, text: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)

        refresh()

        return root
    }

    fun refresh() {
        val pref = requireContext().getSharedPreferences("List_system", 0)
        favs = arrayOf(pref.getString("MASTER_LIST", "Favourites"))
        array = (favs[0] ?: return).split("//".toRegex()).toTypedArray()
        array = array.distinct().toTypedArray()

        scrollingList = generateDummyList(array.size)
        adapter = ListRecycler(scrollingList, ListsFragment())

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ListRecycler(scrollingList, ListsFragment())
            setHasFixedSize(false)
        }
    }

    private fun generateDummyList(max: Int): ArrayList<ListItem> {

        val list = ArrayList<ListItem>()

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
            val item = ListItem(array[i], summary, special)
            list += item
            i++
        }
        return list
    }
}