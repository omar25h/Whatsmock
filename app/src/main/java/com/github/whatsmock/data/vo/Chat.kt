package com.github.whatsmock.data.vo

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Chat(
    val userId: Int,
    val imagePath: String?,
    val fullName: String,
    val lastMessage: String?,
    val updatedAt: Date?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readSerializable() as Date?
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(imagePath)
        parcel.writeString(fullName)
        parcel.writeString(lastMessage)
        parcel.writeSerializable(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}