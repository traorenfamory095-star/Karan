package com.example.karan.data.repository

/*
 * ============================================================
 * REVISION REPOSITORY — INTERFACE
 * ============================================================
 *
 * 👤 Responsable : Mohamed Maciré Soumah
 *
 * 🎯 OBJECTIF :
 * Cette interface définit les opérations que l'application
 * peut effectuer sur les données de révision.
 *
 * Elle constitue une abstraction entre les ViewModels et
 * la source réelle des données.
 *
 * ------------------------------------------------------------
 * 🔄 FLUX DE DONNÉES :
 *
 * View
 *   ↓
 * ViewModel
 *   ↓
 * RevisionRepository
 *   ↓
 * RevisionRepositoryImpl
 *   ↓
 * DAO
 *   ↓
 * Room
 *
 * Les ViewModels doivent dépendre de cette interface et
 * non directement de RevisionRepositoryImpl.
 *
 * ------------------------------------------------------------
 * 🧠 POURQUOI UNE INTERFACE ?
 *
 * L'application doit respecter le principe :
 *
 * "Le ViewModel ne doit pas connaître la manière dont
 * les données sont stockées."
 *
 * Aujourd'hui :
 *
 * ViewModel → Repository → Room
 *
 * Plus tard, si nécessaire, on pourrait avoir :
 *
 * ViewModel → Repository → API
 *
 * sans devoir modifier toute la logique des ViewModels.
 *
 * ------------------------------------------------------------
 * 📚 OPÉRATIONS À PRÉVOIR :
 *
 * MATIÈRES :
 * - récupérer toutes les matières ;
 * - récupérer les matières par examen ;
 * - récupérer une matière par son id.
 *
 * QUESTIONS :
 * - récupérer les questions d'une matière ;
 * - récupérer les questions d'un examen ;
 * - récupérer un nombre limité de questions ;
 * - récupérer une question par son id.
 *
 * SESSIONS :
 * - récupérer l'historique des quiz ;
 * - récupérer les sessions d'une matière ;
 * - récupérer les dernières sessions ;
 * - enregistrer une nouvelle session ;
 * - récupérer une session par son id.
 *
 * FICHES :
 * - récupérer toutes les fiches ;
 * - récupérer les fiches d'une matière ;
 * - récupérer les fiches d'un examen ;
 * - récupérer une fiche par son id.
 *
 * ------------------------------------------------------------
 * 🌊 FLOW / SUSPEND :
 *
 * Les opérations destinées à observer une liste de données
 * peuvent retourner Flow.
 *
 * Les opérations ponctuelles peuvent être suspend.
 *
 * Le choix définitif devra rester cohérent avec les méthodes
 * des DAO.
 *
 * ------------------------------------------------------------
 * 🚫 À NE PAS FAIRE :
 *
 * ❌ Ne pas mettre de logique d'interface.
 * ❌ Ne pas utiliser Compose.
 * ❌ Ne pas gérer la navigation.
 * ❌ Ne pas calculer le score du quiz ici.
 * ❌ Ne pas gérer le chronomètre.
 * ❌ Ne pas appeler directement l'interface utilisateur.
 *
 * Le Repository sert principalement à coordonner l'accès
 * aux données.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier que les ViewModels pourront utiliser le Repository
 * sans connaître les DAO directement.
 *
 * Il faudra également pouvoir remplacer l'implémentation
 * réelle par une fausse implémentation lors des tests.
 *
 * ============================================================
 */

// TODO : déclarer l'interface RevisionRepository
// TODO : définir les opérations nécessaires à l'application
