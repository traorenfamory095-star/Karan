package com.example.karan.viewmodels

/*
 * ============================================================
 * ACCUEIL VIEWMODEL — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Mamadou Alpha Diallo
 *
 * 🎯 OBJECTIF :
 * AccueilViewModel contient la logique nécessaire à
 * AccueilScreen.
 *
 * Il récupère les données depuis le Repository et expose
 * un état observable à l'interface avec StateFlow.
 *
 * ------------------------------------------------------------
 * 🔄 FLUX :
 *
 * AccueilScreen
 *      ↓
 * AccueilViewModel
 *      ↓
 * RevisionRepository
 *      ↓
 * DAO
 *      ↓
 * Room
 *
 * Le Screen ne communique jamais directement avec Room.
 *
 * ------------------------------------------------------------
 * 📊 DONNÉES À GÉRER :
 *
 * Le ViewModel devra notamment pouvoir fournir :
 *
 * - l'examen sélectionné : BAC ou BEPC ;
 * - la progression générale ;
 * - les dernières sessions ;
 * - la dernière matière travaillée ;
 * - le nombre d'erreurs à revoir ;
 * - l'état de chargement ;
 * - les éventuelles erreurs.
 *
 * ------------------------------------------------------------
 * 🌊 STATEFLOW :
 *
 * Utiliser StateFlow pour exposer l'état de l'écran.
 *
 * Exemple de principe :
 *
 * UI State
 *   ├── loading
 *   ├── examen sélectionné
 *   ├── progression
 *   ├── dernière session
 *   ├── erreurs
 *   └── erreur éventuelle
 *
 * Le Screen observe cet état et se redessine lorsque les
 * données changent.
 *
 * ------------------------------------------------------------
 * 🧠 LOGIQUE MÉTIER :
 *
 * Le ViewModel peut :
 *
 * - sélectionner BAC ou BEPC ;
 * - demander les données au Repository ;
 * - calculer une progression à partir des sessions ;
 * - déterminer les informations à afficher sur le dashboard ;
 * - gérer les états de chargement et d'erreur.
 *
 * ------------------------------------------------------------
 * ❌ À NE PAS FAIRE :
 *
 * ❌ Ne pas accéder directement aux DAO.
 * ❌ Ne pas accéder directement à Room.
 * ❌ Ne pas écrire de code Compose.
 * ❌ Ne pas gérer la navigation directement.
 *
 * Le ViewModel dépend uniquement de l'interface :
 *
 * RevisionRepository
 *
 * et non de :
 *
 * RevisionRepositoryImpl
 *
 * ------------------------------------------------------------
 * ⚠️ ÉTATS À PRÉVOIR :
 *
 * 1. Chargement
 * 2. Données disponibles
 * 3. Aucune session
 * 4. Aucune erreur à revoir
 * 5. Erreur de récupération des données
 *
 * Exemple d'état vide :
 *
 * "Aucune donnée pour le moment"
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - sélection BAC ;
 * - sélection BEPC ;
 * - récupération des sessions ;
 * - calcul de la progression ;
 * - affichage de l'état vide ;
 * - gestion des erreurs ;
 * - mise à jour du StateFlow.
 *
 * ============================================================
 */

// TODO : créer l'UI State de l'écran d'accueil
// TODO : créer AccueilViewModel
// TODO : injecter RevisionRepository
// TODO : créer le StateFlow de l'écran
// TODO : gérer la sélection BAC / BEPC
// TODO : récupérer les dernières sessions
// TODO : récupérer les informations de progression
// TODO : gérer les erreurs à revoir
// TODO : gérer les états loading / success / error / empty
