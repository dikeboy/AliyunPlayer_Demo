package com.aliyun.player.alivcplayerexpand.view.dlna.manager;

import android.content.Context;
import android.util.Log;


import com.aliyun.player.alivcplayerexpand.view.dlna.domain.ClingControlPoint;
import com.aliyun.player.alivcplayerexpand.view.dlna.domain.ClingDevice;
import com.aliyun.player.alivcplayerexpand.view.dlna.domain.IControlPoint;
import com.aliyun.player.alivcplayerexpand.view.dlna.domain.IDevice;
import com.aliyun.player.alivcplayerexpand.view.dlna.service.ClingUpnpService;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDAServiceType;
import org.fourthline.cling.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Cling管理类
 * 对服务操作的代理类
 */
public class ClingManager implements IClingManager {

    private static ClingManager mInstance = null;

    public static final DeviceType DMR_DEVICE_TYPE = new UDADeviceType("MediaRenderer");
    public static final ServiceType AV_TRANSPORT_SERVICE = new UDAServiceType("AVTransport");
    /**
     * 控制服务
     */
    public static final ServiceType RENDERING_CONTROL_SERVICE = new UDAServiceType("RenderingControl");

    private ClingUpnpService mUpnpService;
    private IDeviceManager mDeviceManager;

    private ClingManager() {
    }

    public static ClingManager getInstance() {
        if (mInstance == null) {
            synchronized (ClingManager.class) {
                if (mInstance == null) {
                    mInstance = new ClingManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void setUpnpService(ClingUpnpService upnpService) {
        mUpnpService = upnpService;
    }

    @Override
    public void setDeviceManager(IDeviceManager deviceManager) {
        mDeviceManager = deviceManager;
    }

    @Override
    public Registry getRegistry() {
        return mUpnpService.getRegistry();
    }

    @Override
    public void searchDevices() {
        if (mUpnpService != null) {
            mUpnpService.getControlPoint().search();
        }
    }

    @Override
    public Collection<ClingDevice> getDmrDevices() {
        if (mUpnpService == null) {
            return null;
        }

        Collection<Device> devices = mUpnpService.getRegistry().getDevices(DMR_DEVICE_TYPE);
        if (devices == null || devices.size() == 0) {
            return null;
        }

        Collection<ClingDevice> clingDevices = new ArrayList<>();
        for (Device device : devices) {
            ClingDevice clingDevice = new ClingDevice(device);
            clingDevices.add(clingDevice);
            Log.e("AliyunDLNA", "查找设备投屏 : " + clingDevice.getDevice().getDetails().getFriendlyName());
        }
        return clingDevices;
    }

    @Override
    public IControlPoint getControlPoint() {
        if (mUpnpService == null) {
            return null;
        }
        ClingControlPoint.getInstance().setControlPoint(mUpnpService.getControlPoint());

        return ClingControlPoint.getInstance();
    }

    @Override
    public IDevice getSelectedDevice() {
        if (mDeviceManager == null) {
            return null;
        }
        return mDeviceManager.getSelectedDevice();
    }

    @Override
    public void cleanSelectedDevice() {
        if (mDeviceManager == null) {
            return;
        }
        mDeviceManager.cleanSelectedDevice();
    }

    @Override
    public void setSelectedDevice(IDevice device) {
        mDeviceManager.setSelectedDevice(device);
    }

    @Override
    public void registerAVTransport(Context context) {
        if (mDeviceManager == null) {
            return;
        }
        mDeviceManager.registerAVTransport(context);
    }

    @Override
    public void registerRenderingControl(Context context) {
        if (mDeviceManager == null) {
            return;
        }
        mDeviceManager.registerRenderingControl(context);
    }

    @Override
    public void destroy() {
        mUpnpService.onDestroy();
        mDeviceManager.destroy();
    }
}
