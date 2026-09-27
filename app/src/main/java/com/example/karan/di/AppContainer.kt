package com.example.karan.di

/*
 * ============================================================
 * APP CONTAINER — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : N'famory Traore
 *
 * 🎯 OBJECTIF :
 * AppContainer centralise la création et la fourniture des
 * dépendances principales de l'application.
 *
 * Il permet notamment de construire :
 *
 * - AppDatabase ;
 * - les DAO ;
 * - RevisionRepository ;
 * - RevisionRepositoryImpl.
 *
 * ------------------------------------------------------------
 * 🔄 ARCHITECTURE :
 *
 * AppContainer
 *      ↓
 * AppDatabase
 *      ↓
 * DAO
 *      ↓
 * RevisionRepositoryImpl
 *      ↓
 * RevisionRepository
 *      ↓
 * ViewModel
 *
 * ------------------------------------------------------------
 * 🧠 POURQUOI UN CONTAINER ?
 *
 * On évite de créer la base de données et les repositories
 * directement dans les Screens.
 *
 * Exemple à éviter :
 *
 * Screen → Room.databaseBuilder(...)
 *
 * Les Screens doivent uniquement s'occuper de l'interface.
 *
 * ------------------------------------------------------------
 * 🗄️ BASE DE DONNÉES :
 *
 * AppContainer devra créer/récupérer une instance unique
 * d'AppDatabase pour l'application.
 *
 * Cette instance permettra d'obtenir :
 *
 * - MatiereDao
 * - QuestionDao
 * - SessionQuizDao
 * - FicheDao
 *
 * ------------------------------------------------------------
 * 📦 REPOSITORY :
 *
 * Le container devra construire une instance de
 * RevisionRepositoryImpl avec les DAO nécessaires.
 *
 * Mais les ViewModels devront recevoir :
 *
 * RevisionRepository
 *
 * et non directement :
 *
 * RevisionRepositoryImpl
 *
 * ------------------------------------------------------------
 * 🔗 DÉPENDANCES :
 *
 * AppContainer dépendra notamment de :
 *
 * - Context Android ;
 * - AppDatabase ;
 * - DAO ;
 * - RevisionRepository ;
 * - RevisionRepositoryImpl.
 *
 * ------------------------------------------------------------
 * 🚫 À NE PAS FAIRE :
 *
 * ❌ Pas de logique métier.
 * ❌ Pas de code Compose.
 * ❌ Pas de navigation.
 * ❌ Pas de calcul de score.
 * ❌ Pas de gestion du chronomètre.
 *
 * Le container sert uniquement à construire et fournir
 * les dépendances.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS / INTÉGRATION :
 *
 * Vérifier que :
 *
 * - la base est correctement créée ;
 * - les DAO sont disponibles ;
 * - le Repository peut être créé ;
 * - les ViewModels peuvent recevoir le Repository.
 *
 * ------------------------------------------------------------
 * 👤 RESPONSABILITÉ INTÉGRATION :
 *
 * Ce fichier sera particulièrement important lors de
 * l'intégration finale du projet.
 *
 * Toute modification importante des dépendances doit être
 * vérifiée avant de fusionner les branches.
 *
 * ============================================================
 */

// TODO : créer le conteneur AppContainer
// TODO : récupérer le Context nécessaire
// TODO : créer l'instance AppDatabase
// TODO : récupérer les quatre DAO
// TODO : créer RevisionRepositoryImpl
// TODO : exposer le Repository sous forme de RevisionRepository