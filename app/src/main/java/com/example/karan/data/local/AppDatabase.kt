package com.example.karan.data.local

/*
 * ============================================================
 * APP DATABASE — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Mohamed Maciré Soumah
 *
 * 🎯 OBJECTIF :
 * AppDatabase représente la base de données locale Room
 * de l'application Révision BAC / BEPC.
 *
 * Elle constitue le point central permettant à l'application
 * d'accéder aux données stockées sur le téléphone.
 *
 * ------------------------------------------------------------
 * 🗄️ DONNÉES GÉRÉES :
 *
 * La base de données doit contenir les entités suivantes :
 *
 * - Matiere
 * - Question
 * - SessionQuiz
 * - Fiche
 *
 * Ces modèles sont également utilisés comme entités Room
 * afin de simplifier l'architecture du projet.
 *
 * ------------------------------------------------------------
 * 🔄 ARCHITECTURE :
 *
 * View
 *   ↓
 * ViewModel
 *   ↓
 * Repository
 *   ↓
 * DAO
 *   ↓
 * AppDatabase
 *   ↓
 * Room
 *
 * Les Screens et les ViewModels ne doivent jamais créer
 * directement la base de données.
 *
 * ------------------------------------------------------------
 * 🔌 DAO À EXPOSER :
 *
 * AppDatabase devra fournir l'accès aux DAO suivants :
 *
 * - MatiereDao
 * - QuestionDao
 * - SessionQuizDao
 * - FicheDao
 *
 * Exemple de principe :
 *
 * database.matiereDao()
 * database.questionDao()
 * database.sessionQuizDao()
 * database.ficheDao()
 *
 * ------------------------------------------------------------
 * 🏗️ ROOM :
 *
 * La classe devra :
 *
 * - hériter de RoomDatabase ;
 * - utiliser l'annotation @Database ;
 * - déclarer toutes les entités ;
 * - définir une version de la base ;
 * - exposer les DAO sous forme de fonctions abstraites.
 *
 * ------------------------------------------------------------
 * 🔢 VERSION :
 *
 * Commencer avec une version initiale de la base.
 *
 * Si la structure des entités change plus tard, la version
 * devra être augmentée et une migration devra être prévue.
 *
 * ⚠️ Ne pas utiliser fallbackToDestructiveMigration()
 * sans raison valable dans la version finale du projet,
 * car cela pourrait supprimer les données locales de l'élève.
 *
 * ------------------------------------------------------------
 * 📱 OFFLINE-FIRST :
 *
 * AppDatabase est essentielle au fonctionnement hors ligne.
 *
 * Les données nécessaires au MVP doivent être conservées
 * localement dans Room.
 *
 * L'application doit continuer à fonctionner en mode avion.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier notamment que :
 *
 * - la base peut être créée ;
 * - les DAO sont accessibles ;
 * - une donnée peut être insérée ;
 * - une donnée peut être récupérée ;
 * - les données restent disponibles après fermeture
 *   et réouverture de l'application.
 *
 * ------------------------------------------------------------
 * 🚫 À NE PAS METTRE ICI :
 *
 * ❌ logique métier
 * ❌ calcul du score
 * ❌ gestion du chronomètre
 * ❌ navigation
 * ❌ code Compose
 * ❌ logique d'affichage
 * ❌ appels réseau
 *
 * AppDatabase sert uniquement à configurer et fournir
 * la base Room.
 *
 * ============================================================
 */

// TODO : créer la classe AppDatabase
// TODO : déclarer les 4 entités Room
// TODO : déclarer les 4 DAO
// TODO : définir la version initiale de la base