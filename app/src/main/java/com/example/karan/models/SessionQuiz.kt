package com.example.karan.models

/*
 * ============================================================
 * PROJET : RÉVISION BAC / BEPC
 * FICHIER : SessionQuiz.kt
 * ============================================================
 *
 * RESPONSABLE :
 * Mohamed Maciré Soumah — Responsable données
 *
 * ============================================================
 * OBJECTIF DU FICHIER
 * ============================================================
 *
 * Représenter une session de quiz réalisée par l'utilisateur.
 *
 * Une session correspond à une tentative de quiz.
 *
 * Exemple :
 *
 * L'utilisateur choisit :
 * - BAC
 * - Mathématiques
 * - 10 questions
 *
 * Il termine le quiz avec :
 * - 7 bonnes réponses
 * - 3 mauvaises réponses
 *
 * Cette tentative constitue une SessionQuiz.
 *
 * ============================================================
 * UTILISATION
 * ============================================================
 *
 * Les données de SessionQuiz serviront notamment à :
 *
 * - afficher le résultat final ;
 * - conserver l'historique des quiz ;
 * - calculer la progression ;
 * - afficher le dernier quiz réalisé ;
 * - permettre à l'utilisateur de suivre ses performances.
 *
 * ============================================================
 * DONNÉES À PRÉVOIR
 * ============================================================
 *
 * La session doit au minimum permettre de connaître :
 *
 * - un identifiant unique ;
 * - la matière concernée ;
 * - l'examen concerné (BAC ou BEPC) ;
 * - le nombre total de questions ;
 * - le nombre de bonnes réponses ;
 * - le nombre de mauvaises réponses ;
 * - le score obtenu ;
 * - la date de réalisation.
 *
 * ============================================================
 * STRUCTURE ENVISAGÉE
 * ============================================================
 *
 * Exemple conceptuel :
 *
 * SessionQuiz
 * ├── id
 * ├── matiereId
 * ├── examen
 * ├── nombreQuestions
 * ├── bonnesReponses
 * ├── mauvaisesReponses
 * ├── score
 * └── date
 *
 * Les noms et types définitifs seront choisis
 * lors de l'implémentation.
 *
 * ============================================================
 * ROOM
 * ============================================================
 *
 * Cette classe sera également utilisée comme entité Room.
 *
 * TODO :
 * [ ] Ajouter l'annotation @Entity
 * [ ] Définir la table "sessions_quiz"
 * [ ] Définir la clé primaire
 * [ ] Définir les propriétés nécessaires
 * [ ] Vérifier les types compatibles avec Room
 *
 * ============================================================
 * HISTORIQUE
 * ============================================================
 *
 * Les sessions doivent rester enregistrées après :
 *
 * - fermeture de l'application ;
 * - redémarrage du téléphone ;
 * - absence de connexion Internet.
 *
 * TODO :
 * [ ] Prévoir les champs nécessaires à l'historique.
 * [ ] Vérifier que les sessions sont correctement sauvegardées.
 *
 * ============================================================
 * PROGRESSION
 * ============================================================
 *
 * Les données de cette classe pourront être utilisées pour
 * calculer les performances de l'utilisateur par matière.
 *
 * Exemple :
 *
 * Mathématiques
 * → 8/10
 * → 6/10
 * → 9/10
 *
 * TODO :
 * [ ] Vérifier que les informations enregistrées permettent
 *     de calculer une progression.
 *
 * ============================================================
 * RÈGLES
 * ============================================================
 *
 * - Pas de logique métier dans ce fichier.
 * - Pas de calcul de progression dans le modèle.
 * - Pas de logique d'interface utilisateur.
 * - Les données doivent être disponibles hors connexion.
 *
 * ============================================================
 * TESTS À PRÉVOIR
 * ============================================================
 *
 * [ ] Créer une session de test.
 * [ ] Enregistrer la session dans Room.
 * [ ] Récupérer la session depuis Room.
 * [ ] Vérifier que le score est correctement conservé.
 * [ ] Vérifier que la matière est correctement associée.
 * [ ] Vérifier que la date est correctement conservée.
 * [ ] Vérifier que plusieurs sessions peuvent être enregistrées.
 *
 * ============================================================
 * APRÈS VALIDATION
 * ============================================================
 *
 * Le modèle sera utilisé par :
 *
 * SessionQuiz.kt
 *      ↓
 * SessionQuizDao.kt
 *      ↓
 * RevisionRepository
 *      ↓
 * ResultatViewModel
 *      ↓
 * ResultatScreen
 *
 * ============================================================
 */

// TODO : créer le modèle SessionQuiz
