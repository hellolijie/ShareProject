package com.example.test.printerdemo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lijie on 2017/8/25.
 */

public class BluetoothDeviceAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BluetoothDevice> deviceList;
    private LayoutInflater inflater;

    public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> deviceList){
        this.context = context;
        this.deviceList = deviceList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceHolder(inflater.inflate(R.layout.item_bluetooth_device, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DeviceHolder deviceHolder = (DeviceHolder) holder;
        BluetoothDevice device = deviceList.get(position);

        deviceHolder.deviceName.setText(device.getName());
        deviceHolder.deviceAddress.setText(device.getAddress());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder{
        TextView deviceName;
        TextView deviceAddress;

        public DeviceHolder(View itemView) {
            super(itemView);
            deviceName = (TextView) itemView.findViewById(R.id.tv_device_name);
            deviceAddress = (TextView) itemView.findViewById(R.id.tv_device_address);
        }
    }
}
