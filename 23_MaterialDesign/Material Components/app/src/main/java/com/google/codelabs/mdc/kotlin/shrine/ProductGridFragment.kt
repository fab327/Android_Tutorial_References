package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter
import kotlinx.android.synthetic.main.product_grid_fragment.*
import kotlinx.android.synthetic.main.product_grid_fragment.view.*

class ProductGridFragment : Fragment() {

    var layoutSwitchCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set a different theme
        // activity?.setTheme(R.style.Theme_Shrine_Autumn)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.product_grid_fragment, container, false)

        // Set up the toolbar
        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)

        // Set up the recyclerView
        view.recycler_view.setHasFixedSize(true)
        setUpRecyclerLayout(view)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.layout_switch -> {
                setUpRecyclerLayout(this.view)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerLayout(view: View?) {
        if (layoutSwitchCount % 2 == 0) {
            view?.recycler_view?.adapter = ProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))
            view?.recycler_view?.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        } else {
            recycler_view?.adapter = StaggeredProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))
            recycler_view?.layoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position % 3 == 2) 2 else 1
                    }
                }
            }
        }

        if (layoutSwitchCount == 0) {
            // Handling of the between element spacing outside of the xml paddings and margins
            val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
            val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
            view?.recycler_view?.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
        }

        layoutSwitchCount++
    }

}
