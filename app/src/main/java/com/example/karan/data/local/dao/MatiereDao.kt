package com.example.karan.data.local.dao

/*
 * ============================================================
 * PROJET : RÉVISION BAC / BEPC
 * FICHIER : MatiereDao.kt
 * ============================================================
 *
 * RESPONSABLE :
 * Mohamed Maciré Soumah — Responsable données
 *
 * ============================================================
 * OBJECTIF DU FICHIER
 * ============================================================
 *
 * Fournir les opérations permettant de lire et modifier
 * les matières stockées dans la base de données Room.
 *
 * Le DAO est la seule couche qui communique directement
 * avec la table des matières.
 *
 * ============================================================
 * ARCHITECTURE
 * ============================================================
 *
 * MatiereScreen
 *      ↓
 * MatieresViewModel
 *      ↓
 * RevisionRepository
 *      ↓
 * MatiereDao
 *      ↓
 * Room
 *
 * L'interface utilisateur ne doit JAMAIS appeler directement
 * MatiereDao.
 *
 * ============================================================
 * OPÉRATIONS À PRÉVOIR
 * ============================================================
 *
 * Le DAO devra permettre notamment de :
 *
 * - récupérer toutes les matières ;
 * - récupérer les matières d'un examen ;
 * - récupérer une matière par son identifiant ;
 * - ajouter une matière ;
 * - éventuellement modifier une matière ;
 * - éventuellement supprimer une matière.
 *
 * ============================================================
 * RETOUR DES DONNÉES
 * ============================================================
 *
 * Les données destinées à l'interface devront pouvoir être
 * observées afin que l'interface se mette à jour lorsque
 * les données changent.
 *
 * TODO :
 * [ ] Choisir entre Flow et suspend selon l'opération.
 * [ ] Utiliser Flow pour les données observables.
 * [ ] Utiliser suspend pour les opérations ponctuelles.
 *
 * ============================================================
 * ROOM
 * ============================================================
 *
 * TODO :
 * [ ] Ajouter @Dao
 * [ ] Déclarer les fonctions Room
 * [ ] Utiliser les bonnes annotations :
 *     - @Query
 *     - @Insert
 *     - @Update
 *     - @Delete si nécessaire
 * [ ] Vérifier que les requêtes correspondent exactement
 *     à la structure de Matiere.kt.
 *
 * ============================================================
 * REQUÊTES À PRÉVOIR
 * ============================================================
 *
 * TODO :
 *
 * [ ] Récupérer toutes les matières
 *
 * [ ] Récupérer les matières correspondant au BAC
 *
 * [ ] Récupérer les matières correspondant au BEPC
 *
 * [ ] Récupérer une matière avec son id
 *
 * [ ] Insérer une matière
 *
 * ============================================================
 * RÈGLES
 * ============================================================
 *
 * - Aucune logique métier dans le DAO.
 * - Aucune logique d'interface dans le DAO.
 * - Le DAO ne doit pas gérer le score du quiz.
 * - Le DAO ne doit pas calculer la progression.
 * - Le DAO ne doit pas contenir de logique de navigation.
 *
 * Son rôle est uniquement de communiquer avec Room.
 *
 * ============================================================
 * TESTS À PRÉVOIR
 * ============================================================
 *
 * [ ] Insérer une matière.
 * [ ] Récupérer toutes les matières.
 * [ ] Récupérer une matière par son id.
 * [ ] Filtrer les matières par examen.
 * [ ] Vérifier que les données retournées sont correctes.
 *
 * ============================================================
 * DÉPENDANCES
 * ============================================================
 *
 * Ce DAO dépendra de :
 *
 * - Matiere.kt
 * - Room
 *
 * Il sera ensuite utilisé par :
 *
 * RevisionRepositoryImpl.kt
 *
 * ============================================================
 */

// TODO : créer le DAO MatiereDao