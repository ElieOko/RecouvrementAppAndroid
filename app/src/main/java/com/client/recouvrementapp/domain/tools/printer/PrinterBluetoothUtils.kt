package com.client.recouvrementapp.domain.tools.printer

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Build
import com.client.recouvrementapp.domain.tools.AppUtils
import java.io.IOException
import java.lang.Long
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.get

object PrinterBluetoothUtils {
    @Throws(IOException::class)
    fun printSimple(device: BluetoothDevice, context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
            return
        }
        val socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        if (!socket.isConnected) socket.connect()
        val outputStream = socket.outputStream
        var text: String

        //region Original
        //Reset
        BasePrinterUtils.resetToDefault(outputStream)

        //LargeBoldFont
        BasePrinterUtils.setFontLargeBold(outputStream)
        text = AppUtils.centerPadding("" + "", "Original", 48, ' ')
        text = text.trimIndent()
        outputStream.write(text.toByteArray())
        outputStream.flush()

        //Company
        text = "Soficom Transfert\r\n"
        outputStream.write(text.toByteArray())
        outputStream.flush()



        //Cut
        /*try {
            text = "\t\n"
            outputStream.write(text.toByteArray())
            outputStream.flush()
            outputStream.write(text.toByteArray())
            outputStream.flush()
            outputStream.write(text.toByteArray())
            outputStream.flush()
            outputStream.write(text.toByteArray())
            outputStream.flush()
            outputStream.write(text.toByteArray())
            outputStream.flush()
            outputStream.write(0x1D)
            outputStream.write("V".toByteArray())
            outputStream.write(48)
            outputStream.write(0)
            outputStream.flush()
        } catch (ignored: Exception) {
        }*/
        BasePrinterUtils.cut(outputStream)
        //endregion

        //region Client
        //Reset
        BasePrinterUtils.resetToDefault(outputStream)
        text = "\t\n"
        outputStream.write(text.toByteArray())
        outputStream.flush()
        outputStream.write(text.toByteArray())
        outputStream.flush()

        //LargeBoldFont
        BasePrinterUtils.setFontLargeBold(outputStream)

        //region BarCode
        /*if(model.getBarcode()!=null){
            text = "\t\n";
            outputStream.write(text.getBytes());
            outputStream.flush();
            //reset
            BasePrinterUtils.resetToDefault(outputStream);
            //Center
            BasePrinterUtils.setPosition(outputStream,1);

            //Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.qr_code);
            Bitmap bmp = QRCode.from(model.getBarcode()).bitmap();
            Bitmap b = Bitmap.createScaledBitmap(bmp, 150, 150, false);
            if (b != null ) {
                byte[] command = AppUtils.decodeBitmap(b);
                if(command!=null){
                    outputStream.write(command);
                    outputStream.flush();
                }
            }

            //reset
            BasePrinterUtils.resetToDefault(outputStream);
            //Center
            BasePrinterUtils.setPosition(outputStream,0);
        }*/
        //endregion
        outputStream.write("\t\n".toByteArray())
        outputStream.flush()
        text = "Toujours a votre ecoute, pour mieux vous servir en toute securite et rapidite\r\n"
        outputStream.write(text.toByteArray())
        outputStream.flush()
        text = "Plus d'info +243 819 872 444, +243 998 724 444\r\n"
        outputStream.write(text.toByteArray())
        outputStream.flush()
        text = "email :dir@groupesoficom.com\r\n"
        outputStream.write(text.toByteArray())
        outputStream.flush()
        text = "web   :https://www.groupesoficom.com/\r\n"
        outputStream.write(text.toByteArray())
        outputStream.flush()
        text = "Veuillez-nous contacter en cas probleme\r\n"
        outputStream.write(text.toByteArray())
        outputStream.flush()


        BasePrinterUtils.cut(outputStream)
        //endregion
        socket.close()
    }
}