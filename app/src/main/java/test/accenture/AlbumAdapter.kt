package test.accenture

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author Satyam
 */

class AlbumAdapter(private val list: List<MainActivity.Albums>)
    : RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val movie: MainActivity.Albums = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}
class AlbumViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_album, parent, false)) {
    private var mTitleView: TextView? = null
    private var mId: TextView? = null
    private var tvUserId: TextView? = null



    init {
        mId = itemView.findViewById(R.id.tv_id)
        mTitleView = itemView.findViewById(R.id.tv_title)
        tvUserId= itemView.findViewById(R.id.tv_user_id)
    }

    fun bind(album: MainActivity.Albums) {
        mTitleView?.text = "Title : "+album.title
        mId?.text = "Id : "+album.id.toString()
        tvUserId?.text="User Id : "+album.userId.toString()
    }

}