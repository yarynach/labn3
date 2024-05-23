package com.nulp.labn3.api

import com.google.gson.annotations.SerializedName


data class Appointment (

  @SerializedName("appointments"  ) var appointments  : Appointments?  = Appointments(),
  @SerializedName("notifications" ) var notifications : Notifications? = Notifications()

)