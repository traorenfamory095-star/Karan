package com.example.karan.data.local


import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question

object SeedData {

    val matieres = listOf(
        Matiere(
            id = 1,
            nom = "Mathématiques",
            examen = "BAC",
            nombreChapitres = 5
        ),
        Matiere(
            id = 2,
            nom = "Physique",
            examen = "BAC",
            nombreChapitres = 4
        ),
        Matiere(
            id = 3,
            nom = "Chimie",
            examen = "BAC",
            nombreChapitres = 4
        ),
        Matiere(
            id = 4,
            nom = "Français",
            examen = "BAC",
            nombreChapitres = 5
        ),
        Matiere(
            id = 5,
            nom = "Anglais",
            examen = "BAC",
            nombreChapitres = 4
        ),
        Matiere(
            id = 6,
            nom = "Mathématiques",
            examen = "BEPC",
            nombreChapitres = 5
        ),
        Matiere(
            id = 7,
            nom = "Français",
            examen = "BEPC",
            nombreChapitres = 5
        ),
        Matiere(
            id = 8,
            nom = "Physique",
            examen = "BEPC",
            nombreChapitres = 4
        ),
        Matiere(
            id = 9,
            nom = "Anglais",
            examen = "BEPC",
            nombreChapitres = 4
        )
    )

    val questions = listOf(
        Question(
            id = 1,
            matiereId = 1,
            examen = "BAC",
            chapitre = "Algèbre",
            enonce = "Quelle est la valeur de 2 + 3 × 4 ?",
            optionA = "20",
            optionB = "14",
            optionC = "24",
            optionD = "10",
            reponseCorrecte = "B",
            explication = "La multiplication est prioritaire : 3 × 4 = 12, puis 2 + 12 = 14."
        ),
        Question(
            id = 2,
            matiereId = 1,
            examen = "BAC",
            chapitre = "Algèbre",
            enonce = "Quelle est la solution de x + 5 = 12 ?",
            optionA = "5",
            optionB = "6",
            optionC = "7",
            optionD = "8",
            reponseCorrecte = "C",
            explication = "On soustrait 5 des deux côtés : x = 12 - 5 = 7."
        ),
        Question(
            id = 3,
            matiereId = 2,
            examen = "BAC",
            chapitre = "Mécanique",
            enonce = "Quelle est l'unité SI de la vitesse ?",
            optionA = "Newton",
            optionB = "Joule",
            optionC = "m/s",
            optionD = "Watt",
            reponseCorrecte = "C",
            explication = "La vitesse s'exprime en mètres par seconde (m/s)."
        ),
        Question(
            id = 4,
            matiereId = 3,
            examen = "BAC",
            chapitre = "Chimie générale",
            enonce = "Quel est le symbole chimique de l'oxygène ?",
            optionA = "O",
            optionB = "Ox",
            optionC = "Og",
            optionD = "C",
            reponseCorrecte = "A",
            explication = "Le symbole chimique de l'oxygène est O."
        ),
        Question(
            id = 5,
            matiereId = 4,
            examen = "BAC",
            chapitre = "Grammaire",
            enonce = "Quel est le pluriel de « cheval » ?",
            optionA = "Chevals",
            optionB = "Chevaux",
            optionC = "Chevals",
            optionD = "Chevaus",
            reponseCorrecte = "B",
            explication = "Le pluriel de cheval est chevaux."
        ),
        Question(
            id = 6,
            matiereId = 5,
            examen = "BAC",
            chapitre = "Vocabulary",
            enonce = "What is the opposite of « big »?",
            optionA = "Small",
            optionB = "Long",
            optionC = "Tall",
            optionD = "Large",
            reponseCorrecte = "A",
            explication = "The opposite of big is small."
        ),
        Question(
            id = 7,
            matiereId = 6,
            examen = "BEPC",
            chapitre = "Algèbre",
            enonce = "Combien font 15 + 7 ?",
            optionA = "20",
            optionB = "21",
            optionC = "22",
            optionD = "23",
            reponseCorrecte = "C",
            explication = "15 + 7 = 22."
        ),
        Question(
            id = 8,
            matiereId = 7,
            examen = "BEPC",
            chapitre = "Grammaire",
            enonce = "Quel est le contraire de « rapide » ?",
            optionA = "Vif",
            optionB = "Lent",
            optionC = "Fort",
            optionD = "Grand",
            reponseCorrecte = "B",
            explication = "Le contraire de rapide est lent."
        ),
        Question(
            id = 9,
            matiereId = 8,
            examen = "BEPC",
            chapitre = "Électricité",
            enonce = "Quelle est l'unité de l'intensité électrique ?",
            optionA = "Volt",
            optionB = "Ohm",
            optionC = "Ampère",
            optionD = "Watt",
            reponseCorrecte = "C",
            explication = "L'intensité électrique se mesure en ampères (A)."
        ),
        Question(
            id = 10,
            matiereId = 9,
            examen = "BEPC",
            chapitre = "Vocabulary",
            enonce = "What is the opposite of « hot »?",
            optionA = "Warm",
            optionB = "Cold",
            optionC = "Dry",
            optionD = "High",
            reponseCorrecte = "B",
            explication = "The opposite of hot is cold."
        )
    )

    val fiches = listOf(
        Fiche(
            id = 1,
            matiereId = 1,
            titre = "Priorités de calcul",
            contenu = "Les multiplications et divisions sont prioritaires sur les additions et soustractions.",
            chapitre = "Algèbre"
        ),
        Fiche(
            id = 2,
            matiereId = 2,
            titre = "Unités de vitesse",
            contenu = "Dans le système international, la vitesse s'exprime en mètres par seconde.",
            chapitre = "Mécanique"
        ),
        Fiche(
            id = 3,
            matiereId = 3,
            titre = "Symboles chimiques",
            contenu = "Chaque élément chimique possède un symbole. Par exemple, O représente l'oxygène.",
            chapitre = "Chimie générale"
        ),
        Fiche(
            id = 4,
            matiereId = 4,
            titre = "Les règles du pluriel",
            contenu = "Certains noms prennent une forme particulière au pluriel, comme cheval qui devient chevaux.",
            chapitre = "Grammaire"
        ),
        Fiche(
            id = 5,
            matiereId = 5,
            titre = "Basic vocabulary",
            contenu = "Learn common English opposites such as big/small and hot/cold.",
            chapitre = "Vocabulary"
        )
    )
}
