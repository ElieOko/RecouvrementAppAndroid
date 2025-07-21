package com.client.recouvrementapp.domain.tools.printer;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

public class BasePrinterUtils {

    public static void resetToDefault(@NotNull OutputStream outputStream) throws IOException {
        outputStream.write(0x1D);
        outputStream.write("B".getBytes());
        outputStream.write(0);
        outputStream.flush();
    }

    public static void resetToDefault(@NotNull UsbDeviceConnection connection, UsbEndpoint endpoint) {
        byte[] bytes = BasePrinterUtils.toBytes(0x1D);
        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
        bytes = "B".getBytes();
        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
    }

    public static void setFontLargeBold(@NotNull OutputStream outputStream) throws IOException {
        outputStream.write(new byte[]{0x1B,0x21,0x10});
        outputStream.flush();
    }

    public static void setFontLargeBold(@NotNull UsbDeviceConnection connection, UsbEndpoint endpoint) {
        byte[] bytes = new byte[]{0x1B,0x21,0x10};
        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
    }

    public static void setFontNormal(@NotNull OutputStream outputStream) throws IOException {
        outputStream.write(new byte[]{0x1B,0x21,0x00});
        outputStream.flush();
    }

    public static void setFontNormal(@NotNull UsbDeviceConnection connection, UsbEndpoint endpoint) {
        byte[] bytes = new byte[]{0x1B,0x21,0x00};
        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
    }

    public static void setPosition(@NotNull OutputStream outputStream, int position) throws IOException {
        outputStream.write(0x1B);
        outputStream.write("a".getBytes());
        outputStream.write(position);
        outputStream.flush();
    }

    public static void setPosition(@NotNull UsbDeviceConnection connection, UsbEndpoint endpoint,int position) {
        byte[] bytes = toBytes(0x1B);
        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
        bytes = "a".getBytes();
        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
        bytes = toBytes(position);
        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
    }

    public static void cut (OutputStream outputStream){
        try {
            String text = "\t\n";
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.write(text.getBytes());
            outputStream.flush();
            outputStream.write(0x1D);
            outputStream.write("V".getBytes());
            outputStream.write(48);
            outputStream.write(0);
            outputStream.flush();
        } catch (Exception ignored){}
    }

    public static void cut(UsbDeviceConnection connection, UsbEndpoint endpoint) {
        try {
            String text = "\t\n";
            byte[] bytes = text.getBytes();
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
            bytes = BasePrinterUtils.toBytes(0x1D);
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
            bytes = "V".getBytes();
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
            bytes = BasePrinterUtils.toBytes(48);
            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
        } catch (Exception ignored){}
    }

    @NotNull
    @Contract(pure = true)
    public static byte[] toBytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i);
        return result;
    }
}
