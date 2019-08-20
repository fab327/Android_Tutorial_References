package com.justfabcodes.retrofit_skeleton.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.justfabcodes.retrofit_skeleton.R
import com.justfabcodes.retrofit_skeleton.models.CommitData
import com.justfabcodes.retrofit_skeleton.network.GetRepositoryCommand
import com.justfabcodes.retrofit_skeleton.util.NetworkUtil
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val getRepoCommand = GetRepositoryCommand()
    private val callback = object : Callback<CommitData> {
        override fun onResponse(call: Call<CommitData>, response: Response<CommitData>) {
            response.isSuccessful.let {
                val resultList = CommitData(response.body()?.items ?: emptyList())
                (repositories.adapter as RepoAdapter).updateDataSet(resultList)
            }
        }

        override fun onFailure(call: Call<CommitData>, t: Throwable) {
            Log.d("TAG", "Something went wrong", t)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        repositories.layoutManager = LinearLayoutManager(this)
        repositories.adapter = RepoAdapter(CommitData(emptyList()))
        repositories.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        getData.setOnClickListener {
            if (NetworkUtil.isNetworkConnected(this@MainActivity)) {
                getRepoCommand.getRepositories(callback)
            }
        }
    }
}