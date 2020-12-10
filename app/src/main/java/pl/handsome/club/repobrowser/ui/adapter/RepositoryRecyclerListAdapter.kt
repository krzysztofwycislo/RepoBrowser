package pl.handsome.club.repobrowser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_repository.view.*
import pl.handsome.club.repobrowser.R
import pl.handsome.club.repobrowser.domain.search.SearchRepository

class RepositoryRecyclerListAdapter(
    private val onItemClick: (SearchRepository) -> Unit
) : ListAdapter<SearchRepository, RepositoryRecyclerListAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_repository, parent, false)
            .let(::ViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let(holder::bind)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val listItemContainer = itemView.listItemContainer
        private val authorAvatarImage = itemView.authorAvatarImage
        private val repoTitleTextView = itemView.repoTitleTextView
        private val starsCountTextView = itemView.starsCountTextView


        fun bind(searchRepository: SearchRepository) = with(searchRepository) {
            loadImage(ownerAvatarUrl)
            repoTitleTextView.text = title
            starsCountTextView.text = starsCount.toString()

            listItemContainer.setOnClickListener { onItemClick(searchRepository) }
        }

        private fun loadImage(ownerAvatarUrl: String) {
            Glide.with(itemView)
                .load(ownerAvatarUrl)
                .into(authorAvatarImage)
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<SearchRepository>() {

        override fun areItemsTheSame(
            oldItem: SearchRepository,
            newItem: SearchRepository
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.ownerId == newItem.ownerId
        }

        override fun areContentsTheSame(
            oldItem: SearchRepository,
            newItem: SearchRepository
        ): Boolean {
            return oldItem == newItem
        }

    }
}
