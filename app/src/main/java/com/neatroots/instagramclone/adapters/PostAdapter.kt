package com.neatroots.instagramclone.adapters

import Models.Post
import Models.User
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.toObject
import com.neatroots.instagramclone.R
import com.neatroots.instagramclone.databinding.PostRvBinding
import com.neatroots.instagramclone.utils.USER_NODE

class PostAdapter(
    private val context: Context,
    private val postList: ArrayList<Post>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    private val likedPositions = HashSet<Int>()  // ✅ Trạng thái like theo vị trí

    inner class ViewHolder(val binding: PostRvBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]

        // Load thông tin người dùng nếu có uid
        val uid = post.uid
        if (!uid.isNullOrEmpty()) {
            Firebase.firestore.collection(USER_NODE)
                .document(uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject<User>()
                    user?.let {
                        Glide.with(context)
                            .load(it.image)
                            .placeholder(R.drawable.user_icon)
                            .into(holder.binding.profileImage)

                        holder.binding.name.text = it.name
                    }
                }
                .addOnFailureListener {
                    // Xử lý khi lỗi Firestore (nếu cần)
                }
        }

        // Load ảnh bài đăng
        Glide.with(context)
            .load(post.postUrl)
            .placeholder(R.drawable.loading)
            .into(holder.binding.postImage)

        // Set thời gian đăng bài
        try {
            val text = TimeAgo.using(post.time.toLong())
            holder.binding.time.text = text
        } catch (e: Exception) {
            holder.binding.time.text = ""
        }

        // Chia sẻ bài viết
        holder.binding.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, post.postUrl)
            context.startActivity(intent)
        }

        // Caption bài viết
        holder.binding.csption.text = post.caption

        // ✅ Xử lý hiển thị trạng thái like
        val isLiked = likedPositions.contains(position)
        holder.binding.like.setImageResource(
            if (isLiked) R.drawable.heart_icon else R.drawable.heart
        )

        // ✅ Toggle trạng thái khi nhấn
        holder.binding.like.setOnClickListener {
            if (likedPositions.contains(position)) {
                likedPositions.remove(position)
                holder.binding.like.setImageResource(R.drawable.heart)
            } else {
                likedPositions.add(position)
                holder.binding.like.setImageResource(R.drawable.heart_icon)
            }
        }

        holder.binding.menuButton.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, holder.binding.menuButton)
            popupMenu.menuInflater.inflate(R.menu.post_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deletePost -> {
                        Toast.makeText(holder.itemView.context, "Xóa bài viết", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.editPost -> {
                        Toast.makeText(context, "Chỉnh sửa", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

    }
}
