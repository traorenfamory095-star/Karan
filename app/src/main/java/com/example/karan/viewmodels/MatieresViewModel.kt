package com.example.karan.viewmodels

/*
 * ============================================================
 * MATIERES VIEWMODEL — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Mamadou Alpha Diallo
 *
 * 🎯 OBJECTIF :
 * MatieresViewModel contient la logique nécessaire à
 * MatieresScreen.
 *
 * Il récupère les matières et les fiches depuis le Repository
 * et expose leur état à l'interface grâce à StateFlow.
 *
 * ------------------------------------------------------------
 * 🔄 FLUX :
 *
 * MatieresScreen
 *       ↓
 * MatieresViewModel
 *       ↓
 * RevisionRepository
 *       ↓
 * DAO
 *       ↓
 * Room
 *
 * ------------------------------------------------------------
 * 📚 DONNÉES À GÉRER :
 *
 * Le ViewModel devra notamment gérer :
 *
 * - l'examen sélectionné : BAC ou BEPC ;
 * - la liste des matières ;
 * - la matière sélectionnée ;
 * - les fiches associées à une matière ;
 * - le nombre de questions choisi ;
 * - l'état de chargement ;
 * - les éventuelles erreurs.
 *
 * ------------------------------------------------------------
 * 🔢 NOMBRE DE QUESTIONS :
 *
 * L'élève doit pouvoir choisir le nombre de questions avant
 * de commencer le quiz.
 *
 * Exemple :
 *
 * 5 questions
 * 10 questions
 * 20 questions
 *
 * Le ViewModel devra conserver ce choix afin qu'il puisse
 * être transmis au QuizScreen via la navigation.
 *
 * ------------------------------------------------------------
 * 📖 FICHES :
 *
 * Le ViewModel pourra demander au Repository les fiches
 * correspondant à la matière sélectionnée.
 *
 * Les fiches sont stockées localement dans Room afin de
 * fonctionner hors connexion.
 *
 * ------------------------------------------------------------
 * 🌊 STATEFLOW :
 *
 * Utiliser StateFlow pour exposer un état unique de l'écran.
 *
 * L'état pourra contenir notamment :
 *
 * - loading ;
 * - liste des matières ;
 * - matière sélectionnée ;
 * - liste des fiches ;
 * - nombre de questions ;
 * - erreur éventuelle.
 *
 * ------------------------------------------------------------
 * 🧠 LOGIQUE MÉTIER :
 *
 * Le ViewModel peut :
 *
 * - filtrer les matières selon BAC / BEPC ;
 * - sélectionner une matière ;
 * - sélectionner le nombre de questions ;
 * - demander les fiches d'une matière ;
 * - préparer les informations nécessaires au lancement
 *   du quiz.
 *
 * ------------------------------------------------------------
 * ❌ À NE PAS FAIRE :
 *
 * ❌ Ne pas accéder directement aux DAO.
 * ❌ Ne pas accéder directement à Room.
 * ❌ Ne pas écrire de code Compose.
 * ❌ Ne pas gérer la navigation directement.
 * ❌ Ne pas afficher de Toast depuis le ViewModel.
 *
 * Le ViewModel dépend uniquement de :
 *
 * RevisionRepository
 *
 * ------------------------------------------------------------
 * ⚠️ VALIDATION :
 *
 * Vérifier que :
 *
 * - une matière est bien sélectionnée avant de commencer ;
 * - le nombre de questions est valide ;
 * - il existe suffisamment de questions disponibles.
 *
 * Les messages d'erreur devront être exposés dans l'état
 * afin que le Screen puisse les afficher.
 *
 * ------------------------------------------------------------
 * 📭 ÉTAT VIDE :
 *
 * Si aucune matière n'est disponible :
 *
 * "Aucune matière disponible pour le moment."
 *
 * Si aucune fiche n'est disponible :
 *
 * "Aucune fiche disponible pour le moment."
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - filtrage BAC / BEPC ;
 * - récupération des matières ;
 * - sélection d'une matière ;
 * - récupération des fiches ;
 * - sélection du nombre de questions ;
 * - validation du nombre de questions ;
 * - gestion d'une liste vide ;
 * - gestion des erreurs ;
 * - mise à jour du StateFlow.
 *
 * ============================================================
 */

// TODO : créer l'UI State de l'écran des matières
// TODO : créer MatieresViewModel
// TODO : injecter RevisionRepository
// TODO : créer le StateFlow de l'écran
// TODO : charger les matières selon l'examen
// TODO : gérer la sélection d'une matière
// TODO : charger les fiches de la matière
// TODO : gérer le choix du nombre de questions
// TODO : valider les paramètres avant le lancement du quiz
// TODO : gérer les états loading / success / error / empty