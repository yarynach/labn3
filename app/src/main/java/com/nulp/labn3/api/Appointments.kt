package com.nulp.labn3.api

import com.google.gson.annotations.SerializedName


data class Appointments (

  @SerializedName("id"              ) var id              : String? = null,
  @SerializedName("fullName"        ) var fullName        : String? = null,
  @SerializedName("age"             ) var age             : Int?    = null,
  @SerializedName("appointmentType" ) var appointmentType : String? = null,
  @SerializedName("date"            ) var date            : String? = null,
  @SerializedName("time"            ) var time            : String? = null

)