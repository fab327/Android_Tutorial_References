package com.justfabcodes.retrofit_skeleton.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.justfabcodes.retrofit_skeleton.R
import com.justfabcodes.retrofit_skeleton.models.Item
import com.justfabcodes.retrofit_skeleton.models.RepoData
import kotlinx.android.synthetic.main.recycler_view_commit_item.view.*
import kotlinx.android.synthetic.main.recycler_view_repo_item.view.*

class RepoAdapter(private val repoList: RepoData) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    interface RepoAdapterListener {
        fun onItemClick(message: String)
    }

    private val COMMIT_VIEW_TYPE = 0
    private val REPO_VIEW_TYPE = 1
    private var listener: RepoAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (viewType == COMMIT_VIEW_TYPE) R.layout.recycler_view_commit_item else R.layout.recycler_view_repo_item
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            COMMIT_VIEW_TYPE -> holder.bindCommit(repoList.items[position], listener)
            REPO_VIEW_TYPE -> holder.bindRepo(repoList.items[position], listener)
        }
    }

    override fun getItemCount(): Int {
        return repoList.items.size
    }

    /**
     * This method is called before [onCreateViewHolder] and the logic inside determines which layout to inflate
     */
    override fun getItemViewType(position: Int): Int {
        if (!repoList.items[position].name.isNullOrEmpty()) {
            return REPO_VIEW_TYPE
        } else {
            return COMMIT_VIEW_TYPE
        }
    }

    fun setListener(listener: RepoAdapterListener) {
        this.listener = listener
    }

    fun updateDataSet(data: RepoData) {
        repoList.items.clear()
        repoList.items.addAll(data.items)
        notifyDataSetChanged()
    }

    fun enableDragFunctionality(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), /* Enable drag in both directions */
            ItemTouchHelper.LEFT /* Only enable swipe from right to left */
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //Reorganize item position
                val item = repoList.items.removeAt(viewHolder.adapterPosition)
                repoList.items.add(target.adapterPosition, item)

                notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Remove item from list
                repoList.items.removeAt(viewHolder.adapterPosition)
                notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)
    }

    /**
     * - We can set any click listener in there
     *
     * - If we didn't have kotlin extensions we would have to add the following for each view property
     * val author: TextView = view.findViewById(R.id.author)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindCommit(item: Item, listener: RepoAdapterListener?) {
            with(itemView) {
                // the view object in the constructor can be accessed via the itemView property
                /*
                 * New API to optimize fetching of the text (not needed here but serving as reference)
                 * isItemPrefetchEnabled must be enabled on the recyclerView#LayoutManager (true by default)
                 * More info -> https://medium.com/androiddevelopers/prefetch-text-layout-in-recyclerview-4acf9103f438
                 */
                itemCommitHash.setTextFuture(PrecomputedTextCompat.getTextFuture(
                    context.getString(R.string.commit_hash_text, item.commitHash),
                    TextViewCompat.getTextMetricsParams(itemCommitHash),
                    null //Executor
                ))
                itemAuthorName.text = context.getString(R.string.author_name_text, item.commit?.author?.name)
                itemCommitMessage.text = context.getString(R.string.commit_message_text, item.commit?.commitMessage)
            }
            registerListener(item, listener)
        }

        fun bindRepo(item: Item, listener: RepoAdapterListener?) {
            with(itemView) {
                // the view object in the constructor can be accessed via the itemView property
                itemRepoName.text = context.getString(R.string.repo_name_text, item.name)
                itemRepoDescription.text = context.getString(R.string.repo_description_text, item.description)
            }
            registerListener(item, listener)
        }

        private fun registerListener(item: Item, listener: RepoAdapterListener?) {
            listener?.onItemClick(item.description ?: "")
        }
    }

}