package com.aliyun.player.alivcplayerexpand.view.dlna.callback;


import com.aliyun.player.alivcplayerexpand.view.dlna.domain.IDevice;

/**
 * 设备状态改变监听接口
 */

public interface DeviceListChangedListener {

    /**
     * 某设备被发现之后回调该方法
     * @param device    被发现的设备
     */
    void onDeviceAdded(IDevice device);

    /**
     * 在已发现设备中 移除了某设备之后回调该接口
     * @param device    被移除的设备
     */
    void onDeviceRemoved(IDevice device);
}
