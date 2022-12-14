package com.example.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class AppServiceActionsEnum : Parcelable {
    START_SERVICE,
    DOWNLOAD_IMAGE,
    SHOW_IMAGE,
    STOP_SERVICE
}