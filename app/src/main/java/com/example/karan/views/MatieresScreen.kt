package com.example.karan.views

/*
 * ============================================================
 * MATIERES SCREEN — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Amadou Bah
 *
 * 🎯 OBJECTIF :
 * MatieresScreen affiche les matières disponibles pour
 * l'examen sélectionné et permet à l'élève de choisir
 * ce qu'il souhaite réviser.
 *
 * ------------------------------------------------------------
 * 📱 RÔLE DANS LE MVP :
 *
 * Cet écran sert de LISTE principale du parcours.
 *
 * Il doit permettre de :
 *
 * - voir les matières disponibles ;
 * - sélectionner une matière ;
 * - accéder aux fiches de révision ;
 * - démarrer un quiz.
 *
 * ------------------------------------------------------------
 * 🔄 PARCOURS :
 *
 * AccueilScreen
 *      ↓
 * MatieresScreen
 *      ↓
 * QuizScreen
 *      ↓
 * ResultatScreen
 *
 * ------------------------------------------------------------
 * 📋 CONTENU :
 *
 * Pour chaque matière, afficher au minimum :
 *
 * - nom de la matière ;
 * - éventuellement une icône ;
 * - éventuellement la progression ;
 * - bouton/action pour commencer.
 *
 * Les données doivent provenir du ViewModel.
 *
 * ------------------------------------------------------------
 * 📝 CHOIX DU QUIZ :
 *
 * L'élève doit pouvoir choisir :
 *
 * - la matière ;
 * - le nombre de questions.
 *
 * Le nombre de questions pourra être sélectionné avant
 * d'entrer dans QuizScreen.
 *
 * Exemple :
 *
 * "Combien de questions ?"
 *
 * ○ 5
 * ○ 10
 * ○ 20
 *
 * La validation du choix doit être claire.
 *
 * ------------------------------------------------------------
 * 📖 FICHES DE RÉVISION :
 *
 * Les fiches peuvent être accessibles depuis cet écran
 * sans créer un écran principal supplémentaire dans le MVP.
 *
 * L'objectif est de conserver une navigation limitée
 * à 3–4 écrans principaux.
 *
 * ------------------------------------------------------------
 * 🌐 OFFLINE-FIRST :
 *
 * Les matières doivent être récupérées depuis le Repository
 * et donc depuis Room.
 *
 * Aucun appel réseau ne doit être obligatoire.
 *
 * ------------------------------------------------------------
 * 🧠 RESPONSABILITÉ :
 *
 * Le Screen affiche l'état fourni par MatieresViewModel.
 *
 * ❌ Pas d'accès direct à Room.
 * ❌ Pas d'accès direct au DAO.
 * ❌ Pas de calcul de progression.
 * ❌ Pas de logique métier complexe.
 *
 * ------------------------------------------------------------
 * ⚠️ ÉTAT VIDE :
 *
 * Si aucune matière n'est disponible, afficher un message
 * compréhensible.
 *
 * Exemple :
 *
 * "Aucune matière disponible pour le moment."
 *
 * ------------------------------------------------------------
 * 🎨 ACCESSIBILITÉ :
 *
 * Respecter :
 *
 * - interface en français ;
 * - texte minimum 14sp ;
 * - contraste suffisant ;
 * - boutons facilement identifiables ;
 * - éléments suffisamment espacés pour être touchés.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - affichage des matières ;
 * - filtrage BAC / BEPC ;
 * - état vide ;
 * - sélection d'une matière ;
 * - choix du nombre de questions ;
 * - lancement du quiz ;
 * - accès aux fiches.
 *
 * ============================================================
 */

// TODO : créer le composable MatieresScreen
// TODO : recevoir l'état fourni par MatieresViewModel
// TODO : afficher la liste des matières
// TODO : afficher l'examen sélectionné
// TODO : permettre de sélectionner une matière
// TODO : permettre de choisir le nombre de questions
// TODO : prévoir l'accès aux fiches
// TODO : ajouter l'action pour démarrer le quiz
// TODO : gérer l'état vide
// TODO : ajouter les callbacks de navigation