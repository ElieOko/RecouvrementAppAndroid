//package com.client.recouvrementapp.domain.tools.printer;
//
//import android.annotation.SuppressLint;
//import android.hardware.usb.UsbConstants;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbDeviceConnection;
//import android.hardware.usb.UsbEndpoint;
//import android.hardware.usb.UsbInterface;
//import android.hardware.usb.UsbManager;
//
//import com.soficom_transfert.tickets.app.SoficomTicket;
//import com.soficom_transfert.tickets.data.helpers.ObjectBox;
//import com.soficom_transfert.tickets.data.models.CurrencyModel;
//import com.soficom_transfert.tickets.data.models.IntervalModel;
//import com.soficom_transfert.tickets.data.models.TransferModel;
//import com.soficom_transfert.tickets.tools.AppUtils;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import io.objectbox.Box;
//
//public class PrinterUsbUtils {
//    public static void printSimple(UsbManager manager, @NotNull UsbDevice device, TransferModel model){
//        UsbInterface usbInterface = device.getInterface(0);
//        for (int i = 0; i < usbInterface.getEndpointCount(); i++){
//            UsbEndpoint endpoint = usbInterface.getEndpoint(i);
//            if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK && endpoint.getDirection() == UsbConstants.USB_DIR_OUT){
//                UsbDeviceConnection connection = manager.openDevice(device);
//                connection.claimInterface(usbInterface, true);
//
//                byte[] bytes = "\n".getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                String text;
//
//                //Reset
//                BasePrinterUtils.resetToDefault(connection,endpoint);
//
//                //LargeBoldFont
//                BasePrinterUtils.setFontLargeBold(connection,endpoint);
//
//                text = AppUtils.centerPadding(model.getOrderNumber()+"", "Original",48,' ');
//                text = text+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                //Company
//                text = "Soficom Transfert\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                //Branch
//                text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                //TransfertType
//                String type = "Unknown";
//                switch (model.getTransferTypeId().intValue()){
//                    case 1:
//                        type = "Envoi"; break;
//                    case 2:
//                        type = "Retrait"; break;
//                    case 3:
//                    case 5:
//                        type = "Ria"; break;
//                    case 4:
//                        type = "Special"; break;
//                    case 6:
//                        type = "MoneyTrans"; break;
//                }
//                text = type+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                //NormalFont
//                BasePrinterUtils.setFontNormal(connection,endpoint);
//
//                Box<IntervalModel> intervalBox = ObjectBox.getBoxStore().boxFor(IntervalModel.class);
//                IntervalModel intervalModel = (model.getIntervalId()>0) ? intervalBox.get(model.getIntervalId()) : null;
//
//                if(intervalModel!=null){
//                    DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.GERMAN);
//                    String min  = decimalFormat.format(intervalModel.getMin());
//                    String max = (intervalModel.getMax()==0)? AppUtils.rightPadding("+",min.length(),'+') :decimalFormat.format(intervalModel.getMax())+"";
//                    text = min+" - "+max+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                Box<CurrencyModel> currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel.class);
//                CurrencyModel currencyModel = currencyBox.get(model.getCurrencyId());
//                if(intervalModel!=null){
//                    text = currencyModel.getTitle()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getDateCreated()!=null){
//                    try {
//                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                        Date date = format.parse(model.getDateCreated());
//                        if(date!=null){
//                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                            String strDate = dateFormat.format(date);
//                            text = strDate+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                //Cut
//                BasePrinterUtils.cut(connection,endpoint);
//
//                //Reset
//                BasePrinterUtils.resetToDefault(connection,endpoint);
//
//                text = "\t\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                //LargeBoldFont
//                BasePrinterUtils.setFontLargeBold(connection,endpoint);
//
//                text = AppUtils.centerPadding(model.getOrderNumber()+"", "Client",48,' ');
//                text = text+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                text = "Soficom Transfert\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                type = "Unknown";
//                switch (model.getTransferTypeId().intValue()){
//                    case 1:
//                        type = "Envoi"; break;
//                    case 2:
//                        type = "Retrait"; break;
//                    case 3:
//                    case 5:
//                        type = "Ria"; break;
//                    case 4:
//                        type = "Special"; break;
//                    case 6:
//                        type = "MoneyTrans"; break;
//                }
//                text = type+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                //NormalFont
//                BasePrinterUtils.setFontNormal(connection,endpoint);
//
//                if(intervalModel!=null){
//                    DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.GERMAN);
//                    String min  = decimalFormat.format(intervalModel.getMin());
//                    String max = (intervalModel.getMax()==0)? AppUtils.rightPadding("+",min.length(),'+') :decimalFormat.format(intervalModel.getMax())+"";
//                    text = min+" - "+max+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(currencyModel!=null){
//                    text = currencyModel.getTitle()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getDateCreated()!=null){
//                    try {
//                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                        Date date = format.parse(model.getDateCreated());
//                        if(date!=null){
//                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                            String strDate = dateFormat.format(date);
//                            text = strDate+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if(model.getBarcode()!=null){
//                    text = "\t\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    //Center
//                    BasePrinterUtils.setPosition(connection,endpoint,1);
//
//                    /*Bitmap bmp = AppUtils.encodeToQrCode(model.getBarcode());
//                    if (bmp != null ) {
//                        byte[] command = AppUtils.decodeBitmap(bmp);
//                        if(command!=null){
//                            connection.bulkTransfer(endpoint, command, command.length, 100000);
//                        }
//                    }*/
//                    //Left
//                    BasePrinterUtils.setPosition(connection,endpoint,0);
//                }
//
//                bytes = "\t\n".getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Ceci est juste un ticket pour votre numero\r\nd'ordre au guichet et non un recu de paiement\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Toujours a votre ecoute, pour mieux vous servir\nen toute securite et rapidite\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Plus d'info +243 819 872 444, +243 998 724 444\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "email :dir@groupesoficom.com"+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "web   :https://www.groupesoficom.com/"+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Veuillez-nous contacter au de probleme\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                //endregion
//
//                BasePrinterUtils.cut(connection,endpoint);
//
//                connection.close();
//                connection.releaseInterface(usbInterface);
//            }
//        }
//    }
//
//    public static void printNormal(UsbManager manager, @NotNull UsbDevice device, TransferModel model){
//        UsbInterface usbInterface = device.getInterface(0);
//        for (int i = 0; i < usbInterface.getEndpointCount(); i++){
//            UsbEndpoint endpoint = usbInterface.getEndpoint(i);
//            if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK && endpoint.getDirection() == UsbConstants.USB_DIR_OUT){
//                UsbDeviceConnection connection = manager.openDevice(device);
//                connection.claimInterface(usbInterface, true);
//
//                byte[] bytes = "\n".getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                //Reset
//                BasePrinterUtils.resetToDefault(connection,endpoint);
//
//                BasePrinterUtils.setFontLargeBold(connection,endpoint);
//
//                String text;
//                text = AppUtils.centerPadding(model.getOrderNumber()+"", "Original",48,' ');
//                text = text+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                text = "Soficom Transfert\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                String type = "Unknown";
//                switch (model.getTransferTypeId().intValue()){
//                    case 1:
//                        type = "Envoi"; break;
//                    case 2:
//                        type = "Retrait"; break;
//                    case 3:
//                    case 5:
//                        type = "Ria"; break;
//                    case 4:
//                        type = "Special"; break;
//                    case 6:
//                        type = "MoneyTrans"; break;
//                }
//                text = type+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                BasePrinterUtils.setFontNormal(connection,endpoint);
//
//                if(model.getTransferTypeId()==1){
//                    text = AppUtils.rightPadding("Destination",15,' ');
//                    text = text+" :"+model.getToBranchName()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getTransferTypeId()==2||model.getTransferTypeId()==3||model.getTransferTypeId()==6){
//                    text = AppUtils.rightPadding("Provenance",15,' ');
//                    text = text+" :"+model.getFromBranchName()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getTransferTypeId()==4){
//                    if(model.getSenderName()!=null){
//                        text = AppUtils.rightPadding("Nom Complet",15,' ');
//                        text = text+" :"+model.getSenderName()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//
//                    if(model.getSenderPhone()!=null){
//                        text = AppUtils.rightPadding("Telephone",15,' ');
//                        text = text+" :"+model.getSenderPhone()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//                } else {
//
//                    if(model.getTransferTypeId()==1){
//
//                        if(model.getSenderName()!=null){
//                            text = AppUtils.rightPadding("Expediteur",15,' ');
//                            text = text+" :"+model.getSenderName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getSenderPhone()!=null){
//                            text = AppUtils.rightPadding("Exp. Tel",15,' ');
//                            text = text+" :"+model.getSenderPhone()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getLocation()!=null){
//                            text = AppUtils.rightPadding("Adresse",15,' ');
//                            text = text+" :"+model.getLocation()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverName()!=null){
//                            text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                            text = text+" :"+model.getReceiverName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverPhone()!=null){
//                            text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                            text = text+" :"+model.getReceiverPhone()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    } else {
//
//                        if(model.getSenderName()!=null){
//                            text = AppUtils.rightPadding("Expediteur",15,' ');
//                            text = text+" :"+model.getSenderName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverName()!=null){
//                            text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                            text = text+" :"+model.getReceiverName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverPhone()!=null){
//                            text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                            text = text+" :"+model.getReceiverPhone()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getLocation()!=null){
//                            text = AppUtils.rightPadding("Adresse",15,' ');
//                            text = text+" :"+model.getLocation()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    }
//
//                }
//
//                if(model.getAmount()>0 && model.getCurrencyCode()!=null){
//                    text = AppUtils.rightPadding("Montant",15,' ');
//                    @SuppressLint("DefaultLocale")
//                    String amount = String.format ("%.0f", model.getAmount());
//                    text = text+" :"+amount+" "+model.getCurrencyCode()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getCode()!=null){
//                    text = AppUtils.rightPadding("Code",15,' ');
//                    text = text+" :"+model.getCode()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getTransferTypeId()==4){
//                    if(model.getNote()!=null){
//                        text = AppUtils.rightPadding("Message",15,' ');
//                        text = text+" :"+model.getNote()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//                } else {
//                    if(model.getNote()!=null){
//                        text = AppUtils.rightPadding("Motif",15,' ');
//                        text = text+" :"+model.getNote()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//                }
//
//                if(model.getDateCreated()!=null){
//                    try {
//                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                        Date date = format.parse(model.getDateCreated());
//                        if(date!=null){
//                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                            String strDate = dateFormat.format(date);
//                            text = strDate+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                //endregion
//
//                BasePrinterUtils.cut(connection,endpoint);
//
//                //region Client
//
//                BasePrinterUtils.resetToDefault(connection,endpoint);
//
//                BasePrinterUtils.setFontLargeBold(connection,endpoint);
//
//                text = AppUtils.centerPadding(model.getOrderNumber()+"", "Client",48,' ');
//                text = text+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                text = "Soficom Transfert\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                type = "Unknown";
//                switch (model.getTransferTypeId().intValue()){
//                    case 1:
//                        type = "Envoi"; break;
//                    case 2:
//                        type = "Retrait"; break;
//                    case 3:
//                    case 5:
//                        type = "Ria"; break;
//                    case 4:
//                        type = "Special"; break;
//                    case 6:
//                        type = "MoneyTrans"; break;
//                }
//                text = type+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//
//                BasePrinterUtils.setFontNormal(connection,endpoint);
//
//                if(model.getTransferTypeId()==1){
//                    text = AppUtils.rightPadding("Destination",15,' ');
//                    text = text+" :"+model.getToBranchName()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getTransferTypeId()==2||model.getTransferTypeId()==3||model.getTransferTypeId()==6){
//                    text = AppUtils.rightPadding("Provenance",15,' ');
//                    text = text+" :"+model.getFromBranchName()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getTransferTypeId()==4){
//                    if(model.getSenderName()!=null){
//                        text = AppUtils.rightPadding("Nom Complet",15,' ');
//                        text = text+" :"+model.getSenderName()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//
//                    if(model.getSenderPhone()!=null){
//                        text = AppUtils.rightPadding("Telephone",15,' ');
//                        text = text+" :"+model.getSenderPhone()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//                } else {
//
//                    if(model.getTransferTypeId()==1){
//
//                        if(model.getSenderName()!=null){
//                            text = AppUtils.rightPadding("Expediteur",15,' ');
//                            text = text+" :"+model.getSenderName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getSenderPhone()!=null){
//                            text = AppUtils.rightPadding("Exp. Tel",15,' ');
//                            text = text+" :"+model.getSenderPhone()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getLocation()!=null){
//                            text = AppUtils.rightPadding("Adresse",15,' ');
//                            text = text+" :"+model.getLocation()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverName()!=null){
//                            text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                            text = text+" :"+model.getReceiverName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverPhone()!=null){
//                            text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                            text = text+" :"+model.getReceiverPhone()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    } else {
//
//                        if(model.getSenderName()!=null){
//                            text = AppUtils.rightPadding("Expediteur",15,' ');
//                            text = text+" :"+model.getSenderName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverName()!=null){
//                            text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                            text = text+" :"+model.getReceiverName()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getReceiverPhone()!=null){
//                            text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                            text = text+" :"+model.getReceiverPhone()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//
//                        if(model.getLocation()!=null){
//                            text = AppUtils.rightPadding("Adresse",15,' ');
//                            text = text+" :"+model.getLocation()+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    }
//
//                }
//
//                if(model.getAmount()>0 && model.getCurrencyCode()!=null){
//                    text = AppUtils.rightPadding("Montant",15,' ');
//                    @SuppressLint("DefaultLocale")
//                    String amount = String.format ("%.0f", model.getAmount());
//                    text = text+" :"+amount+" "+model.getCurrencyCode()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getCode()!=null){
//                    text = AppUtils.rightPadding("Code",15,' ');
//                    text = text+" :"+model.getCode()+"\r\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//
//                if(model.getTransferTypeId()==4){
//                    if(model.getNote()!=null){
//                        text = AppUtils.rightPadding("Message",15,' ');
//                        text = text+" :"+model.getNote()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//                } else {
//                    if(model.getNote()!=null){
//                        text = AppUtils.rightPadding("Motif",15,' ');
//                        text = text+" :"+model.getNote()+"\r\n";
//                        bytes = text.getBytes();
//                        connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    }
//                }
//
//                if(model.getDateCreated()!=null){
//                    try {
//                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                        Date date = format.parse(model.getDateCreated());
//                        if(date!=null){
//                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                            String strDate = dateFormat.format(date);
//                            text = strDate+"\r\n";
//                            bytes = text.getBytes();
//                            connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if(model.getBarcode()!=null){
//                    text = "\t\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                    //Center
//                    BasePrinterUtils.setPosition(connection,endpoint,1);
//                    /*Bitmap bmp = AppUtils.encodeToQrCode(model.getBarcode());
//                    if (bmp != null ) {
//                        byte[] command = AppUtils.decodeBitmap(bmp);
//                        if(command!=null){
//                            connection.bulkTransfer(endpoint, command, command.length, 100000);
//                        }
//                    }*/
//                    //Left
//                    BasePrinterUtils.setPosition(connection,endpoint,1);
//                    text = "\t\n";
//                    bytes = text.getBytes();
//                    connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                }
//                text = "Ceci est juste un ticket pour votre numero\r\nd'ordre au guichet et non un recu de paiement\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Toujours a votre ecoute, pour mieux vous servir\nen toute securite et rapidite\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Plus d'info +243 819 872 444, +243 998 724 444\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Email :dir@groupesoficom.com"+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Web   :https://www.groupesoficom.com/"+"\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                text = "Veuillez-nous contacter au de probleme\r\n";
//                bytes = text.getBytes();
//                connection.bulkTransfer(endpoint, bytes, bytes.length, 100000);
//                //endregion
//                BasePrinterUtils.cut(connection,endpoint);
//
//                connection.close();
//                connection.releaseInterface(usbInterface);
//            }
//        }
//    }
//}
