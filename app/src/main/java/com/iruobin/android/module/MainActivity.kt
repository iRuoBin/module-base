package com.iruobin.android.module

import android.content.Context
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iruobin.android.module.util.Downloader
import com.iruobin.android.module.util.NetworkStatus
import com.iruobin.android.module.util.PrintLog
import com.iruobin.android.module.util.Utils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utils.init(this)
//        ScreenDensity.setKeepScreenOn(this, true)

//        lifecycle.addObserver(Battery.Observer(this, object : Battery.BatteryChangedCallback {
//            override fun onChanged(intent: Intent?) {
//                val text = "${Battery.getCurrentQuantity(intent)} / ${Battery.getMaxQuantity(intent)} | ${Battery.isCharging(intent)}"
//                Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
//            }
//        }))

//        lifecycle.addObserver(DeviceOrientation.Observer(this, object: DeviceOrientation.ConfigChangedCallback {
//            override fun onChanged(intent: Intent?) {
//                PrintLog.d("isPortrait : ${DeviceOrientation.isPortrait(this@MainActivity)}")
//            }
//        }))

//        val manager = ScreenShotListenManager.newInstance(this)
//        manager?.setListener(object : ScreenShotListenManager.OnScreenShotListener {
//            override fun onShot(imagePath: String?) {
//                PrintLog.d("imagePath : $imagePath")
//            }
//        })
//        manager?.startListen()

        lifecycle.addObserver(NetworkStatus.Observer(this, object : NetworkStatus.NetworkChangedCallback {
            override fun onChanged(context: Context?, networkInfo: NetworkInfo?) {
                PrintLog.i("isConnected: ${NetworkStatus.isConnected(networkInfo)}")
                PrintLog.i("isWifi: ${NetworkStatus.isWifi(networkInfo)}")
                PrintLog.i("networkIsValidated: ${NetworkStatus.networkIsValidated(context)}")
            }
        }))


        tv.setOnClickListener{
//            Clipboard.setText("button")

//            Toast.makeText(this, Clipboard.getText(), Toast.LENGTH_SHORT).show()

//            Location.getAMapLocation(AMapLocationListener { location ->
//                PrintLog.d(location.toString())
//                Toast.makeText(this, location.toString(), Toast.LENGTH_LONG).show()
//            })

//            PrintLog.d(PackageInfos.checkApkExist("com.tencent.mm").toString())

            Downloader.download("https://f.51240.com/file/wannianrili/pic.jpg",
//            Downloader.download("http://fm.dl.126.net/mailmaster/updatemac/mailmaster-2.14.5.1269.dmg",
                object : Downloader.DownloadCompleteListener {
                    override fun downloadCompleted(file: File?) {
                        PrintLog.w(Downloader.fileToBase64(file))
                    }
                })
        }
    }
}
