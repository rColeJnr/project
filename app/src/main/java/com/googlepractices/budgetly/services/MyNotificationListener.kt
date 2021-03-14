package com.googlepractices.budgetly.services

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

/* packages names from the app, for which we want to listen the notifications*/

class MyNotificationListener: NotificationListenerService() {

    companion object ApplicationPackageNamesAndCode {
        const val INSTAGRAM_PACK_NAME = "com.instagram.android"
        const val GOOGLEPAY_PACK_NAME = "com.google.pay"
        const val YOUTUBE_PACK_NAME = "com.google.youtube"
        const val GOOGLEPAY_CODE = 1
        const val INSTAGRAM_CODE = 2
        const val YOUTUBE_CODE = 3
        const val OTHER_NOTIFICATIONS_CODE = 4
    }

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    lateinit var extras: Bundle
    lateinit var title: String
    lateinit var text: String
    lateinit var pack: String
    var subText: String? = ""


    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        pack = sbn.packageName
        extras = sbn.notification.extras
        title = extras.getString("android.title").toString()
        text = extras.getCharSequence("android.text").toString()

        if (notificationCode != OTHER_NOTIFICATIONS_CODE) {

            val extraMessages = extras.get(Notification.EXTRA_MESSAGES) as Array<*>

            if (extraMessages != null){
                for (message in extraMessages){
                    val msgBundle = (message as Bundle)
                    subText = msgBundle.getString("text")
                }
                Log.i("DetailErro1", "$subText")
            }

            if (subText?.isEmpty() == true){
                subText = text
            }
            Log.i("DetailErro2", "$subText")

            val intent = Intent("com.googlepractices.budgetly.services.mynotificationlistener").also {
                it.putExtra("Notification Code", notificationCode)
                it.putExtra("package", pack)
                it.putExtra("title", title)
                it.putExtra("text", subText)
                it.putExtra("id", sbn.id)
            }
            sendBroadcast(intent)

        }

    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val notificationCode = matchNotificationCode(sbn)
        if (notificationCode != OTHER_NOTIFICATIONS_CODE) {
            val activeNotifications = this.activeNotifications
            if (activeNotifications != null && activeNotifications.size > 0) {
                for (i in activeNotifications.indices) {
                    if (notificationCode == matchNotificationCode(activeNotifications[i])) {
                        val intent = Intent("com.googlepractices.budgetly.services.mynotificationlistener")
                        intent.putExtra("Notification Code", notificationCode)
                        sendBroadcast(intent)
                        break
                    }
                }
            }
        }
    }

    private fun matchNotificationCode(sbn: StatusBarNotification): Int {
        return when (sbn.packageName) {
            GOOGLEPAY_PACK_NAME -> {
                GOOGLEPAY_CODE
            }
            INSTAGRAM_PACK_NAME -> {
                INSTAGRAM_CODE
            }
            YOUTUBE_PACK_NAME -> {
                YOUTUBE_CODE
            }
            else -> {
                OTHER_NOTIFICATIONS_CODE
            }
        }
    }
}