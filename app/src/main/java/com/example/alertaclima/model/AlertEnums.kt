package com.example.alertaclima.model


enum class AlertType(val label: String, val safetyTip: String) {
    FLOOD(
        "Alagamento",
        "Evite áreas alagadas, não atravesse água em movimento e procure um local elevado."
    ),
    LANDSLIDE(
        "Deslizamento",
        "Afaste-se de encostas, observe rachaduras no terreno e acione a Defesa Civil (199)."
    ),
    HEAT(
        "Calor extremo",
        "Hidrate-se, evite o sol entre 10h e 16h e redobre o cuidado com idosos e crianças."
    ),
    FIRE(
        "Queimada",
        "Afaste-se da fumaça, não tente apagar sozinho e ligue para os Bombeiros (193)."
    ),
    WIND(
        "Vendaval",
        "Fique longe de janelas, árvores e fios caídos e busque abrigo em local fechado."
    )
}


enum class AlertSeverity(val label: String) {
    LOW("Baixa"),
    MEDIUM("Média"),
    HIGH("Alta")
}


enum class AlertStatus(val label: String) {
    ACTIVE("Ativo"),
    RESOLVED("Resolvido")
}
