package com.devproject.miguelfagundez.basicnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IInterface
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

/********************************************
 * Activity - MainActivity
 * Show a notification message
 * @author: Miguel Fagundez
 * @date: March 16th, 2020
 * @version: 1.0
 * *******************************************/
class MainActivity : AppCompatActivity() {

    // Notification manager which delivery notification to the user
    private var manager : NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotification()
        setupListeners()
    }

    private fun createNotification() {
        // Instantiate object manager
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Device version is higher or equal to 26
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            // Create the notification Channel
            var notificationChannel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID,
                                      Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            // Defining some notifications properties
            notificationChannel.enableLights(false)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationChannel.description = Constants.NOTIFICATION_CHANNEL_DESCRIPTION
            manager?.createNotificationChannel(notificationChannel)
        }
    }

    //********************************************
    // Open the MainActivity (or any other) throughout
    // an Intent and PendingIntent
    //********************************************
    private fun createNotificationIntent() : PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val notificationIntent = PendingIntent.getActivity(this, Constants.NOTIFICATION_ID, intent,PendingIntent.FLAG_ONE_SHOT)
        return notificationIntent
    }

    private fun getBuilderCompat():NotificationCompat.Builder{
        val pendingIntent = createNotificationIntent()
        val builderCompat = NotificationCompat.Builder(this@MainActivity, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(Constants.NOTIFICATION_0_TITLE)
            .setContentText(Constants.NOTIFICATION_0_TEXT)
            .setSmallIcon(R.drawable.ic_download_cloud)
            // set the future action when user tap the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return builderCompat
    }

    private fun setupListeners() {
        //******************************
        // Showing the notification when
        //     button is clicked
        //******************************
        btnShowNotification.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {
        val builderNotification = getBuilderCompat()
        manager?.notify(Constants.NOTIFICATION_ID, builderNotification.build())
    }
}
