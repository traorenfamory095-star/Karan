package com.example.karan.data.local.dao

/*
 * ============================================================
 * PROJET : RÉVISION BAC / BEPC
 * FICHIER : QuestionDao.kt
 * ============================================================
 *
 * RESPONSABLE :
 * Mohamed Maciré Soumah — Responsable données
 *
 * ============================================================
 * OBJECTIF DU FICHIER
 * ============================================================
 *
 * Fournir toutes les opérations nécessaires pour accéder
 * aux questions du quiz stockées dans Room.
 *
 * Les questions constituent les données principales utilisées
 * pendant une session de quiz.
 *
 * ============================================================
 * ARCHITECTURE
 * ============================================================
 *
 * QuizScreen
 *      ↓
 * QuizViewModel
 *      ↓
 * RevisionRepository
 *      ↓
 * QuestionDao
 *      ↓
 * Room
 *
 * L'interface utilisateur ne doit jamais communiquer
 * directement avec QuestionDao.
 *
 * ============================================================
 * OPÉRATIONS À PRÉVOIR
 * ============================================================
 *
 * Le DAO devra permettre de :
 *
 * - récupérer les questions d'une matière ;
 * - récupérer les questions selon l'examen ;
 * - récupérer un nombre limité de questions pour un quiz ;
 * - récupérer une question par son identifiant ;
 * - insérer des questions ;
 * - éventuellement modifier une question ;
 * - éventuellement supprimer une question.
 *
 * ============================================================
 * SÉLECTION DES QUESTIONS
 * ============================================================
 *
 * L'utilisateur pourra choisir le nombre de questions
 * lorsqu'il démarre un quiz.
 *
 * Exemple :
 *
 * L'utilisateur choisit 10 questions.
 *
 * Le système devra pouvoir récupérer 10 questions
 * correspondant à la matière et à l'examen sélectionnés.
 *
 * TODO :
 * [ ] Prévoir une requête permettant de limiter
 *     le nombre de questions retournées.
 *
 * [ ] Prévoir le filtrage par matière.
 *
 * [ ] Prévoir le filtrage par examen.
 *
 * ============================================================
 * ERREURS À REVOIR
 * ============================================================
 *
 * Le projet possède une fonctionnalité :
 *
 * "Erreurs à revoir"
 *
 * Le DAO doit donc permettre au reste de l'application
 * de retrouver les questions concernées.
 *
 * IMPORTANT :
 *
 * Le DAO ne décide pas si une réponse est correcte ou non.
 *
 * Il fournit uniquement les données nécessaires.
 *
 * TODO :
 * [ ] Vérifier quelles informations permettront d'identifier
 *     les questions auxquelles l'utilisateur s'est trompé.
 *
 * ============================================================
 * ROOM
 * ============================================================
 *
 * TODO :
 * [ ] Ajouter @Dao
 *
 * [ ] Créer les requêtes avec @Query
 *
 * [ ] Créer l'insertion avec @Insert
 *
 * [ ] Ajouter @Update si nécessaire
 *
 * [ ] Ajouter @Delete si nécessaire
 *
 * [ ] Vérifier que les requêtes correspondent à Question.kt
 *
 * ============================================================
 * RETOUR DES DONNÉES
 * ============================================================
 *
 * Pour les données observables :
 *
 * TODO :
 * [ ] Utiliser Flow lorsque cela est pertinent.
 *
 * Pour les opérations ponctuelles :
 *
 * TODO :
 * [ ] Utiliser suspend lorsque cela est nécessaire.
 *
 * ============================================================
 * RÈGLES
 * ============================================================
 *
 * - Aucune logique métier dans le DAO.
 * - Aucun calcul de score dans le DAO.
 * - Aucune vérification de réponse dans le DAO.
 * - Aucune logique de chronomètre dans le DAO.
 * - Aucune logique d'interface dans le DAO.
 * - Aucune logique de navigation dans le DAO.
 *
 * Le DAO communique uniquement avec Room.
 *
 * ============================================================
 * TESTS À PRÉVOIR
 * ============================================================
 *
 * [ ] Insérer plusieurs questions.
 *
 * [ ] Récupérer toutes les questions d'une matière.
 *
 * [ ] Récupérer les questions d'un examen donné.
 *
 * [ ] Récupérer un nombre précis de questions.
 *
 * [ ] Récupérer une question avec son id.
 *
 * [ ] Vérifier qu'une question inexistante ne provoque
 *     pas de comportement inattendu.
 *
 * ============================================================
 * DÉPENDANCES
 * ============================================================
 *
 * Ce DAO dépendra de :
 *
 * - Question.kt
 * - Room
 *
 * Il sera utilisé ensuite par :
 *
 * RevisionRepositoryImpl.kt
 *
 * ============================================================
 */

// TODO : créer le DAO QuestionDao