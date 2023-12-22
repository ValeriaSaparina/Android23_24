package com.example.homework4.ui

import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.homework4.FlightModeReceiver
import com.example.homework4.PermissionRequestHandler
import com.example.homework4.R
import com.example.homework4.ui.fragments.CoroutineSettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var coroutineSettingFragment = CoroutineSettingsFragment.getInstance()
    private var permissionRequestHandler: PermissionRequestHandler? = null
    private var flightModeReceiver: FlightModeReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controller =
            (supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment)
                .navController
        findViewById<BottomNavigationView>(R.id.bnv_main).apply {
            setupWithNavController(controller)
        }

        handleIntent(intent)

        flightModeReceiver = FlightModeReceiver(
            alertView = findViewById(R.id.warning_view),
        )
        registerFlightModeReceiver()

        permissionRequestHandler = PermissionRequestHandler(
            activity = this,
            callback = {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_LONG).show()
            },
            deniedCallback = {
                AlertDialog.Builder(this)
                    .setMessage(R.string.enable_notifications_in_settings)
                    .setPositiveButton(R.string.open_setting) { dialogInterface: DialogInterface, i: Int ->
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra(Settings.EXTRA_APP_PACKAGE, applicationContext.packageName)
                        }
                        startActivity(intent)
                    }
                    .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.cancel()
                    }
                    .show()
            }
        )
    }

    private fun registerFlightModeReceiver() {
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(flightModeReceiver, filter)
    }


    fun requestPermission(permission: String) {
        permissionRequestHandler?.requestPermission(permission)
    }


    override fun onStop() {
        coroutineSettingFragment.stopCoroutines()
        super.onStop()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.getStringExtra("action")) {
            "showToast" -> {
                showToast("Action: Show Toast")
            }

            "openSettings" -> {
                switchToFragment(R.id.notificationSettingsFragment2)
            }

            "" -> {
                switchToFragment(R.id.mainFragment2)
            }
        }
    }

    private fun switchToFragment(id: Int) {

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bnv_main)
        bottomNavigationView.selectedItemId = id

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}