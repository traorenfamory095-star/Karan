package com.example.karan.data.local

import com.example.karan.models.Fiche
import com.example.karan.models.Matiere
import com.example.karan.models.Question

object SeedData {

    // Les identifiants sont générés automatiquement (1, 2, 3...)
    // pour éviter les doublons quand on ajoute des questions.
    // Ces compteurs doivent rester AVANT les listes.
    private var prochainIdQuestion = 0L
    private var prochainIdFiche = 0L

    private fun q(
        matiereId: Long,
        examen: String,
        chapitre: String,
        enonce: String,
        a: String,
        b: String,
        c: String,
        d: String,
        bonne: String,
        explication: String
    ) = Question(
        id = ++prochainIdQuestion,
        matiereId = matiereId,
        examen = examen,
        chapitre = chapitre,
        enonce = enonce,
        optionA = a,
        optionB = b,
        optionC = c,
        optionD = d,
        reponseCorrecte = bonne,
        explication = explication
    )

    private fun f(
        matiereId: Long,
        chapitre: String,
        titre: String,
        contenu: String
    ) = Fiche(
        id = ++prochainIdFiche,
        matiereId = matiereId,
        titre = titre,
        contenu = contenu,
        chapitre = chapitre
    )

    val matieres = listOf(
        Matiere(id = 1, nom = "Mathématiques", examen = "BAC", nombreChapitres = 5),
        Matiere(id = 2, nom = "Physique", examen = "BAC", nombreChapitres = 4),
        Matiere(id = 3, nom = "Chimie", examen = "BAC", nombreChapitres = 4),
        Matiere(id = 4, nom = "Français", examen = "BAC", nombreChapitres = 5),
        Matiere(id = 5, nom = "Anglais", examen = "BAC", nombreChapitres = 4),
        Matiere(id = 6, nom = "Mathématiques", examen = "BEPC", nombreChapitres = 5),
        Matiere(id = 7, nom = "Français", examen = "BEPC", nombreChapitres = 5),
        Matiere(id = 8, nom = "Physique", examen = "BEPC", nombreChapitres = 4),
        Matiere(id = 9, nom = "Anglais", examen = "BEPC", nombreChapitres = 4)
    )

    val questions = listOf(

        // ============================================================
        // BAC - MATHÉMATIQUES (matiereId = 1)
        // ============================================================
        q(1, "BAC", "Algèbre", "Quelle est la valeur de 2 + 3 × 4 ?",
            "20", "14", "24", "10", "B",
            "La multiplication est prioritaire : 3 × 4 = 12, puis 2 + 12 = 14."),
        q(1, "BAC", "Algèbre", "Quelle est la solution de x + 5 = 12 ?",
            "5", "6", "7", "8", "C",
            "On soustrait 5 des deux côtés : x = 12 - 5 = 7."),
        q(1, "BAC", "Analyse", "Quelle est la dérivée de f(x) = x² ?",
            "2x", "x", "x³", "2", "A",
            "La dérivée de x^n est n·x^(n-1). Ici n = 2, donc f'(x) = 2x."),
        q(1, "BAC", "Algèbre", "Quelles sont les solutions de l'équation x² - 9 = 0 ?",
            "x = 3 uniquement", "x = -3 uniquement", "x = 9", "x = 3 ou x = -3", "D",
            "x² = 9 donc x = 3 ou x = -3."),
        q(1, "BAC", "Analyse", "Quelle est la limite de 1/x quand x tend vers +∞ ?",
            "+∞", "0", "1", "-∞", "B",
            "Quand x devient très grand, 1/x devient très petit et tend vers 0."),
        q(1, "BAC", "Analyse", "Quelle est une primitive de f(x) = 2x ?",
            "2x²", "x", "x² + C", "2", "C",
            "La dérivée de x² est 2x, donc x² + C est une primitive de 2x."),
        q(1, "BAC", "Nombres complexes", "Quelle est la valeur de i² ?",
            "1", "-1", "i", "0", "B",
            "Par définition du nombre complexe i, on a i² = -1."),
        q(1, "BAC", "Suites", "Une suite arithmétique a pour premier terme u₀ = 2 et pour raison 3. Quelle est la valeur de u₅ ?",
            "15", "16", "17", "18", "C",
            "u₅ = u₀ + 5 × raison = 2 + 5 × 3 = 17."),

        // ============================================================
        // BAC - PHYSIQUE (matiereId = 2)
        // ============================================================
        q(2, "BAC", "Mécanique", "Quelle est l'unité SI de la vitesse ?",
            "Newton", "Joule", "m/s", "Watt", "C",
            "La vitesse s'exprime en mètres par seconde (m/s)."),
        q(2, "BAC", "Mécanique", "Que dit la deuxième loi de Newton ?",
            "F = m / a", "ΣF = m × a", "F = m × v", "F = a / m", "B",
            "La somme des forces appliquées à un corps est égale à sa masse multipliée par son accélération."),
        q(2, "BAC", "Mécanique", "Quelle est l'unité SI de la force ?",
            "Newton", "Joule", "Pascal", "Watt", "A",
            "La force se mesure en newtons (N)."),
        q(2, "BAC", "Électricité", "Quelle est l'expression de la loi d'Ohm ?",
            "U = R / I", "U = I / R", "R = U × I", "U = R × I", "D",
            "La tension aux bornes d'un conducteur ohmique est U = R × I."),
        q(2, "BAC", "Mécanique", "Quelle est la valeur approximative de l'intensité de la pesanteur g sur Terre ?",
            "1,6 m/s²", "6,7 m/s²", "9,8 m/s²", "12 m/s²", "C",
            "Sur Terre, g est environ égal à 9,8 m/s²."),
        q(2, "BAC", "Énergie", "Quelle est l'unité SI de la puissance ?",
            "Joule", "Watt", "Newton", "Volt", "B",
            "La puissance s'exprime en watts (W)."),
        q(2, "BAC", "Optique", "Quelle est la vitesse approximative de la lumière dans le vide ?",
            "3 × 10⁸ m/s", "3 × 10⁶ m/s", "3 × 10³ m/s", "340 m/s", "A",
            "La lumière se propage dans le vide à environ 300 000 km/s, soit 3 × 10⁸ m/s."),
        q(2, "BAC", "Énergie", "Quelle est l'expression de l'énergie cinétique d'un corps de masse m et de vitesse v ?",
            "m × v", "m × g × h", "m × v²", "½ × m × v²", "D",
            "L'énergie cinétique vaut Ec = ½ m v²."),

        // ============================================================
        // BAC - CHIMIE (matiereId = 3)
        // ============================================================
        q(3, "BAC", "Chimie générale", "Quel est le symbole chimique de l'oxygène ?",
            "O", "Ox", "Og", "C", "A",
            "Le symbole chimique de l'oxygène est O."),
        q(3, "BAC", "Acides et bases", "Quel est le pH de l'eau pure à 25 °C ?",
            "0", "14", "7", "1", "C",
            "L'eau pure est neutre : son pH est égal à 7 à 25 °C."),
        q(3, "BAC", "Chimie générale", "Quelle est la formule chimique de l'eau ?",
            "HO", "H₂O", "H₂O₂", "OH₂O", "B",
            "Une molécule d'eau contient deux atomes d'hydrogène et un atome d'oxygène : H₂O."),
        q(3, "BAC", "Chimie organique", "Quelle est la formule chimique du méthane ?",
            "C₂H₆", "CH₃OH", "CO₂", "CH₄", "D",
            "Le méthane est l'alcane le plus simple : un carbone et quatre hydrogènes, CH₄."),
        q(3, "BAC", "Solutions", "Quelle est la masse molaire de l'eau H₂O (H = 1 g/mol ; O = 16 g/mol) ?",
            "18 g/mol", "16 g/mol", "20 g/mol", "10 g/mol", "A",
            "M(H₂O) = 2 × 1 + 16 = 18 g/mol."),
        q(3, "BAC", "Solutions", "Quelle est la valeur approximative du nombre d'Avogadro ?",
            "6,02 × 10²³ mol⁻¹", "3 × 10⁸ mol⁻¹", "1,6 × 10⁻¹⁹ mol⁻¹", "9,8 mol⁻¹", "A",
            "Une mole contient environ 6,02 × 10²³ entités (nombre d'Avogadro)."),
        q(3, "BAC", "Acides et bases", "Une solution de pH égal à 3 est :",
            "Basique", "Neutre", "Acide", "Saline", "C",
            "Un pH inférieur à 7 correspond à une solution acide."),
        q(3, "BAC", "Chimie organique", "Comment s'appelle l'alcane à deux atomes de carbone ?",
            "Éthane", "Éthène", "Éthyne", "Méthane", "A",
            "L'éthane (C₂H₆) est l'alcane à deux atomes de carbone."),

        // ============================================================
        // BAC - FRANÇAIS (matiereId = 4)
        // ============================================================
        q(4, "BAC", "Grammaire", "Quel est le pluriel de « cheval » ?",
            "Chevals", "Chevaux", "Chevalx", "Chevaus", "B",
            "Le pluriel de cheval est chevaux."),
        q(4, "BAC", "Grammaire", "Quelle est la nature du mot « rapidement » ?",
            "Nom", "Adjectif", "Adverbe", "Verbe", "C",
            "« Rapidement » est un adverbe : il modifie le sens d'un verbe et ne s'accorde pas."),
        q(4, "BAC", "Conjugaison", "Quelle est la forme du verbe chanter au passé simple, à la 3e personne du singulier ?",
            "Il chanta", "Il chantait", "Il chantera", "Il chanterait", "A",
            "Au passé simple, les verbes du 1er groupe se terminent par -a à la 3e personne du singulier."),
        q(4, "BAC", "Littérature", "Qui est l'auteur du roman « Les Misérables » ?",
            "Émile Zola", "Honoré de Balzac", "Molière", "Victor Hugo", "D",
            "« Les Misérables » a été publié en 1862 par Victor Hugo."),
        q(4, "BAC", "Littérature", "Quelle figure de style trouve-t-on dans « Cet homme est un lion » ?",
            "Une comparaison", "Une métaphore", "Une hyperbole", "Une antithèse", "B",
            "Il n'y a pas d'outil de comparaison (comme, tel...) : c'est une métaphore."),
        q(4, "BAC", "Vocabulaire", "Quel est un synonyme de « joyeux » ?",
            "Triste", "Morose", "Gai", "Sombre", "C",
            "« Gai » a un sens proche de « joyeux »."),
        q(4, "BAC", "Grammaire", "Complète : « Les pommes que j'ai ... (manger) étaient bonnes. »",
            "Mangées", "Mangé", "Mangés", "Mangeait", "A",
            "Avec l'auxiliaire avoir, le participe passé s'accorde avec le COD placé avant : « pommes » (féminin pluriel)."),
        q(4, "BAC", "Littérature", "Qui est l'auteur du roman « L'Enfant noir » ?",
            "Cheikh Hamidou Kane", "Ahmadou Kourouma", "Léopold Sédar Senghor", "Camara Laye", "D",
            "« L'Enfant noir » (1953) est un roman autobiographique de l'écrivain guinéen Camara Laye."),

        // ============================================================
        // BAC - ANGLAIS (matiereId = 5)
        // ============================================================
        q(5, "BAC", "Vocabulary", "What is the opposite of « big »?",
            "Small", "Long", "Tall", "Large", "A",
            "The opposite of big is small."),
        q(5, "BAC", "Grammar", "What is the past tense of « go »?",
            "Goed", "Gone", "Went", "Going", "C",
            "« Go » is an irregular verb: go - went - gone."),
        q(5, "BAC", "Grammar", "Choose the correct sentence.",
            "She are a student.", "She is a student.", "She am a student.", "She be a student.", "B",
            "With « she », we use « is »."),
        q(5, "BAC", "Grammar", "What is the plural of « child »?",
            "Childs", "Childes", "Childrens", "Children", "D",
            "« Child » has an irregular plural: children."),
        q(5, "BAC", "Tenses", "Complete: « I have lived here ___ 2010. »",
            "Since", "For", "From", "During", "A",
            "We use « since » with a starting point in time (2010) and « for » with a duration."),
        q(5, "BAC", "Grammar", "What is the comparative of « good »?",
            "Gooder", "More good", "Better", "Best", "C",
            "« Good » is irregular: good - better - best."),
        q(5, "BAC", "Vocabulary", "What is a « library »?",
            "A place where you buy books", "A place where you borrow books", "A school", "A bookshop", "B",
            "A library is a place where you can read and borrow books."),
        q(5, "BAC", "Grammar", "Complete: « If it rains, I ___ stay at home. »",
            "Would", "Am", "Did", "Will", "D",
            "First conditional: if + present simple, will + verb."),

        // ============================================================
        // BEPC - MATHÉMATIQUES (matiereId = 6)
        // ============================================================
        q(6, "BEPC", "Algèbre", "Combien font 15 + 7 ?",
            "20", "21", "22", "23", "C",
            "15 + 7 = 22."),
        q(6, "BEPC", "Géométrie", "Quelle est l'aire d'un rectangle de longueur 5 cm et de largeur 3 cm ?",
            "15 cm²", "8 cm²", "16 cm²", "30 cm²", "A",
            "Aire = longueur × largeur = 5 × 3 = 15 cm²."),
        q(6, "BEPC", "Géométrie", "Quelle est la somme des angles d'un triangle ?",
            "90°", "180°", "270°", "360°", "B",
            "La somme des angles d'un triangle est toujours égale à 180°."),
        q(6, "BEPC", "Géométrie", "Quel est le périmètre d'un carré de côté 4 cm ?",
            "8 cm", "12 cm", "14 cm", "16 cm", "D",
            "Périmètre = 4 × côté = 4 × 4 = 16 cm."),
        q(6, "BEPC", "Géométrie", "Dans un triangle rectangle, les côtés de l'angle droit mesurent 3 cm et 4 cm. Combien mesure l'hypoténuse ?",
            "4 cm", "5 cm", "6 cm", "7 cm", "B",
            "Théorème de Pythagore : 3² + 4² = 9 + 16 = 25, donc l'hypoténuse vaut 5 cm."),
        q(6, "BEPC", "Algèbre", "Quelle est la solution de 2x = 10 ?",
            "x = 5", "x = 8", "x = 12", "x = 20", "A",
            "On divise les deux côtés par 2 : x = 10 / 2 = 5."),
        q(6, "BEPC", "Statistiques", "Quelle est la moyenne des nombres 4, 6 et 8 ?",
            "5", "7", "6", "18", "C",
            "Moyenne = (4 + 6 + 8) / 3 = 18 / 3 = 6."),
        q(6, "BEPC", "Algèbre", "Combien vaut 25 % de 80 ?",
            "10", "15", "25", "20", "D",
            "25 % = un quart. 80 / 4 = 20."),

        // ============================================================
        // BEPC - FRANÇAIS (matiereId = 7)
        // ============================================================
        q(7, "BEPC", "Grammaire", "Quel est le contraire de « rapide » ?",
            "Vif", "Lent", "Fort", "Grand", "B",
            "Le contraire de rapide est lent."),
        q(7, "BEPC", "Grammaire", "Quel est le pluriel de « journal » ?",
            "Journals", "Journaus", "Journaux", "Journales", "C",
            "Les noms en -al font généralement leur pluriel en -aux : journal → journaux."),
        q(7, "BEPC", "Conjugaison", "Comment conjugue-t-on le verbe « être » au présent avec « nous » ?",
            "Nous sommes", "Nous êtes", "Nous sont", "Nous étions", "A",
            "Au présent : je suis, tu es, il est, nous sommes, vous êtes, ils sont."),
        q(7, "BEPC", "Grammaire", "Quelle est la nature du mot « beau » dans « un beau jardin » ?",
            "Nom", "Verbe", "Adverbe", "Adjectif", "D",
            "« Beau » qualifie le nom « jardin » : c'est un adjectif qualificatif."),
        q(7, "BEPC", "Vocabulaire", "Quel est un synonyme de « commencer » ?",
            "Finir", "Débuter", "Arrêter", "Cesser", "B",
            "« Débuter » a le même sens que « commencer »."),
        q(7, "BEPC", "Grammaire", "Quelle phrase est correctement écrite ?",
            "Elle est allé au marché.", "Elle a allé au marché.", "Elle est allée au marché.", "Elle est aller au marché.", "C",
            "Le verbe aller se conjugue avec être : le participe passé s'accorde avec le sujet (elle → allée)."),
        q(7, "BEPC", "Vocabulaire", "Quel est le féminin de « acteur » ?",
            "Actrice", "Acteure", "Acteuse", "Actrisse", "A",
            "Le féminin de acteur est actrice."),
        q(7, "BEPC", "Conjugaison", "À quel temps est conjugué le verbe dans « Il mangeait » ?",
            "Présent", "Passé simple", "Futur simple", "Imparfait", "D",
            "La terminaison -ait à la 3e personne du singulier indique l'imparfait."),

        // ============================================================
        // BEPC - PHYSIQUE (matiereId = 8)
        // ============================================================
        q(8, "BEPC", "Électricité", "Quelle est l'unité de l'intensité électrique ?",
            "Volt", "Ohm", "Ampère", "Watt", "C",
            "L'intensité électrique se mesure en ampères (A)."),
        q(8, "BEPC", "Électricité", "Quelle est l'unité de la tension électrique ?",
            "Volt", "Ampère", "Ohm", "Newton", "A",
            "La tension électrique se mesure en volts (V)."),
        q(8, "BEPC", "Électricité", "Quel appareil permet de mesurer l'intensité du courant ?",
            "Le voltmètre", "L'ampèremètre", "L'ohmmètre", "Le thermomètre", "B",
            "L'ampèremètre se branche en série et mesure l'intensité."),
        q(8, "BEPC", "Électricité", "Quelle est l'unité de la résistance électrique ?",
            "Watt", "Volt", "Ampère", "Ohm", "D",
            "La résistance se mesure en ohms (Ω)."),
        q(8, "BEPC", "Électricité", "Dans un circuit en série, l'intensité du courant est :",
            "Nulle", "Doublée à chaque dipôle", "La même en tout point", "Divisée à chaque dipôle", "C",
            "Dans un circuit en série, il n'y a qu'une seule boucle : l'intensité est la même partout."),
        q(8, "BEPC", "Mécanique", "Quelle est l'unité SI de la masse ?",
            "Kilogramme", "Newton", "Litre", "Gramme par litre", "A",
            "L'unité SI de la masse est le kilogramme (kg)."),
        q(8, "BEPC", "Optique", "Comment la lumière se propage-t-elle dans un milieu transparent homogène ?",
            "En zigzag", "En ligne droite", "En cercle", "En spirale", "B",
            "Dans un milieu transparent et homogène, la lumière se propage en ligne droite."),
        q(8, "BEPC", "Électricité", "Comment appelle-t-on un matériau qui ne laisse pas passer le courant électrique ?",
            "Un conducteur", "Un métal", "Un générateur", "Un isolant", "D",
            "Le plastique, le verre et le bois sont des isolants électriques."),

        // ============================================================
        // BEPC - ANGLAIS (matiereId = 9)
        // ============================================================
        q(9, "BEPC", "Vocabulary", "What is the opposite of « hot »?",
            "Warm", "Cold", "Dry", "High", "B",
            "The opposite of hot is cold."),
        q(9, "BEPC", "Grammar", "Complete: « I ___ a book now. »",
            "Am reading", "Reads", "Reading", "Read", "A",
            "For an action happening now, we use the present continuous: am + verb-ing."),
        q(9, "BEPC", "Grammar", "What is the plural of « man »?",
            "Mans", "Mens", "Men", "Manes", "C",
            "« Man » has an irregular plural: men."),
        q(9, "BEPC", "Grammar", "Complete: « How ___ are you? I am 15. »",
            "Long", "Tall", "Much", "Old", "D",
            "We ask « How old are you? » to know someone's age."),
        q(9, "BEPC", "Vocabulary", "What colour is the sky on a clear day?",
            "Red", "Blue", "Black", "Green", "B",
            "On a clear day, the sky is blue."),
        q(9, "BEPC", "Grammar", "What is the past tense of « see »?",
            "Saw", "Seen", "Seed", "Sees", "A",
            "« See » is irregular: see - saw - seen."),
        q(9, "BEPC", "Vocabulary", "Which day comes after Tuesday?",
            "Monday", "Thursday", "Wednesday", "Friday", "C",
            "The days are: Monday, Tuesday, Wednesday, Thursday..."),
        q(9, "BEPC", "Grammar", "Complete: « She ___ to school every day. »",
            "Go", "Goes", "Going", "Gone", "B",
            "With he / she / it, the present simple takes an -s: she goes.")
    )

    val fiches = listOf(

        // ---------------- BAC - Mathématiques ----------------
        f(1, "Algèbre", "Priorités de calcul",
            "Les multiplications et divisions sont prioritaires sur les additions et soustractions. Les parenthèses passent avant tout."),
        f(1, "Analyse", "Dérivées usuelles",
            "(x^n)' = n·x^(n-1) ; (sin x)' = cos x ; (cos x)' = -sin x ; (ln x)' = 1/x ; (e^x)' = e^x."),
        f(1, "Nombres complexes", "Les nombres complexes",
            "i² = -1. Un complexe s'écrit z = a + ib. Son module est |z| = √(a² + b²). Son conjugué est a - ib."),

        // ---------------- BAC - Physique ----------------
        f(2, "Mécanique", "Unités de vitesse",
            "Dans le système international, la vitesse s'exprime en mètres par seconde (m/s). 1 m/s = 3,6 km/h."),
        f(2, "Mécanique", "Les lois de Newton",
            "1re loi : un corps libre garde un mouvement rectiligne uniforme. 2e loi : ΣF = m·a. 3e loi : action et réaction sont égales et opposées."),
        f(2, "Électricité", "Loi d'Ohm et puissance",
            "Loi d'Ohm : U = R·I (U en volts, R en ohms, I en ampères). Puissance électrique : P = U·I (en watts)."),

        // ---------------- BAC - Chimie ----------------
        f(3, "Chimie générale", "Symboles chimiques",
            "Chaque élément chimique possède un symbole. Par exemple, O représente l'oxygène, H l'hydrogène et C le carbone."),
        f(3, "Acides et bases", "pH et acidité",
            "pH < 7 : solution acide. pH = 7 : solution neutre. pH > 7 : solution basique. L'eau pure a un pH de 7 à 25 °C."),
        f(3, "Solutions", "La mole",
            "Une mole contient 6,02 × 10²³ entités. Quantité de matière : n = m / M (m en grammes, M en g/mol)."),

        // ---------------- BAC - Français ----------------
        f(4, "Grammaire", "Les règles du pluriel",
            "Certains noms prennent une forme particulière au pluriel : cheval → chevaux, journal → journaux, bijou → bijoux."),
        f(4, "Littérature", "Figures de style",
            "Comparaison : rapprochement avec un outil (comme). Métaphore : rapprochement sans outil. Hyperbole : exagération. Antithèse : opposition de deux idées."),
        f(4, "Grammaire", "Accord du participe passé",
            "Avec être : accord avec le sujet. Avec avoir : accord avec le COD seulement s'il est placé avant le verbe."),

        // ---------------- BAC - Anglais ----------------
        f(5, "Vocabulary", "Basic vocabulary",
            "Learn common English opposites such as big/small, hot/cold, fast/slow and old/young."),
        f(5, "Grammar", "Irregular verbs",
            "go - went - gone ; see - saw - seen ; have - had - had ; do - did - done ; take - took - taken."),
        f(5, "Tenses", "Present perfect",
            "Form: have/has + past participle. Use « since » with a starting point and « for » with a duration: I have lived here since 2010."),

        // ---------------- BEPC - Mathématiques ----------------
        f(6, "Géométrie", "Théorème de Pythagore",
            "Dans un triangle rectangle, le carré de l'hypoténuse est égal à la somme des carrés des deux autres côtés : a² + b² = c²."),
        f(6, "Géométrie", "Périmètres et aires",
            "Carré : P = 4c, A = c². Rectangle : P = 2(L + l), A = L × l. Triangle : A = (base × hauteur) / 2."),

        // ---------------- BEPC - Français ----------------
        f(7, "Grammaire", "Pluriel des noms en -al",
            "La plupart des noms en -al font leur pluriel en -aux : cheval → chevaux, journal → journaux. Exceptions : bal, carnaval, festival → -als."),
        f(7, "Conjugaison", "Les temps de l'indicatif",
            "Présent : il mange. Imparfait : il mangeait. Passé simple : il mangea. Futur simple : il mangera."),

        // ---------------- BEPC - Physique ----------------
        f(8, "Électricité", "Le circuit électrique",
            "En série : une seule boucle, l'intensité est la même partout. En dérivation : plusieurs branches, l'intensité se partage."),
        f(8, "Électricité", "Grandeurs et unités",
            "Tension : volt (V). Intensité : ampère (A). Résistance : ohm (Ω). Masse : kilogramme (kg)."),

        // ---------------- BEPC - Anglais ----------------
        f(9, "Grammar", "Present simple et continuous",
            "Present simple : habitudes (she goes to school every day). Present continuous : action en cours (I am reading now)."),
        f(9, "Grammar", "Irregular plurals",
            "man → men ; woman → women ; child → children ; foot → feet ; tooth → teeth ; mouse → mice.")
    )
}