package com.example.karan.data.local.dao

/*
 * ============================================================
 * FICHE DAO — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Mohamed Maciré Soumah
 *
 * 🎯 OBJECTIF :
 * Ce DAO permet de gérer les opérations Room liées aux
 * fiches de révision disponibles hors connexion.
 *
 * Une Fiche représente un contenu pédagogique permettant
 * à l'élève de réviser une matière ou un chapitre.
 *
 * ------------------------------------------------------------
 * 🔄 FLUX DE DONNÉES :
 *
 * MatieresScreen
 *       ↓
 * MatieresViewModel
 *       ↓
 * RevisionRepository
 *       ↓
 * FicheDao
 *       ↓
 * Room
 *
 * L'interface utilisateur ne doit jamais appeler directement
 * FicheDao.
 *
 * ------------------------------------------------------------
 * 📦 OPÉRATIONS À PRÉVOIR :
 *
 * 1. Récupérer toutes les fiches
 *    → utile pour afficher les contenus disponibles.
 *
 * 2. Récupérer les fiches d'une matière
 *    → permet d'afficher uniquement les fiches correspondant
 *      à la matière sélectionnée.
 *
 * 3. Récupérer les fiches d'une matière et d'un examen
 *    → permet de différencier le contenu BEPC et BAC.
 *
 * 4. Récupérer une fiche par son id
 *    → utile lorsqu'un élève sélectionne une fiche précise.
 *
 * 5. Insérer une ou plusieurs fiches
 *    → utile lors du remplissage initial de la base locale.
 *
 * 6. Modifier une fiche si nécessaire.
 *
 * 7. Supprimer une fiche si cette fonctionnalité est prévue.
 *
 * ------------------------------------------------------------
 * 📚 ORDRE DES CHAPITRES :
 *
 * Le modèle Fiche possède une information permettant
 * d'organiser les fiches dans un ordre précis.
 *
 * Le DAO pourra donc récupérer les fiches triées afin que
 * l'élève consulte les chapitres dans un ordre logique.
 *
 * Exemple :
 *
 * Chapitre 1
 * Chapitre 2
 * Chapitre 3
 *
 * plutôt qu'un ordre aléatoire.
 *
 * ------------------------------------------------------------
 * 🌐 OFFLINE-FIRST :
 *
 * Les fiches constituent une partie importante du mode
 * hors connexion.
 *
 * L'élève doit pouvoir consulter les fiches sans Internet.
 *
 * Les données doivent donc être récupérées depuis Room
 * et non depuis une API obligatoire.
 *
 * ------------------------------------------------------------
 * 🌊 FLOW / SUSPEND :
 *
 * Utiliser Flow pour les listes de fiches que l'interface
 * doit pouvoir observer automatiquement.
 *
 * Utiliser suspend pour les opérations ponctuelles.
 *
 * Exemple :
 * - récupérer une fiche précise ;
 * - insérer des fiches ;
 * - modifier une fiche ;
 * - supprimer une fiche.
 *
 * ------------------------------------------------------------
 * 🏗️ ROOM :
 *
 * Le DAO devra utiliser :
 *
 * - @Dao
 * - @Query
 * - @Insert
 * - éventuellement @Update
 * - éventuellement @Delete
 *
 * Les requêtes devront correspondre exactement aux propriétés
 * définies dans Fiche.kt.
 *
 * ------------------------------------------------------------
 * 🧠 IMPORTANT :
 *
 * Le DAO ne contient PAS de logique métier.
 *
 * ❌ Ne pas :
 * - décider quelle fiche afficher ;
 * - gérer la navigation ;
 * - modifier l'interface ;
 * - gérer Compose ;
 * - gérer le score du quiz ;
 * - gérer le chronomètre.
 *
 * Son rôle est uniquement de communiquer avec Room.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier notamment que :
 *
 * - une fiche peut être enregistrée ;
 * - plusieurs fiches peuvent être enregistrées ;
 * - les fiches peuvent être récupérées ;
 * - les fiches d'une matière peuvent être filtrées ;
 * - les fiches BEPC/BAC peuvent être différenciées ;
 * - les fiches sont récupérées dans le bon ordre ;
 * - une fiche précise peut être récupérée par son id.
 *
 * ------------------------------------------------------------
 * 🔗 DÉPENDANCES :
 *
 * Ce fichier dépend principalement de :
 *
 * - models/Fiche.kt
 * - Room
 *
 * Il ne doit pas dépendre des Screens ou des ViewModels.
 *
 * ============================================================
 */

// TODO : créer le DAO FicheDao