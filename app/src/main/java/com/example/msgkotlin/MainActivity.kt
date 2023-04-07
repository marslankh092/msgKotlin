package com.example.msgkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.msgkotlin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    var dbref: DatabaseReference? = null
    var userAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //adapter

        //adapter
        userAdapter = UserAdapter(this)
        binding!!.recyclr.adapter = userAdapter
        binding!!.recyclr.layoutManager = LinearLayoutManager(this)


        dbref = FirebaseDatabase.getInstance().getReference("Arslan").child("users")

        dbref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userAdapter!!.clear()
                for (dbsnapshot in snapshot.children) {
                    val uid = dbsnapshot.key
                    if (uid != FirebaseAuth.getInstance().uid) {
                        val userModel = dbsnapshot.getValue(UserModel::class.java)
                        userAdapter!!.add(userModel!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, AuthentcationActivity::class.java))
            finish()
            return true
        }
        return false
    }
}