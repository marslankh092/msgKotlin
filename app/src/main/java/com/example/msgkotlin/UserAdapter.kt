package com.example.msgkotlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(private val context: Context) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    private val userModelList: MutableList<UserModel>?

    init {
        userModelList = ArrayList()
    }

    fun add(userModel: UserModel) {
        userModelList!!.add(userModel)
        notifyDataSetChanged()
    }

    fun clear() {
        userModelList!!.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val rowView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return MyViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //   holder.tvName.text = list[position].login
        val userModel = userModelList!![position]
        holder.email.text = userModel.userEmail
        holder.name.text = userModel.userName
        holder.email.text = userModel.userPassword

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("id", userModel.userId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userModelList?.size ?: 0
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView
        val email: TextView

        init {
            name = itemView.findViewById<TextView>(R.id.username)
            email = itemView.findViewById<TextView>(R.id.userEmail)
        }
    }
}
