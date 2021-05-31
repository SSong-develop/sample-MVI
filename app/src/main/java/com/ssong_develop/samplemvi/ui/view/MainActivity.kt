package com.ssong_develop.samplemvi.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssong_develop.samplemvi.R
import com.ssong_develop.samplemvi.data.api.ApiHelperImpl
import com.ssong_develop.samplemvi.data.api.RetrofitBuilder
import com.ssong_develop.samplemvi.data.entity.User
import com.ssong_develop.samplemvi.databinding.ActivityMainBinding
import com.ssong_develop.samplemvi.ui.adapter.MainAdapter
import com.ssong_develop.samplemvi.ui.factory.MainViewModelFactory
import com.ssong_develop.samplemvi.ui.intent.MainIntent
import com.ssong_develop.samplemvi.ui.viewmodel.MainViewModel
import com.ssong_develop.samplemvi.ui.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * MVI
 * Model : represents the state of the UI , UI has many state like , DataLoading , Loaded , Change in UI with user Actions,Errors,User current screen position
 * Each state is stored as similar to the object in the model
 *
 * View : our Interfaces which can be implemented in Activities and fragments
 * it means to have a container which can accept the different model states and display it as a UI
 * they use observable intents to respond to user actions
 *
 * Intent : the result of the user actions is passed as an input value to Intents, In turn, we can say we will be sending models as inputs
 * to the Intents which can load it through Views
 */
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            ApiHelperImpl(
                RetrofitBuilder.apiService
            )
        )
    }

    private var mainAdatper = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupUI(binding)
        observeViewModel(binding)
        setupClicks(binding)
    }

    private fun setupUI(binding: ActivityMainBinding) {
        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = mainAdatper
        }
    }

    private fun setupClicks(binding: ActivityMainBinding) {
        binding.mainFetchUserBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun observeViewModel(binding: ActivityMainBinding) {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        binding.mainFetchUserBtn.visibility = View.GONE
                        binding.mainProgressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        binding.mainProgressBar.visibility = View.GONE
                        binding.mainFetchUserBtn.visibility = View.GONE
                        renderList(binding, it.user)
                    }
                    is MainState.Error -> {
                        binding.mainProgressBar.visibility = View.GONE
                        binding.mainFetchUserBtn.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity,it.error,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun renderList(binding: ActivityMainBinding, user: List<User>) {
        binding.mainRecyclerView.visibility = View.VISIBLE
        user.let { listOfUsers -> listOfUsers.let { mainAdatper.addData(it) } }
        mainAdatper.notifyDataSetChanged()
    }


}