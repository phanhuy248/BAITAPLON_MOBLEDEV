package com.neatroots.instagramclone.Post

import Models.Reel
import Models.User
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.neatroots.instagramclone.HomeActivity
import com.neatroots.instagramclone.databinding.ActivityReelBinding
import com.neatroots.instagramclone.utils.REEL
import com.neatroots.instagramclone.utils.REEL_FOLDER
import com.neatroots.instagramclone.utils.USER_NODE
import com.neatroots.instagramclone.utils.uploadVideo

class ReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
    lateinit var progressDialog: ProgressDialog
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) {
                    url->
                if(url!=null){
                    videoUrl=url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog= ProgressDialog(this)

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user:User= it.toObject<User>()!!
                val reel: Reel = Reel(videoUrl!!, binding.caption.editText?.text.toString(),user.image!!)
                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel).addOnSuccessListener {
                        startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }

        }

    }
}