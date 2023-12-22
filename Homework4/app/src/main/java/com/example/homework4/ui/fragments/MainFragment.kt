package com.example.homework4.ui.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.homework4.R
import com.example.homework4.Settings
import com.example.homework4.databinding.FragmentMainBinding
import com.example.homework4.ui.MainActivity


class MainFragment : Fragment(R.layout.fragment_main) {

    private var viewBinding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? MainActivity)?.requestPermission(permission = Manifest.permission.POST_NOTIFICATIONS)

        createNotificationChannels()

        viewBinding?.apply {
            sendNotificationBtn.setOnClickListener {
                createNotification()
            }
        }

    }

    private fun createIntent(action: String = ""): PendingIntent {

        val resultIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("action", action)
        }

        return PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_MUTABLE)
    }

    private fun createNotification() {

        val notificationManager: NotificationManager =
            activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val titleNotification = viewBinding?.titleEt?.text.toString()
        val messageNotification = viewBinding?.messageEt?.text.toString()
        val channelId = when (Settings.getImportance()) {
            NotificationManager.IMPORTANCE_LOW -> Settings.IMPORTANCE_LOW_CHANNEL
            NotificationManager.IMPORTANCE_HIGH -> Settings.IMPORTANCE_HIGH_CHANNEL
            else -> Settings.IMPORTANCE_DEFAULT_CHANNEL

        }

        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(titleNotification)
            .setVisibility(Settings.getVisibility())
            .setContentIntent(createIntent())

        if (Settings.getIsShowFullMessage()) {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(messageNotification))
        } else {
            builder.setContentText(messageNotification)
        }



        if (Settings.getIsShowTwoButton()) {

            val toastIntent = Intent(context, MainActivity::class.java).apply {
                putExtra("action", "showToast")
            }
            val toastPendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 1, toastIntent, PendingIntent.FLAG_IMMUTABLE)


            val settingsIntent = Intent(context, MainActivity::class.java).apply {
                putExtra("action", "openSettings")
            }
            val settingsPendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 2, settingsIntent, PendingIntent.FLAG_IMMUTABLE)


            builder.addAction(
                R.drawable.baseline_notifications_24, getString(R.string.toast),
                toastPendingIntent
            )
                .addAction(
                    R.drawable.baseline_notifications_24,
                    getString(R.string.notifications_settings),
                    settingsPendingIntent
                )
        }

        notificationManager.notify(1, builder.build())
    }

    private fun createNotificationChannel(id: String, importance: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val channel = NotificationChannel(id, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun createNotificationChannels() {
        createNotificationChannel(
            Settings.IMPORTANCE_DEFAULT_CHANNEL,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        createNotificationChannel(
            Settings.IMPORTANCE_HIGH_CHANNEL,
            NotificationManager.IMPORTANCE_HIGH
        )
        createNotificationChannel(
            Settings.IMPORTANCE_LOW_CHANNEL,
            NotificationManager.IMPORTANCE_LOW
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}