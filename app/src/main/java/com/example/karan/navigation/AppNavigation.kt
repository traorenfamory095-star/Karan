package com.example.karan.navigation

/*
 * ============================================================
 * APP NAVIGATION — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : N'famory Traore
 *
 * 🎯 OBJECTIF :
 * AppNavigation centralise la navigation entre les écrans
 * principaux de l'application Révision BAC / BEPC.
 *
 * L'objectif est de garder une navigation simple et adaptée
 * à la contrainte du MVP : maximum 3 à 4 écrans principaux.
 *
 * ------------------------------------------------------------
 * 📱 ÉCRANS PRINCIPAUX :
 *
 * 1. AccueilScreen
 *    → écran d'accueil / tableau de bord.
 *
 * 2. MatieresScreen
 *    → liste des matières et accès aux contenus.
 *
 * 3. QuizScreen
 *    → déroulement du quiz.
 *
 * 4. ResultatScreen
 *    → résultat et correction du quiz.
 *
 * Les fonctionnalités suivantes ne doivent pas créer
 * automatiquement de nouveaux écrans principaux :
 *
 * - progression ;
 * - historique ;
 * - erreurs à revoir ;
 * - fiches de révision.
 *
 * Elles doivent être intégrées intelligemment dans les
 * écrans existants afin de respecter la limite du MVP.
 *
 * ------------------------------------------------------------
 * 🔄 PARCOURS PRINCIPAL :
 *
 * Accueil
 *    ↓
 * Matières
 *    ↓
 * Quiz
 *    ↓
 * Résultat
 *    ↓
 * Accueil / Matières
 *
 * Exemple :
 *
 * Accueil
 *   → choisir BAC
 *   → choisir une matière
 *   → choisir le nombre de questions
 *   → commencer
 *   → répondre aux questions
 *   → voir le résultat
 *
 * ------------------------------------------------------------
 * 🧭 NAVIGATION COMPOSE :
 *
 * Si le projet utilise Navigation Compose, cette classe devra
 * centraliser le NavHost et les différentes destinations.
 *
 * Les routes devront être clairement définies afin d'éviter
 * les chaînes de caractères dispersées dans les Screens.
 *
 * Exemple de principe :
 *
 * accueil
 * matieres
 * quiz
 * resultat
 *
 * ------------------------------------------------------------
 * 📦 PARAMÈTRES DE NAVIGATION :
 *
 * Certains écrans auront besoin d'informations pour savoir
 * quoi afficher.
 *
 * Exemple :
 *
 * QuizScreen
 *    ← id de la matière
 *    ← type d'examen
 *    ← nombre de questions
 *
 * ResultatScreen
 *    ← informations de la session terminée
 *
 * Les paramètres doivent être transmis proprement via la
 * navigation plutôt que de stocker inutilement ces données
 * dans les Composables.
 *
 * ------------------------------------------------------------
 * 🚫 À NE PAS FAIRE :
 *
 * ❌ Pas de logique métier.
 * ❌ Pas de calcul de score.
 * ❌ Pas de requêtes Room.
 * ❌ Pas d'accès direct aux DAO.
 * ❌ Pas de logique de validation du quiz.
 *
 * La navigation doit uniquement décider quel écran afficher
 * et transmettre les paramètres nécessaires.
 *
 * ------------------------------------------------------------
 * 🧠 RESPONSABILITÉ INTÉGRATION :
 *
 * Ce fichier doit être facilement compréhensible par toute
 * l'équipe.
 *
 * Les noms de routes doivent être cohérents et ne doivent
 * pas être modifiés sans vérifier les appels effectués
 * depuis les différents Screens.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier le parcours complet :
 *
 * Accueil
 *   ↓
 * Matières
 *   ↓
 * Quiz
 *   ↓
 * Résultat
 *
 * Vérifier également :
 *
 * - retour en arrière ;
 * - paramètres correctement transmis ;
 * - absence de route inconnue ;
 * - aucun écran supplémentaire inutile.
 *
 * ============================================================
 */

// TODO : définir les routes de navigation
// TODO : créer le NavHost
// TODO : déclarer les quatre destinations principales
// TODO : connecter les callbacks de navigation des Screens
// TODO : gérer les paramètres nécessaires au QuizScreen
// TODO : gérer les paramètres nécessaires au ResultatScreen