package com.hupu.testtex

import android.Manifest
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.aliyun.player.AliPlayerFactory
import com.aliyun.player.alivcplayerexpand.constants.GlobalPlayerConfig
import com.aliyun.player.alivcplayerexpand.pmanager.AliPlayerManager
import com.aliyun.player.alivcplayerexpand.theme.Theme
import com.aliyun.player.alivcplayerexpand.widget.AliyunVodPlayerView
import com.aliyun.player.source.UrlSource
import kotlinx.android.synthetic.main.video_test.*

/**
 *
 * @des:     类
 * @auth:         ldh
 * @date:     2021/2/24 10:26 AM
 */
class TestActivity2:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        setContentView(R.layout.test2)

    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this,
            arrayOf<String>(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE), 11);
    }
}