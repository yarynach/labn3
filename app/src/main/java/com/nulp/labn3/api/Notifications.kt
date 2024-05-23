package com.nulp.labn3.api

import com.google.gson.annotations.SerializedName


data class Notifications (

  @SerializedName("idAppointment" ) var idAppointment : String? = null,
  @SerializedName("status"        ) var status        : String? = null

)