package com.jrexl.novanector.dataclass

data class ProfileDataClass(
    val uname:String,
    val designation: String,
    val ProfilePic : String,
    val gmailid: String,
    val dob: String,
    val contactno: String,
    val address: String,
    val Pincode :Int,
    val State:String,
    val Country: String,
     val PanCardVerify: String,
    val AadharCardVerify : String,
    val BankAccount: String,
    val BankHolderName: String,
    val BankName: String,
    val ifsc: String,


    )

data class Bankverificationdata(
    val contactno: String,
    val BankHolderName: String,
    val BankName: String,
    val BankAccount: String,
    val ifsc: String,
    val passbookimg: String
)

data class kycadpan(
    val contactno: String?,
    val adharno: String,
    val Panno: String,
    val adharimg: String,
    val panimg: String
)

data class AdharPanDetails(
    val adharno: String,
    val Panno: String
)

