package Modelo

import android.os.Parcel
import android.os.Parcelable

data class ListaPacientes(
    val id: String,
    val nombres: String,
    val apellidos: String,
    val edad: Int,
    val enfermedad: String,
    val numeroHabitacion: Int,
    val numeroCama: Int,
    val fechaNacimiento: String,
    val idMedicamento: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombres)
        parcel.writeString(apellidos)
        parcel.writeInt(edad)
        parcel.writeString(enfermedad)
        parcel.writeInt(numeroHabitacion)
        parcel.writeInt(numeroCama)
        parcel.writeString(fechaNacimiento)
        parcel.writeInt(idMedicamento)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListaPacientes> {
        override fun createFromParcel(parcel: Parcel): ListaPacientes {
            return ListaPacientes(parcel)
        }

        override fun newArray(size: Int): Array<ListaPacientes?> {
            return arrayOfNulls(size)
        }
    }
}
