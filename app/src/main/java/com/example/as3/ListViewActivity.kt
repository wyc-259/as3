package com.example.as3

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ListViewActivity : AppCompatActivity() {

    private val names = arrayOf("Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant")
    private val descs = arrayOf("草原之王", "森林之王", "聪明的猴子", "忠诚的朋友", "可爱的猫咪", "庞大的大象")
    private val imageIds = intArrayOf(
        R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
        R.drawable.dog, R.drawable.cat, R.drawable.elephant
    )

    private var pendingTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)

        val listItems = ArrayList<Map<String, Any>>()
        for (i in names.indices) {
            val item = HashMap<String, Any>()
            item["image"] = imageIds[i]
            item["name"] = names[i]
            item["desc"] = descs[i]
            listItems.add(item)
        }

        val adapter = SimpleAdapter(
            this,
            listItems,
            R.layout.list_item,
            arrayOf("image", "name", "desc"),
            intArrayOf(R.id.iv_animal, R.id.tv_name, R.id.tv_desc)
        )

        val listView = findViewById<ListView>(R.id.lv_animals)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val name = names[position]
            Toast.makeText(this, "选中了：$name（${descs[position]}）", Toast.LENGTH_SHORT).show()
            sendNotification(name)
        }
    }

    private fun sendNotification(title: String) {
        createChannel()
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            pendingTitle = title
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_NOTIFICATION
            )
            return
        }
        doNotify(title)
    }

    private fun doNotify(title: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText("您刚刚单击了 $title 列表项")
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(this).notify(title.hashCode(), notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "列表通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICATION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            pendingTitle?.let { doNotify(it) }
            pendingTitle = null
        }
    }

    companion object {
        private const val CHANNEL_ID = "list_notification"
        private const val REQUEST_NOTIFICATION = 100
    }
}
