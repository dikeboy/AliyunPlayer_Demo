package com.aliyun.player.alivcplayerexpand.view.dlna.domain;

import org.fourthline.cling.model.meta.Device;

/**
 * ClingDevices
 */

public class ClingDevice implements IDevice<Device> {

    private Device mDevice;
    /**
     * 是否已选中
     */
    private boolean isSelected;

    public ClingDevice(Device device) {
        this.mDevice = device;
    }

    @Override
    public Device getDevice() {
        return mDevice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}