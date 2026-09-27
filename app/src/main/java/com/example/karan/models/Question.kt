package com.example.karan.models

/*
 * ============================================================
 * PROJET : RÉVISION BAC / BEPC
 * FICHIER : Question.kt
 * ============================================================
 *
 * RESPONSABLE :
 * Mohamed Maciré Soumah — Responsable données
 *
 * ============================================================
 * OBJECTIF DU FICHIER
 * ============================================================
 *
 * Représenter une question de quiz stockée localement
 * dans la base de données Room.
 *
 * Une question appartient à une matière et doit permettre
 * à l'utilisateur de :
 *
 * - lire un énoncé ;
 * - choisir une réponse ;
 * - obtenir une correction ;
 * - comprendre l'explication ;
 * - retrouver la question dans "Erreurs à revoir"
 *   lorsqu'il s'est trompé.
 *
 * ============================================================
 * DONNÉES À PRÉVOIR
 * ============================================================
 *
 * La question doit au minimum contenir :
 *
 * - un identifiant unique ;
 * - l'identifiant de la matière ;
 * - l'énoncé ;
 * - quatre propositions de réponse ;
 * - la bonne réponse ;
 * - une explication de la correction.
 *
 * Il faudra également pouvoir distinguer :
 *
 * - les questions du BEPC ;
 * - les questions du BAC.
 *
 * ============================================================
 * STRUCTURE ENVISAGÉE
 * ============================================================
 *
 * Exemple conceptuel :
 *
 * Question
 * ├── id
 * ├── matiereId
 * ├── examen
 * ├── enonce
 * ├── propositionA
 * ├── propositionB
 * ├── propositionC
 * ├── propositionD
 * ├── bonneReponse
 * └── explication
 *
 * Les noms définitifs des propriétés seront choisis
 * lors de l'implémentation.
 *
 * ============================================================
 * ROOM
 * ============================================================
 *
 * Ce modèle sera également utilisé comme entité Room.
 *
 * TODO :
 * [ ] Ajouter l'annotation @Entity
 * [ ] Définir la table "questions"
 * [ ] Définir la clé primaire
 * [ ] Définir les propriétés nécessaires
 * [ ] Définir la relation avec une matière
 * [ ] Vérifier la compatibilité des types avec Room
 *
 * ============================================================
 * ERREURS À REVOIR
 * ============================================================
 *
 * Une question doit pouvoir être retrouvée après une erreur.
 *
 * TODO :
 * [ ] Vérifier quelles données sont nécessaires pour identifier
 *     une question incorrectement répondue.
 * [ ] Préparer les données nécessaires au futur historique
 *     des erreurs.
 *
 * Attention :
 * La logique permettant de déterminer si l'utilisateur s'est
 * trompé ne doit PAS être placée dans ce modèle.
 *
 * ============================================================
 * RÈGLES
 * ============================================================
 *
 * - Pas de logique métier dans ce fichier.
 * - Pas de logique d'interface utilisateur.
 * - Les questions doivent être disponibles hors connexion.
 * - Les données doivent être compatibles avec Room.
 * - Une question doit toujours avoir une bonne réponse définie.
 * - Une question doit être associée à une matière.
 *
 * ============================================================
 * TESTS À PRÉVOIR
 * ============================================================
 *
 * [ ] Créer une question de test.
 * [ ] Vérifier qu'elle peut être enregistrée dans Room.
 * [ ] Vérifier qu'elle peut être récupérée.
 * [ ] Vérifier que les quatre propositions sont conservées.
 * [ ] Vérifier que la bonne réponse est conservée.
 * [ ] Vérifier que l'explication est conservée.
 * [ ] Vérifier que la question est correctement associée
 *     à une matière.
 *
 * ============================================================
 * APRÈS VALIDATION
 * ============================================================
 *
 * Le modèle sera utilisé par :
 *
 * Question.kt
 *      ↓
 * QuestionDao.kt
 *      ↓
 * RevisionRepository
 *      ↓
 * QuizViewModel
 *      ↓
 * QuizScreen
 *
 * ============================================================
 */

// TODO : créer le modèle Question