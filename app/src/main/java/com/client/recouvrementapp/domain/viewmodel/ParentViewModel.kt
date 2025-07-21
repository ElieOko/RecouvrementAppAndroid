package com.client.recouvrementapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.client.recouvrementapp.domain.viewmodel.config.PrinterConfigViewModel

class ParentViewModel : ViewModel(){
    var vmPrinterConfig : PrinterConfigViewModel? = null
}