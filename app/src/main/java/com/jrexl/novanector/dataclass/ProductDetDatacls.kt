package com.jrexl.novanector.dataclass

data class ProductDetDatacls(
    val prodid: Int,
    val Prodtname: String,
    val Prodtdescription: String,
    val Prodtprice: Int,
    val ProdtstockQuantity: Int,
    val Prodtcategory: String,
    val Productimg : String,
    val ProductCoin: Int
)

data class Filtercat(
    val Prodtcategory: List<String>,
    val Prodtprice: List<Int>
)

data class OfferDetDatacls(
    val offername: String,
    val offerdescription: String,
    val offerprice: Int,
    val Offerimg : String,
)

data class categorydatclss( val name: String, val ctimg: String)
