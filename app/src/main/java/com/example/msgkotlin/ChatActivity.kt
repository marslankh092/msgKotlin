package com.example.msgkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.msgkotlin.databinding.ActivityChatBinding
import com.example.msgkotlin.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference

class ChatActivity : AppCompatActivity() {

    var cbinding: ActivityChatBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cbinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(cbinding!!.root)
    }
}