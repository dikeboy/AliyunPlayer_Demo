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
class TestActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        setContentView(R.layout.video_test)



        var playBean =intent.getSerializableExtra("playBean") as PlayBean


        if(true){
            playBefore(playBean)
//            return
        }
        initPlayView(video_view)


        val urlSource = UrlSource()
        urlSource.uri = playBean.url
        video_view.setLocalSource(urlSource)
        video_view.setAutoPlay(false)

//        Log.e("lin","init")
//        video_view.postDelayed({
//            video_view.start()
//            Log.e("lin","play")
//        },2000)

        var view = RecyclerView(this)
        video_view.setOnPreparedListener {
            var mediaInfo = video_view.mediaInfo
            if(mediaInfo!=null){
                video_view.setCoverUri(mediaInfo.coverUrl)

            }
        }
        playBtn.setOnClickListener {
            if(video_view.isPlaying)
                video_view.pause()
            else
                 video_view.start()
        }
    }

    fun playBefore(bean: PlayBean){
        var aliyunVodPlayer = AliPlayerManager.getPlayerList(applicationContext)
//        var aliyunVidSts =  UrlSource();
//        aliyunVidSts.uri = bean.url
//        aliyunVodPlayer.setDataSource(aliyunVidSts);
//        aliyunVodPlayer.prepare();
         var  surfaceView = findViewById<View>(R.id.scanner_view) as SurfaceView
        surfaceView.visibility = View.VISIBLE
        surfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.e("lin","test surfaceCreated")
                aliyunVodPlayer.setDisplay(holder)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                aliyunVodPlayer.redraw()
                Log.e("lin","test surfaceChanged")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
//                aliyunVodPlayer.setDisplay(null)
                Log.e("lin","test surfaceDestroyed")
            }
        })

        playBtn.postDelayed({
            // 开始播放。
            aliyunVodPlayer.start();

        },500)

    }

    fun initPlayView(mAliyunVodPlayerView: AliyunVodPlayerView){

        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true)
        mAliyunVodPlayerView.setTheme(Theme.Blue)
        mAliyunVodPlayerView.setAutoPlay(true)

        //界面设置

        //界面设置
        mAliyunVodPlayerView.setEnableHardwareDecoder(GlobalPlayerConfig.mEnableHardDecodeType)
        mAliyunVodPlayerView.setRenderMirrorMode(GlobalPlayerConfig.mMirrorMode)
        mAliyunVodPlayerView.setRenderRotate(GlobalPlayerConfig.mRotateMode)
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