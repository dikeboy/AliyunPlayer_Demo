package com.hupu.testtex

import android.Manifest
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.TextureView.SurfaceTextureListener
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliyun.player.AliListPlayer
import com.aliyun.player.AliPlayerFactory
import com.aliyun.player.IPlayer
import com.aliyun.player.IPlayer.OnRenderingStartListener
import com.aliyun.player.alivcplayerexpand.pmanager.AliPlayerManager
import com.aliyun.player.nativeclass.PlayerConfig
import com.aliyun.player.source.UrlSource
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var mAliListPlayer: AliListPlayer
    lateinit var mListPlayerContainer:View
    lateinit var  mListPlayerTextureView:TextureView
    var playList = ArrayList<PlayBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()

        for(i in 0..50){
            if(i%4==0){
                playList.add(PlayBean("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4",i,"${i+1}"))
            }
            else if(i%3==0){
                playList.add(PlayBean("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4",i,"${i+1}"))
            }
            else if(i%2==0)
                 playList.add(PlayBean("https://alivc-demo-cms.alicdn.com/video/videoAD.mp4",i,"${i+1}"))
            else
                playList.add(PlayBean("http://vjs.zencdn.net/v/oceans.mp4",i,"${i+1}"))


        }

        var adapter = TestAdapter(this)
        adapter.playList.addAll(playList)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

//        var aliyunVodPlayer = AliPlayerFactory.createAliPlayer(applicationContext)
//
//        var aliyunVidSts =  UrlSource();
//        aliyunVidSts.uri = "https://alivc-demo-cms.alicdn.com/video/videoAD.mp4"
//        aliyunVodPlayer.setDataSource(aliyunVidSts);
//        aliyunVodPlayer.prepare();

        initMutiplyPlay()
        initListPlayerView()

        playList.forEach {
            mAliListPlayer.addUrl(it.url,it.uid)
        }

//
//       var  surfaceView = findViewById<View>(R.id.scanner_view) as SurfaceView
//        surfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
//            override fun surfaceCreated(holder: SurfaceHolder) {
//                aliyunVodPlayer.setDisplay(holder)
//            }
//
//            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//                aliyunVodPlayer.redraw()
//            }
//
//            override fun surfaceDestroyed(holder: SurfaceHolder) {
//                aliyunVodPlayer.setDisplay(null)
//            }
//        })
//
//        main_content.postDelayed({
//            // 开始播放。
//            aliyunVodPlayer.start();
//
//        },2000)
//        var pos = 0
//        play.setOnClickListener {
//            startPlay(pos++)
//        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState){
                     RecyclerView.SCROLL_STATE_DRAGGING->{

                     }
                     RecyclerView.SCROLL_STATE_IDLE->{
                         (rv.layoutManager as LinearLayoutManager).let {
                             var pos = it.findFirstCompletelyVisibleItemPosition()
                             if(mAliListPlayer.currentUid!="${pos+1}"){
                                 startPlay(pos)
                             }
                         }
                     }
                     RecyclerView.SCROLL_STATE_SETTLING->{

                     }
                }
            }
        })
  }

    fun initMutiplyPlay(){
        mAliListPlayer =  AliPlayerManager.getPlayerList(applicationContext);
        mAliListPlayer.setLoop(true)
        val config: PlayerConfig = mAliListPlayer.getConfig()
//        config.mClearFrameWhenStop = true
        mAliListPlayer.setConfig(config)
        mAliListPlayer.setOnPreparedListener(IPlayer.OnPreparedListener {
            mAliListPlayer.start()
        })
        mAliListPlayer.prepare();


        mAliListPlayer.setOnRenderingStartListener(OnRenderingStartListener {
            (rv.layoutManager as LinearLayoutManager).let {
                var pos = it.findFirstCompletelyVisibleItemPosition()
                if (rv != null) {
                    val mViewHolder  =
                        rv.findViewHolderForLayoutPosition(pos) as? BaseHolder
                    if (mViewHolder != null) {
//                        mViewHolder.getCoverView().setVisibility(View.GONE)
                    }
                }
            }

        })

        mAliListPlayer.setOnInfoListener(IPlayer.OnInfoListener { })
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
            ), 11
        );
    }

    var lastSurface: Surface?=null
    private fun initListPlayerView() {
         mListPlayerContainer = View.inflate(this, R.layout.layout_list_player_view, null)
         mListPlayerTextureView =
            mListPlayerContainer.findViewById<TextureView>(R.id.list_player_textureview)

        //TextureView

//        mListPlayerTextureView.getHolder().addCallback(object : SurfaceHolder.Callback {
//            override fun surfaceCreated(holder: SurfaceHolder) {
//                Log.e("lin","setDisplay")
//                mAliListPlayer.setDisplay(holder)
//                mAliListPlayer.start()
//            }
//
//            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//                Log.e("lin","surfaceChanged")
//                mAliListPlayer.redraw()
//            }
//
//            override fun surfaceDestroyed(holder: SurfaceHolder) {
//                Log.e("lin","surfaceDestroyed")
//                mAliListPlayer.setDisplay(null)
//            }
//        })

        mListPlayerTextureView.setSurfaceTextureListener(object : SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surfaceTexture: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                val mSurface = Surface(surfaceTexture);
//                val mSurface =if(lastSurface==null) Surface(surfaceTexture) else lastSurface
                if (mAliListPlayer != null) {
                    lastSurface = mSurface
                    Log.e("lin","setSurface")

                    mAliListPlayer.setSurface(mSurface)

                    mAliListPlayer.redraw()
//                    mAliListPlayer.prepare()
//                    mAliListPlayer.start()
//                    startPlay(0)

                }
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                Log.e("lin","onSurfaceTextureSizeChanged")
                if (mAliListPlayer != null) {
                    mAliListPlayer.redraw()
                }
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                Log.e("loin","onSurfaceTextureUpdated")
            }
        })


    }

    private fun startPlay(position: Int) {
        if (position < 0 || position >playList.size-1) {
            return
        }
        val mViewHolder =
            rv.findViewHolderForLayoutPosition(position) as? BaseHolder
        val parent = mListPlayerContainer.parent
        if (parent is LinearLayout) {
            (parent as ViewGroup).removeView(mListPlayerContainer)
        }
        if (mViewHolder != null) {
            mViewHolder.videoView.visibility = View.VISIBLE
            mViewHolder.videoView.addView(mListPlayerContainer, 0)
        }

        mAliListPlayer.moveTo("${position+1}")
    }


    override fun onPause() {
        super.onPause()
//        mAliListPlayer.pause()
        mListPlayerTextureView.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        mListPlayerTextureView.visibility = View.VISIBLE
    }
}