package com.example.karan.views

/*
 * ============================================================
 * QUIZ SCREEN — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Amadou Bah
 *
 * 🎯 OBJECTIF :
 * QuizScreen permet à l'élève de répondre aux questions
 * d'un quiz et de voir immédiatement la correction ainsi que
 * l'explication associée.
 *
 * ------------------------------------------------------------
 * 📱 RÔLE DANS LE MVP :
 *
 * Cet écran représente le cœur de l'expérience de révision.
 *
 * Il doit afficher :
 *
 * - la question actuelle ;
 * - les propositions de réponse ;
 * - la progression dans le quiz ;
 * - le chronomètre ;
 * - la correction ;
 * - l'explication ;
 * - le bouton permettant de continuer.
 *
 * ------------------------------------------------------------
 * 🔄 PARCOURS :
 *
 * MatieresScreen
 *       ↓
 * QuizScreen
 *       ↓
 * réponse de l'élève
 *       ↓
 * correction + explication
 *       ↓
 * question suivante
 *       ↓
 * ResultatScreen
 *
 * ------------------------------------------------------------
 * 📝 QUESTION :
 *
 * Afficher une seule question à la fois.
 *
 * Une question contient :
 *
 * - énoncé ;
 * - proposition A ;
 * - proposition B ;
 * - proposition C ;
 * - proposition D ;
 * - bonne réponse ;
 * - explication.
 *
 * ------------------------------------------------------------
 * ⏱️ CHRONOMÈTRE :
 *
 * Le quiz est chronométré.
 *
 * Le Screen doit uniquement AFFICHER le temps fourni par
 * QuizViewModel.
 *
 * ❌ Le Screen ne doit pas gérer lui-même la logique du
 * chronomètre.
 *
 * Le ViewModel est responsable de :
 *
 * - démarrer le chronomètre ;
 * - diminuer le temps ;
 * - détecter la fin du temps ;
 * - terminer le quiz si nécessaire.
 *
 * ------------------------------------------------------------
 * ✅ RÉPONSE :
 *
 * Lorsque l'élève sélectionne une réponse :
 *
 * 1. transmettre la réponse au ViewModel ;
 * 2. le ViewModel détermine si elle est correcte ;
 * 3. l'état est mis à jour ;
 * 4. le Screen affiche la correction ;
 * 5. l'explication est affichée ;
 * 6. l'élève peut passer à la question suivante.
 *
 * ------------------------------------------------------------
 * 🎨 AFFICHAGE DE LA CORRECTION :
 *
 * Avant la réponse :
 *
 * → les propositions sont sélectionnables.
 *
 * Après la réponse :
 *
 * → empêcher une nouvelle modification de la réponse ;
 * → indiquer clairement la bonne réponse ;
 * → indiquer si la réponse de l'élève est correcte ou non ;
 * → afficher l'explication.
 *
 * Exemple :
 *
 * "Bonne réponse !"
 *
 * ou
 *
 * "Mauvaise réponse."
 *
 * Puis :
 *
 * "Explication : ..."
 *
 * ------------------------------------------------------------
 * 📊 PROGRESSION :
 *
 * Afficher par exemple :
 *
 * Question 3 / 10
 *
 * ou une barre de progression.
 *
 * Les informations doivent provenir du ViewModel.
 *
 * ------------------------------------------------------------
 * ❌ ERREURS À REVOIR :
 *
 * Lorsqu'une réponse est incorrecte, le ViewModel devra
 * pouvoir identifier la question comme erreur à revoir.
 *
 * Le Screen ne doit pas décider comment cette erreur est
 * enregistrée.
 *
 * ------------------------------------------------------------
 * 🧠 RESPONSABILITÉ :
 *
 * Le Screen est responsable uniquement de l'affichage et
 * des interactions utilisateur.
 *
 * ❌ Pas de Room.
 * ❌ Pas de DAO.
 * ❌ Pas de calcul du score.
 * ❌ Pas de gestion du chronomètre.
 * ❌ Pas de logique métier complexe.
 *
 * Toutes les actions doivent passer par QuizViewModel.
 *
 * ------------------------------------------------------------
 * 🌐 OFFLINE-FIRST :
 *
 * Les questions du quiz doivent être disponibles depuis
 * les données locales Room.
 *
 * Le quiz doit fonctionner en mode avion.
 *
 * ------------------------------------------------------------
 * ⚠️ ÉTATS À PRÉVOIR :
 *
 * 1. Chargement
 * 2. Quiz prêt
 * 3. Réponse sélectionnée
 * 4. Correction affichée
 * 5. Temps écoulé
 * 6. Quiz terminé
 * 7. Erreur
 * 8. Aucune question disponible
 *
 * ------------------------------------------------------------
 * 🎨 ACCESSIBILITÉ :
 *
 * Respecter :
 *
 * - interface en français ;
 * - texte minimum 14sp ;
 * - contraste suffisant ;
 * - boutons suffisamment grands ;
 * - état sélectionné clairement visible.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - affichage d'une question ;
 * - sélection d'une réponse ;
 * - correction ;
 * - explication ;
 * - passage à la question suivante ;
 * - progression ;
 * - chronomètre ;
 * - fin du temps ;
 * - fin du quiz ;
 * - aucune question disponible.
 *
 * ============================================================
 */

// TODO : créer le composable QuizScreen
// TODO : recevoir l'état fourni par QuizViewModel
// TODO : afficher la question actuelle
// TODO : afficher les quatre propositions
// TODO : afficher la progression
// TODO : afficher le temps restant
// TODO : transmettre la réponse au ViewModel
// TODO : afficher la correction après réponse
// TODO : afficher l'explication
// TODO : permettre de passer à la question suivante
// TODO : gérer le temps écoulé
// TODO : gérer l'état "aucune question"
// TODO : ajouter l'action permettant d'aller au résultat