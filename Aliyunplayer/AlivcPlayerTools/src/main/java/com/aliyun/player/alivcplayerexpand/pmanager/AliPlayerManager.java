package com.aliyun.player.alivcplayerexpand.pmanager;

import android.content.Context;

import com.aliyun.player.AliListPlayer;
import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;

/**
 * @des: 类
 * @auth: ldh
 * @date: 2021/2/26 2:24 PM
 */
public class AliPlayerManager {
    public static AliPlayer aliPlayer;
    public static AliListPlayer getPlayerList(Context context){
        if(aliPlayer==null)
            aliPlayer = AliPlayerFactory.createAliListPlayer(context);
        return ((AliListPlayer)aliPlayer);
    }
}
