package com.hcdisat.week_three

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hcdisat.week_three.data.database.AppRepository
import com.hcdisat.week_three.data.database.MusicTrackDatabase
import com.hcdisat.week_three.databinding.ActivityMainBinding
import com.hcdisat.week_three.models.MusicTrack
import com.hcdisat.week_three.monitors.NetworkMonitor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val compositeDisposable = CompositeDisposable()

    private val networkMonitor by lazy {
        NetworkMonitor(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
//        val track = MusicTrack(
//            trackId = 2,
//            artistName = "Korn",
//            collectionName = "Issues",
//            artworkUrl = "http://",
//            trackNumber = 3
//        )
//
//        val db = MusicTrackDatabase.getInstance(applicationContext)
//
//        db.musicTracksDAO().insertMany(listOf(track))
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                Log.d("TAG", "onStart: Now test reading")
//            }, {
//                Log.e("TAG", "onStart: ${it.stackTrace}", )
//            }).let {
//                compositeDisposable.add(it)
//            }

//        val db = MusicTrackDatabase.getInstance(applicationContext)
//        db.musicTracksDAO().getAll()
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                Log.d("TAG", it.toString())
//            }, {}).apply {
//                compositeDisposable.add(this)
//            }

//        networkMonitor.registerForNetworkUpdates().subscribe(
//            {
//                when(it) {
//                    true -> Log.d("TAG", "Connected: ")
//                    false -> Log.d("TAG", "Disconnected: ")
//                }
//            },
//            {})?.let {
//            compositeDisposable.add(it)
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}