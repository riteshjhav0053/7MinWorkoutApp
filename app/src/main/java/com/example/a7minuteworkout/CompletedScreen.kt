package com.example.a7minuteworkout

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkout.databinding.ActivityCompletedScreenBinding
import com.example.a7minuteworkout.roomdb.HistoryDao
import com.example.a7minuteworkout.roomdb.HistoryEntity
import com.example.a7minuteworkout.roomdb.WorkoutApp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CompletedScreen : AppCompatActivity() {
    private var binding: ActivityCompletedScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCompletedScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        setSupportActionBar(binding?.toolbarFinishActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        addDateToDb(dao)
    }

    private fun addDateToDb(historyDao: HistoryDao){

        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date = date))
            Log.i("DB_INSERT", "Date added successfully: $date")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}


