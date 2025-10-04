package com.example.a7minuteworkout

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityBmiBinding
import com.example.a7minuteworkout.databinding.ActivityHistoryBinding
import com.example.a7minuteworkout.roomdb.HistoryAdapter
import com.example.a7minuteworkout.roomdb.HistoryDao
import com.example.a7minuteworkout.roomdb.WorkoutApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarHistoryActivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)


    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { allCompletedDates ->
                if (allCompletedDates.isNotEmpty()) {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.GONE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for (date in allCompletedDates) {
                        dates.add(date.date)
                    }

                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }
            }
        }
    }


//    private fun getAllCompletedDates(historyDao: HistoryDao){
//        lifecycleScope.launch {
//            historyDao.fetchAllDates().collect{allCompletedDates ->{
//                if (allCompletedDates.isNotEmpty()){
//                    binding?.tvHistory?.visibility = View.VISIBLE
//                    binding?.rvHistory?.visibility = View.VISIBLE
//                    binding?.tvNoDataAvailable?.visibility = View.GONE
//                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
//                    val dates = ArrayList<String>()
//                    for (date in allCompletedDates){
//                        dates.add(date.date)
//                    }
//                    val historyAdapter = HistoryAdapter(dates)
//                    binding?.rvHistory?.adapter = historyAdapter
//
//                }else{
//                    binding?.tvHistory?.visibility = View.GONE
//                    binding?.rvHistory?.visibility = View.GONE
//                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
//                }
//            }
//        }

//    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null

    }
}