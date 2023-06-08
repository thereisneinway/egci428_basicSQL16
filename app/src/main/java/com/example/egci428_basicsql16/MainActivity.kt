package com.example.egci428_basicsql16

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var dataSource: CommentDataSource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        val addBtn = findViewById<Button>(R.id.addBtn)
        val commentText = findViewById<EditText>(R.id.commentTextView)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)
        val idInputText = findViewById<EditText>(R.id.idInputText)

        var comment: Comment

        dataSource = CommentDataSource(this)
        dataSource!!.open()

        val values = dataSource!!.allComments  //read all records from comments table

        val adapter = ArrayAdapter<Comment>(this, android.R.layout.simple_expandable_list_item_1, values)
        listView.adapter = adapter

        addBtn.setOnClickListener {
            comment = dataSource!!.createComment(commentText.text.toString())
            adapter.add(comment)
            adapter.notifyDataSetChanged()
        }

        deleteBtn.setOnClickListener{
            if(adapter.count>0){
                var listid = idInputText.text.toString().toInt()
                if(listid <= adapter.count) {
                    comment = adapter.getItem(listid) as Comment
                    dataSource!!.deleteComment(comment)
                    adapter.remove(comment)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(baseContext, comment.message+" deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        listView.setOnItemClickListener { adapterView, view, position, id ->
            comment = adapter.getItem(position) as Comment
            adapter.remove(comment)
            adapter.notifyDataSetChanged()
            Toast.makeText(baseContext, comment.message+ " deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        dataSource!!.open()
    }

    override fun onPause() {
        super.onPause()
        dataSource!!.close()
    }
}