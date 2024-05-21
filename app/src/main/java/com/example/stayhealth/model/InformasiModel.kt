package com.example.stayhealth.model

import android.os.Parcel
import android.os.Parcelable


data class InformasiModel(
    val image : Int,
    val title : String?,
    val desc : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(title)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InformasiModel> {
        override fun createFromParcel(parcel: Parcel): InformasiModel {
            return InformasiModel(parcel)
        }

        override fun newArray(size: Int): Array<InformasiModel?> {
            return arrayOfNulls(size)
        }
    }
}
