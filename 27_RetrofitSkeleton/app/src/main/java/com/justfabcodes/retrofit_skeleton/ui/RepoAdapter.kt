package com.justfabcodes.retrofit_skeleton.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justfabcodes.retrofit_skeleton.R
import com.justfabcodes.retrofit_skeleton.models.CommitData
import com.justfabcodes.retrofit_skeleton.models.Item
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class RepoAdapter(private val repoList: CommitData) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(repoList.items[position])
    }

    override fun getItemCount(): Int {
        return repoList.items.size
    }

    fun updateDataSet(data: CommitData) {
        repoList.items = data.items
        notifyDataSetChanged()
    }

    /**
     * - We can set any click listener in there
     *
     * - If we didn't have kotlin extensions we would have to add the following for each view property
     * val author: TextView = view.findViewById(R.id.author)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindViewHolder(item: Item) {
            with(itemView) { // the view object in the constructor can be accessed via the itemView property
                itemCommitHash.text = context.getString(R.string.commit_hash_text, item.commitHash)
                itemAuthorName.text = context.getString(R.string.author_name_text, item.commit?.author?.name)
                itemCommitMessage.text = context.getString(R.string.commit_message_text, item.commit?.commitMessage)
            }
        }
    }

}