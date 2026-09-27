package com.example.karan.data.local.dao

/*
 * ============================================================
 * SESSION QUIZ DAO — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Mohamed Maciré Soumah
 *
 * 🎯 OBJECTIF :
 * Ce DAO permet de gérer les opérations Room liées aux
 * sessions de quiz enregistrées localement.
 *
 * Une SessionQuiz représente une tentative de quiz terminée.
 *
 * ------------------------------------------------------------
 * 🔄 FLUX DE DONNÉES :
 *
 * ResultatScreen
 *       ↓
 * ResultatViewModel
 *       ↓
 * RevisionRepository
 *       ↓
 * SessionQuizDao
 *       ↓
 * Room
 *
 * L'interface utilisateur ne doit jamais appeler directement
 * SessionQuizDao.
 *
 * ------------------------------------------------------------
 * 📦 OPÉRATIONS À PRÉVOIR :
 *
 * 1. Récupérer toutes les sessions de quiz
 *    → utile pour afficher l'historique.
 *
 * 2. Récupérer les sessions d'une matière
 *    → utile pour calculer/afficher la progression
 *      d'une matière.
 *
 * 3. Récupérer les dernières sessions
 *    → utile pour afficher les activités récentes
 *      sur l'écran d'accueil.
 *
 * 4. Récupérer une session par son id
 *    → utile pour consulter les détails d'une session.
 *
 * 5. Insérer une nouvelle session
 *    → appelé lorsqu'un quiz est terminé.
 *
 * 6. Modifier une session si nécessaire.
 *
 * 7. Supprimer une session si cette fonctionnalité est
 *    prévue dans le MVP.
 *
 * ------------------------------------------------------------
 * 🧠 IMPORTANT :
 *
 * Le DAO s'occupe uniquement de communiquer avec Room.
 *
 * ❌ Le DAO ne doit PAS :
 * - calculer le score ;
 * - vérifier les réponses ;
 * - gérer le chronomètre ;
 * - décider de la progression ;
 * - gérer la navigation ;
 * - contenir du code Compose.
 *
 * Ces responsabilités appartiennent principalement au
 * ViewModel et/ou au Repository selon leur nature.
 *
 * ------------------------------------------------------------
 * 💾 PERSISTANCE :
 *
 * Les sessions doivent rester disponibles même après :
 * - fermeture de l'application ;
 * - redémarrage du téléphone ;
 * - utilisation hors connexion.
 *
 * Le projet étant offline-first, aucune connexion Internet
 * ne doit être nécessaire pour récupérer l'historique.
 *
 * ------------------------------------------------------------
 * 🌊 FLOW / SUSPEND :
 *
 * Utiliser Flow pour les données que l'interface doit pouvoir
 * observer automatiquement.
 *
 * Exemple :
 * - historique des sessions ;
 * - progression basée sur les sessions.
 *
 * Utiliser suspend pour les opérations ponctuelles.
 *
 * Exemple :
 * - insérer une session ;
 * - récupérer une session précise ;
 * - supprimer une session.
 *
 * ------------------------------------------------------------
 * 🏗️ ROOM :
 *
 * Le DAO devra utiliser :
 * - @Dao
 * - @Query
 * - @Insert
 * - éventuellement @Update
 * - éventuellement @Delete
 *
 * Les requêtes SQL devront correspondre exactement aux
 * propriétés définies dans SessionQuiz.kt.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier notamment que :
 * - une session peut être enregistrée ;
 * - les sessions enregistrées peuvent être récupérées ;
 * - les sessions d'une matière peuvent être filtrées ;
 * - les dernières sessions sont correctement récupérées ;
 * - une session inexistante ne provoque pas de comportement
 *   inattendu.
 *
 * ------------------------------------------------------------
 * 🔗 DÉPENDANCES :
 *
 * Ce fichier dépend principalement de :
 *
 * - models/SessionQuiz.kt
 * - Room
 *
 * Il ne doit pas dépendre des Screens ou des ViewModels.
 *
 * ============================================================
 */

// TODO : créer le DAO SessionQuizDao