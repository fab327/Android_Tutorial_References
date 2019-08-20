package com.justfabcodes.retrofit_skeleton.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justfabcodes.retrofit_skeleton.R
import com.justfabcodes.retrofit_skeleton.models.Item
import com.justfabcodes.retrofit_skeleton.models.RepoData
import kotlinx.android.synthetic.main.recycler_view_commit_item.view.*
import kotlinx.android.synthetic.main.recycler_view_repo_item.view.*

class RepoAdapter(private val repoList: RepoData) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private val COMMIT_VIEW_TYPE = 0
    private val REPO_VIEW_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (viewType == COMMIT_VIEW_TYPE) R.layout.recycler_view_commit_item else R.layout.recycler_view_repo_item
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            COMMIT_VIEW_TYPE -> holder.bindCommit(repoList.items[position])
            REPO_VIEW_TYPE -> holder.bindRepo(repoList.items[position])
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

    fun updateDataSet(data: RepoData) {
        repoList.items.clear()
        repoList.items.addAll(data.items)
        notifyDataSetChanged()
    }

    /**
     * - We can set any click listener in there
     *
     * - If we didn't have kotlin extensions we would have to add the following for each view property
     * val author: TextView = view.findViewById(R.id.author)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindCommit(item: Item) {
            with(itemView) { // the view object in the constructor can be accessed via the itemView property
                itemCommitHash.text = context.getString(R.string.commit_hash_text, item.commitHash)
                itemAuthorName.text = context.getString(R.string.author_name_text, item.commit?.author?.name)
                itemCommitMessage.text = context.getString(R.string.commit_message_text, item.commit?.commitMessage)
            }
        }

        fun bindRepo(item: Item) {
            with(itemView) { // the view object in the constructor can be accessed via the itemView property
                itemRepoName.text = context.getString(R.string.repo_name_text, item.name)
                itemRepoDescription.text = context.getString(R.string.repo_description_text, item.description)
            }
        }
    }

}