package pl.handsome.club.repobrowser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_commits_history.view.*
import pl.handsome.club.repobrowser.R
import pl.handsome.club.repobrowser.domain.details.CommitDetails


class CommitsHistoryRecyclerListAdapter :
    ListAdapter<CommitDetails, CommitsHistoryRecyclerListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_commits_history, parent, false)
            .let(::ViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).also { holder.bind(it, position) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val commitNumberText = itemView.commitNumberText
        private val repoAuthorNameText = itemView.repoAuthorNameText
        private val authorEmailText = itemView.authorEmailText
        private val commitMessageText = itemView.commitMessageText


        fun bind(commitDetails: CommitDetails, position: Int) = with(commitDetails) {
            commitNumberText.text = (position + 1).toString()
            repoAuthorNameText.text = commitDetails.authorName
            authorEmailText.text = commitDetails.authorEmail
            commitMessageText.text = commitDetails.message
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<CommitDetails>() {

        override fun areItemsTheSame(
            oldItem: CommitDetails,
            newItem: CommitDetails
        ): Boolean {
            return oldItem.htmlUrl == newItem.htmlUrl
        }

        override fun areContentsTheSame(
            oldItem: CommitDetails,
            newItem: CommitDetails
        ): Boolean {
            return oldItem == newItem
        }

    }

}
