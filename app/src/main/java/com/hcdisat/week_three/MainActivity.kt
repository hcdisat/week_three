package com.hcdisat.week_three

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayout
import com.hcdisat.week_three.databinding.ActivityMainBinding
import com.hcdisat.week_three.utils.LOG_TAG
import com.hcdisat.week_three.utils.Tabs

private const val SELECTED_TAB = "SELECTED_TAB"

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

//    private val compositeDisposable = CompositeDisposable()
//
//    private val networkMonitor by lazy {
//        NetworkMonitor(applicationContext)
//    }

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_content_main)
    }

    /**
     * navigates to the provided tab position
     */
    private fun navigateToTab(tabPosition: Int) {
        val selectedTab = when(tabPosition) {
            Tabs.ROCK -> R.id.rockFragment
            Tabs.POP -> R.id.popFragment
            Tabs.CLASSIC -> R.id.classicFragment
            else -> 0
        }
        binding.tabs.selectTab(binding.tabs.getTabAt(tabPosition))
        navController.navigate(selectedTab)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                navigateToTab(binding.tabs.selectedTabPosition)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        savedInstanceState?.let {
            val tabPosition = it.getInt(SELECTED_TAB)
            if (tabPosition in 0..3 )
                navigateToTab(tabPosition)
        }
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_TAB, binding.tabs.selectedTabPosition)
    }
}