//package com.client.recouvrementapp.domain.tools.printer;
//
//import android.annotation.SuppressLint;
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
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import io.objectbox.Box;
//
//public class PrinterWifiUtils {
//
//    public static void printSimple(String device, @NotNull TransferModel model) throws IOException {
//        Socket socket = new Socket();
//        socket.connect(new InetSocketAddress(device,9100), 1300);
//        OutputStream outputStream = socket.getOutputStream();
//
//        String text;
//        //Reset
//        BasePrinterUtils.resetToDefault(outputStream);
//
//        //LargeBoldFont
//        BasePrinterUtils.setFontLargeBold(outputStream);
//
//        text = AppUtils.centerPadding(model.getOrderNumber()+"", "Original",48,' ');
//        text = text+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        //Company
//        text = "Soficom Transfert\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        //Branch
//        text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        //TransfertType
//        String type = "Unknown";
//        switch (model.getTransferTypeId().intValue()){
//            case 1:
//                type = "Envoi"; break;
//            case 2:
//                type = "Retrait"; break;
//            case 3:
//            case 5:
//                type = "Ria"; break;
//            case 4:
//                type = "Special"; break;
//            case 6:
//                type = "MoneyTrans"; break;
//        }
//        text = type+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        //NormalFont
//        BasePrinterUtils.setFontNormal(outputStream);
//
//        Box<IntervalModel> intervalBox = ObjectBox.getBoxStore().boxFor(IntervalModel.class);
//        IntervalModel intervalModel = (model.getIntervalId()>0) ? intervalBox.get(model.getIntervalId()) : null;
//
//        if(intervalModel!=null){
//            DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.GERMAN);
//            String min  = decimalFormat.format(intervalModel.getMin());
//            String max = (intervalModel.getMax()==0)? AppUtils.rightPadding("+",min.length(),'+') :decimalFormat.format(intervalModel.getMax())+"";
//            text = min+" - "+max+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        Box<CurrencyModel> currencyBox = ObjectBox.getBoxStore().boxFor(CurrencyModel.class);
//        CurrencyModel currencyModel = currencyBox.get(model.getCurrencyId());
//        if(intervalModel!=null){
//            text = currencyModel.getTitle()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getDateCreated()!=null){
//            try {
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                Date date = format.parse(model.getDateCreated());
//                if(date!=null){
//                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                    String strDate = dateFormat.format(date);
//                    text = strDate+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //Cut
//        BasePrinterUtils.cut(outputStream);
//
//        //Reset
//        BasePrinterUtils.resetToDefault(outputStream);
//
//        text = "\t\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        //LargeBoldFont
//        BasePrinterUtils.setFontLargeBold(outputStream);
//
//        text = AppUtils.centerPadding(model.getOrderNumber()+"", "Client",48,' ');
//        text = text+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        text = "Soficom Transfert\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        type = "Unknown";
//        switch (model.getTransferTypeId().intValue()){
//            case 1:
//                type = "Envoi"; break;
//            case 2:
//                type = "Retrait"; break;
//            case 3:
//            case 5:
//                type = "Ria"; break;
//            case 4:
//                type = "Special"; break;
//            case 6:
//                type = "MoneyTrans"; break;
//        }
//        text = type+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        //NormalFont
//        BasePrinterUtils.setFontNormal(outputStream);
//
//        if(intervalModel!=null){
//            DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.GERMAN);
//            String min  = decimalFormat.format(intervalModel.getMin());
//            String max = (intervalModel.getMax()==0)? AppUtils.rightPadding("+",min.length(),'+') :decimalFormat.format(intervalModel.getMax())+"";
//            text = min+" - "+max+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(currencyModel!=null){
//            text = currencyModel.getTitle()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getDateCreated()!=null){
//            try {
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                Date date = format.parse(model.getDateCreated());
//                if(date!=null){
//                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                    String strDate = dateFormat.format(date);
//                    text = strDate+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(model.getBarcode()!=null){
//            text = "\t\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//            //Center
//            BasePrinterUtils.setPosition(outputStream,1);
//
//            /*Bitmap bmp = AppUtils.encodeToQrCode(model.getBarcode());
//            if (bmp != null ) {
//                byte[] command = AppUtils.decodeBitmap(bmp);
//                if(command!=null){
//                    outputStream.write(command);
//                    outputStream.flush();
//                }
//            }*/
//            //Left
//            BasePrinterUtils.setPosition(outputStream,0);
//        }
//
//        outputStream.write("\t\n".getBytes());
//        outputStream.flush();
//
//        text = "Ceci est juste un ticket pour votre numero\r\nd'ordre au guichet et non un recu de paiement\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Toujours a votre ecoute, pour mieux vous servir\nen toute securite et rapidite\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Plus d'info +243 819 872 444, +243 998 724 444\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Email :dir@groupesoficom.com"+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Web   :https://www.groupesoficom.com/"+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Veuillez-nous contacter au de probleme\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        //endregion
//
//        //Cut
//        BasePrinterUtils.cut(outputStream);
//
//        socket.close();
//    }
//
//    public static void printNormal(String device, @NotNull TransferModel model) throws IOException {
//        Socket socket = new Socket();
//        socket.connect(new InetSocketAddress(device,9100), 1300);
//        OutputStream outputStream = socket.getOutputStream();
//
//        //region Original
//
//        //Reset
//        BasePrinterUtils.resetToDefault(outputStream);
//
//        BasePrinterUtils.setFontLargeBold(outputStream);
//
//        String text;
//        text = AppUtils.centerPadding(model.getOrderNumber()+"", "Original",48,' ');
//        text = text+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        text = "Soficom Transfert\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        String type = "Unknown";
//        switch (model.getTransferTypeId().intValue()){
//            case 1:
//                type = "Envoi"; break;
//            case 2:
//                type = "Retrait"; break;
//            case 3:
//            case 5:
//                type = "Ria"; break;
//            case 4:
//                type = "Special"; break;
//            case 6:
//                type = "MoneyTrans"; break;
//        }
//        text = type+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        BasePrinterUtils.setFontNormal(outputStream);
//
//        if(model.getTransferTypeId()==1){
//            text = AppUtils.rightPadding("Destination",15,' ');
//            text = text+" :"+model.getToBranchName()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getTransferTypeId()==2||model.getTransferTypeId()==3||model.getTransferTypeId()==6){
//            text = AppUtils.rightPadding("Provenance",15,' ');
//            text = text+" :"+model.getFromBranchName()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getTransferTypeId()==4){
//            if(model.getSenderName()!=null){
//                text = AppUtils.rightPadding("Nom Complet",15,' ');
//                text = text+" :"+model.getSenderName()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//
//            if(model.getSenderPhone()!=null){
//                text = AppUtils.rightPadding("Telephone",15,' ');
//                text = text+" :"+model.getSenderPhone()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//        } else {
//
//            if(model.getTransferTypeId()==1){
//
//                if(model.getSenderName()!=null){
//                    text = AppUtils.rightPadding("Expediteur",15,' ');
//                    text = text+" :"+model.getSenderName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getSenderPhone()!=null){
//                    text = AppUtils.rightPadding("Exp. Tel",15,' ');
//                    text = text+" :"+model.getSenderPhone()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getLocation()!=null){
//                    text = AppUtils.rightPadding("Adresse",15,' ');
//                    text = text+" :"+model.getLocation()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverName()!=null){
//                    text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                    text = text+" :"+model.getReceiverName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverPhone()!=null){
//                    text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                    text = text+" :"+model.getReceiverPhone()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            } else {
//
//                if(model.getSenderName()!=null){
//                    text = AppUtils.rightPadding("Expediteur",15,' ');
//                    text = text+" :"+model.getSenderName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverName()!=null){
//                    text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                    text = text+" :"+model.getReceiverName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverPhone()!=null){
//                    text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                    text = text+" :"+model.getReceiverPhone()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getLocation()!=null){
//                    text = AppUtils.rightPadding("Adresse",15,' ');
//                    text = text+" :"+model.getLocation()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            }
//
//        }
//
//        if(model.getAmount()>0 && model.getCurrencyCode()!=null){
//            text = AppUtils.rightPadding("Montant",15,' ');
//            @SuppressLint("DefaultLocale")
//            String amount = String.format ("%.0f", model.getAmount());
//            text = text+" :"+amount+" "+model.getCurrencyCode()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getCode()!=null){
//            text = AppUtils.rightPadding("Code",15,' ');
//            text = text+" :"+model.getCode()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getTransferTypeId()==4){
//            if(model.getNote()!=null){
//                text = AppUtils.rightPadding("Message",15,' ');
//                text = text+" :"+model.getNote()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//        } else {
//            if(model.getNote()!=null){
//                text = AppUtils.rightPadding("Motif",15,' ');
//                text = text+" :"+model.getNote()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//        }
//
//        if(model.getDateCreated()!=null){
//            try {
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                Date date = format.parse(model.getDateCreated());
//                if(date!=null){
//                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                    String strDate = dateFormat.format(date);
//                    text = strDate+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //endregion
//
//        BasePrinterUtils.cut(outputStream);
//
//        //region Client
//
//        BasePrinterUtils.resetToDefault(outputStream);
//
//        BasePrinterUtils.setFontLargeBold(outputStream);
//
//        text = AppUtils.centerPadding(model.getOrderNumber()+"", "Client",48,' ');
//        text = text+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        text = "Soficom Transfert\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        text = SoficomTicket.getInstance().getUser().getBranch().getTitle()+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        type = "Unknown";
//        switch (model.getTransferTypeId().intValue()){
//            case 1:
//                type = "Envoi"; break;
//            case 2:
//                type = "Retrait"; break;
//            case 3:
//            case 5:
//                type = "Ria"; break;
//            case 4:
//                type = "Special"; break;
//            case 6:
//                type = "MoneyTrans"; break;
//        }
//        text = type+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//
//        BasePrinterUtils.setFontNormal(outputStream);
//
//        if(model.getTransferTypeId()==1){
//            text = AppUtils.rightPadding("Destination",15,' ');
//            text = text+" :"+model.getToBranchName()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getTransferTypeId()==2||model.getTransferTypeId()==3||model.getTransferTypeId()==6){
//            text = AppUtils.rightPadding("Provenance",15,' ');
//            text = text+" :"+model.getFromBranchName()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getTransferTypeId()==4){
//            if(model.getSenderName()!=null){
//                text = AppUtils.rightPadding("Nom Complet",15,' ');
//                text = text+" :"+model.getSenderName()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//
//            if(model.getSenderPhone()!=null){
//                text = AppUtils.rightPadding("Telephone",15,' ');
//                text = text+" :"+model.getSenderPhone()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//        } else {
//
//            if(model.getTransferTypeId()==1){
//
//                if(model.getSenderName()!=null){
//                    text = AppUtils.rightPadding("Expediteur",15,' ');
//                    text = text+" :"+model.getSenderName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getSenderPhone()!=null){
//                    text = AppUtils.rightPadding("Exp. Tel",15,' ');
//                    text = text+" :"+model.getSenderPhone()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getLocation()!=null){
//                    text = AppUtils.rightPadding("Adresse",15,' ');
//                    text = text+" :"+model.getLocation()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverName()!=null){
//                    text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                    text = text+" :"+model.getReceiverName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverPhone()!=null){
//                    text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                    text = text+" :"+model.getReceiverPhone()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            } else {
//
//                if(model.getSenderName()!=null){
//                    text = AppUtils.rightPadding("Expediteur",15,' ');
//                    text = text+" :"+model.getSenderName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverName()!=null){
//                    text = AppUtils.rightPadding("Beneficiaire",15,' ');
//                    text = text+" :"+model.getReceiverName()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getReceiverPhone()!=null){
//                    text = AppUtils.rightPadding("Ben. Tel",15,' ');
//                    text = text+" :"+model.getReceiverPhone()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//
//                if(model.getLocation()!=null){
//                    text = AppUtils.rightPadding("Adresse",15,' ');
//                    text = text+" :"+model.getLocation()+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            }
//
//        }
//
//        if(model.getAmount()>0 && model.getCurrencyCode()!=null){
//            text = AppUtils.rightPadding("Montant",15,' ');
//            @SuppressLint("DefaultLocale")
//            String amount = String.format ("%.0f", model.getAmount());
//            text = text+" :"+amount+" "+model.getCurrencyCode()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getCode()!=null){
//            text = AppUtils.rightPadding("Code",15,' ');
//            text = text+" :"+model.getCode()+"\r\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        if(model.getTransferTypeId()==4){
//            if(model.getNote()!=null){
//                text = AppUtils.rightPadding("Message",15,' ');
//                text = text+" :"+model.getNote()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//        } else {
//            if(model.getNote()!=null){
//                text = AppUtils.rightPadding("Motif",15,' ');
//                text = text+" :"+model.getNote()+"\r\n";
//                outputStream.write(text.getBytes());
//                outputStream.flush();
//            }
//        }
//
//        if(model.getDateCreated()!=null){
//            try {
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
//                Date date = format.parse(model.getDateCreated());
//                if(date!=null){
//                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy - hh:mm", Locale.getDefault());
//                    String strDate = dateFormat.format(date);
//                    text = strDate+"\r\n";
//                    outputStream.write(text.getBytes());
//                    outputStream.flush();
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(model.getBarcode()!=null){
//            text = "\t\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//            //Center
//            BasePrinterUtils.setPosition(outputStream,1);
//            /*Bitmap bmp = AppUtils.encodeToQrCode(model.getBarcode());
//            if (bmp != null ) {
//                byte[] command = AppUtils.decodeBitmap(bmp);
//                if(command!=null){
//                    outputStream.write(command);
//                    outputStream.flush();
//                }
//            }*/
//            //Left
//            BasePrinterUtils.setPosition(outputStream,1);
//            text = "\t\n";
//            outputStream.write(text.getBytes());
//            outputStream.flush();
//        }
//
//        text = "Ceci est juste un ticket pour votre numero\r\nd'ordre au guichet et non un recu de paiement\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Toujours a votre ecoute, pour mieux vous servir\nen toute securite et rapidite\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Plus d'info +243 819 872 444, +243 998 724 444\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Email :dir@groupesoficom.com"+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Web   :https://www.groupesoficom.com/"+"\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        text = "Veuillez-nous contacter au de probleme\r\n";
//        outputStream.write(text.getBytes());
//        outputStream.flush();
//        //endregion
//        BasePrinterUtils.cut(outputStream);
//
//        socket.close();
//    }
//}
