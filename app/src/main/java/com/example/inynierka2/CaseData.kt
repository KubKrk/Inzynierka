package com.example.inynierka2

data class CaseData(
    // DANE KLIENTA
    var clientName: String = "",       // editTextClientName
    var clientSurname: String = "",    // editTextClientSurname
    var clientPesel: String = "",      // editTextExtraInfo
    var clientPhone: String = "",      // editTextClientPhone

    // DANE SAMOCHODU (naszego klienta)
    var carMark: String = "",          // marka
    var carModel: String = "",         // model
    var carPlates: String = "",        // blachy (tablice rejestracyjne)
    var carFirstRegDate: String = "",  // rej1 (data pierwszej rejestracji)

    // DANE UBEZPIECZALNI (poszkodowanego i sprawcy)
    var poszkodInsurance: String = "", // ubezp (ubezpieczalnia poszkodowanego)
    var sprawcaInsurance: String = "", // ubezp1 (ubezpieczalnia sprawcy)
    var sprawcaCarPlates: String = "", // blachy1
    var sprawcaCarMark: String = "",   // marka1

    // PODPIS
    var signatureUrl: String = ""
)
