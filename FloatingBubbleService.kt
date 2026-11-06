package br.com.rotainteligente

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class FloatingBubbleService : Service() {
    private var windowManager: WindowManager? = null
    private var bubble: TextView? = null
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        bubble = TextView(this).apply {
            text = "RI"
            textSize = 17f
            setTextColor(0xFFFFFFFF.toInt())
            setBackgroundColor(0xFF16834A.toInt())
            gravity = Gravity.CENTER
            setPadding(22, 18, 22, 18)
            setOnClickListener {
                val launch = packageManager.getLaunchIntentForPackage(packageName)
                launch?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (launch != null) startActivity(launch)
            }
        }
        val type = if (android.os.Build.VERSION.SDK_INT >= 26) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE
        val params = WindowManager.LayoutParams(120, 110, type, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT)
        params.gravity = Gravity.TOP or Gravity.END
        params.x = 20; params.y = 260
        windowManager?.addView(bubble, params)
    }
    override fun onDestroy() { bubble?.let { windowManager?.removeView(it) }; bubble = null; super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
}
