package com.devproject.miguelfagundez.basicnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private fun getBuilderCompat():NotificationCompat.Builder{
        val builderCompat = NotificationCompat.Builder(this@MainActivity, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(Constants.NOTIFICATION_0_TITLE)
            .setContentText(Constants.NOTIFICATION_0_TEXT)
            .setSmallIcon(R.drawable.ic_download_cloud)
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
