package com.example.karan.viewmodels

/*
 * ============================================================
 * QUIZ VIEWMODEL — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Mamadou Alpha Diallo
 *
 * 🎯 OBJECTIF :
 * QuizViewModel contient toute la logique métier nécessaire
 * au déroulement d'un quiz.
 *
 * Il constitue le cerveau du QuizScreen.
 *
 * ------------------------------------------------------------
 * 🔄 FLUX :
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
 * ------------------------------------------------------------
 * 📥 PARAMÈTRES DU QUIZ :
 *
 * Le ViewModel devra recevoir/prendre en compte :
 *
 * - l'examen : BAC ou BEPC ;
 * - l'identifiant de la matière ;
 * - le nombre de questions choisi.
 *
 * Ces informations permettent de récupérer les bonnes
 * questions depuis le Repository.
 *
 * ------------------------------------------------------------
 * ❓ GESTION DES QUESTIONS :
 *
 * Le ViewModel devra :
 *
 * - charger les questions ;
 * - conserver la liste du quiz actuel ;
 * - connaître l'index de la question actuelle ;
 * - afficher une question à la fois ;
 * - passer à la question suivante ;
 * - détecter la fin du quiz.
 *
 * ------------------------------------------------------------
 * 📝 GESTION DES RÉPONSES :
 *
 * Lorsqu'un élève choisit une proposition :
 *
 * 1. récupérer la réponse choisie ;
 * 2. comparer avec la bonne réponse ;
 * 3. déterminer si elle est correcte ;
 * 4. mettre à jour le score ;
 * 5. mémoriser la réponse donnée ;
 * 6. identifier une éventuelle erreur ;
 * 7. afficher la correction ;
 * 8. afficher l'explication.
 *
 * ------------------------------------------------------------
 * 🎯 SCORE :
 *
 * Le calcul du score appartient au ViewModel.
 *
 * Exemple :
 *
 * 8 bonnes réponses sur 10
 *
 * → score : 8 / 10
 *
 * Le ViewModel devra également pouvoir calculer le
 * pourcentage de réussite si nécessaire.
 *
 * ------------------------------------------------------------
 * ❌ ERREURS À REVOIR :
 *
 * Lorsqu'une réponse est incorrecte, la question devra
 * pouvoir être identifiée comme une erreur.
 *
 * Ces informations pourront ensuite être utilisées pour
 * permettre à l'élève de revoir ses erreurs.
 *
 * ⚠️ Le mécanisme exact de persistance devra être défini
 * avec le modèle de données et le Repository.
 *
 * ------------------------------------------------------------
 * ⏱️ CHRONOMÈTRE :
 *
 * Le ViewModel est responsable du chronomètre.
 *
 * Il devra :
 *
 * - démarrer le temps ;
 * - maintenir le temps restant ;
 * - mettre à jour l'état ;
 * - détecter lorsque le temps arrive à zéro ;
 * - terminer le quiz si nécessaire ;
 * - arrêter le chronomètre lorsque le quiz est terminé.
 *
 * ❌ Le chronomètre ne doit pas être géré directement
 * dans le Composable.
 *
 * ------------------------------------------------------------
 * 🌊 STATEFLOW :
 *
 * Utiliser StateFlow pour exposer l'état du quiz.
 *
 * L'état devra pouvoir représenter notamment :
 *
 * - chargement ;
 * - quiz prêt ;
 * - question actuelle ;
 * - réponse sélectionnée ;
 * - correction affichée ;
 * - temps restant ;
 * - score ;
 * - quiz terminé ;
 * - erreur ;
 * - aucune question disponible.
 *
 * ------------------------------------------------------------
 * 🔒 RÉPONSE DÉJÀ DONNÉE :
 *
 * Après sélection d'une réponse :
 *
 * - empêcher une deuxième réponse ;
 * - conserver la réponse choisie ;
 * - afficher la correction ;
 * - afficher l'explication ;
 * - permettre ensuite de passer à la question suivante.
 *
 * ------------------------------------------------------------
 * 💾 FIN DU QUIZ :
 *
 * Lorsque le quiz est terminé, le ViewModel devra préparer
 * les informations nécessaires à la création d'une
 * SessionQuiz.
 *
 * La session devra ensuite être enregistrée localement
 * via RevisionRepository.
 *
 * ------------------------------------------------------------
 * 🧠 RESPONSABILITÉS :
 *
 * QuizViewModel est responsable de la logique métier du quiz.
 *
 * Il peut donc gérer :
 *
 * ✅ score
 * ✅ réponses
 * ✅ correction
 * ✅ progression
 * ✅ chronomètre
 * ✅ fin du quiz
 * ✅ préparation de la session
 * ✅ erreurs à revoir
 *
 * ------------------------------------------------------------
 * 🚫 À NE PAS FAIRE :
 *
 * ❌ Pas de code Compose.
 * ❌ Pas d'accès direct aux DAO.
 * ❌ Pas d'accès direct à Room.
 * ❌ Pas de navigation UI.
 *
 * Pour les données :
 *
 * QuizViewModel → RevisionRepository
 *
 * et jamais :
 *
 * QuizViewModel → QuestionDao
 *
 * ------------------------------------------------------------
 * ⚠️ CAS PARTICULIERS :
 *
 * Prévoir les situations suivantes :
 *
 * - aucune question disponible ;
 * - moins de questions disponibles que demandé ;
 * - réponse vide/non sélectionnée ;
 * - temps écoulé ;
 * - erreur lors du chargement ;
 * - quiz terminé ;
 * - tentative de répondre deux fois.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - chargement des questions ;
 * - nombre de questions ;
 * - sélection d'une réponse ;
 * - bonne réponse ;
 * - mauvaise réponse ;
 * - calcul du score ;
 * - progression ;
 * - chronomètre ;
 * - temps écoulé ;
 * - question suivante ;
 * - fin du quiz ;
 * - enregistrement de la session ;
 * - identification des erreurs.
 *
 * ============================================================
 */

// TODO : créer l'UI State du quiz
// TODO : créer QuizViewModel
// TODO : injecter RevisionRepository
// TODO : charger les questions du quiz
// TODO : gérer la question actuelle
// TODO : gérer la réponse sélectionnée
// TODO : vérifier la réponse
// TODO : gérer la correction et l'explication
// TODO : calculer le score
// TODO : gérer la progression
// TODO : créer le chronomètre
// TODO : gérer le temps écoulé
// TODO : identifier les erreurs à revoir
// TODO : terminer le quiz
// TODO : préparer et enregistrer SessionQuiz
// TODO : gérer les états loading / success / error / empty