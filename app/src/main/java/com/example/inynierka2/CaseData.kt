package com.example.inynierka2

data class CaseData(
    // DANE KLIENTA
    var clientName: String = "",
    var clientSurname: String = "",
    var clientPesel: String = "",
    var clientPhone: String = "",

    // DANE SAMOCHODU
    var carMark: String = "",
    var carModel: String = "",
    var carFirstRegDate: String = "",
    var carPlates: String = "",

    // DANE UBEZPIECZALNI
    var poszkodInsurance: String = "",
    var sprawcaInsurance: String = "",
    var sprawcaCarPlates: String = "",
    var sprawcaCarMark: String = "",

    // PODPIS (może zostać puste)
    var signatureUrl: String = "",

    // ID właściciela (użytkownika Firebase)
    var ownerId: String = "",

    var createdAt: Long = 0L
)
