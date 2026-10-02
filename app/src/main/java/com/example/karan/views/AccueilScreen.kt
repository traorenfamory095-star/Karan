package com.example.karan.views

/*
 * ============================================================
 * ACCUEIL SCREEN — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Amadou Bah
 *
 * 🎯 OBJECTIF :
 * AccueilScreen est l'écran principal de l'application.
 *
 * Il doit permettre à l'élève de :
 *
 * - choisir son examen (BAC / BEPC) ;
 * - voir rapidement sa progression ;
 * - reprendre une révision ;
 * - accéder aux erreurs à revoir ;
 * - accéder aux matières.
 *
 * ------------------------------------------------------------
 * 📱 RÔLE DANS LE MVP :
 *
 * Cet écran joue également le rôle de tableau de bord simple.
 *
 * Il ne faut donc PAS créer un écran supplémentaire uniquement
 * pour afficher la progression ou l'historique.
 *
 * ------------------------------------------------------------
 * 🔄 PARCOURS :
 *
 * AccueilScreen
 *      ↓
 * MatieresScreen
 *      ↓
 * QuizScreen
 *      ↓
 * ResultatScreen
 *
 * ------------------------------------------------------------
 * 🧩 ÉLÉMENTS À PRÉVOIR :
 *
 * 1. EN-TÊTE
 *    - nom de l'application ;
 *    - message court destiné à l'élève.
 *
 * 2. CHOIX DE L'EXAMEN
 *    - BEPC ;
 *    - BAC.
 *
 * 3. PROGRESSION
 *    - progression générale ;
 *    - éventuellement progression par matière.
 *
 * 4. REPRENDRE LA RÉVISION
 *    - dernière matière travaillée ;
 *    - bouton pour continuer.
 *
 * 5. ERREURS À REVOIR
 *    - nombre de questions incorrectes ;
 *    - bouton pour les revoir.
 *
 * 6. ACCÈS AUX MATIÈRES
 *    - bouton permettant d'aller vers MatieresScreen.
 *
 * ------------------------------------------------------------
 * 🎨 INTERFACE :
 *
 * L'interface doit respecter les exigences du projet :
 *
 * - langue française ;
 * - textes lisibles ;
 * - taille minimale recommandée : 14sp ;
 * - bon contraste ;
 * - boutons clairement identifiables ;
 * - interface adaptée au mobile.
 *
 * Prévoir également des états vides.
 *
 * Exemple :
 *
 * "Aucune donnée pour le moment"
 *
 * lorsqu'un nouvel utilisateur n'a encore aucune session.
 *
 * ------------------------------------------------------------
 * 🧠 RESPONSABILITÉ :
 *
 * Ce Screen affiche uniquement l'état fourni par
 * AccueilViewModel.
 *
 * ❌ Pas de requête Room.
 * ❌ Pas d'accès au DAO.
 * ❌ Pas de logique métier.
 * ❌ Pas de calcul de progression.
 * ❌ Pas de calcul de score.
 *
 * Le ViewModel fournit les données nécessaires à l'interface.
 *
 * ------------------------------------------------------------
 * 🔘 ACTIONS :
 *
 * Prévoir des callbacks pour :
 *
 * - choisir l'examen ;
 * - ouvrir les matières ;
 * - reprendre une révision ;
 * - ouvrir les erreurs à revoir.
 *
 * La navigation réelle sera gérée par AppNavigation.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - affichage correct du BAC ;
 * - affichage correct du BEPC ;
 * - état vide lorsque aucune session n'existe ;
 * - affichage de la progression ;
 * - fonctionnement des boutons ;
 * - navigation vers les matières.
 *
 * ============================================================
 */

// TODO : créer le composable AccueilScreen
// TODO : recevoir l'état fourni par AccueilViewModel
// TODO : afficher le choix BAC / BEPC
// TODO : afficher la progression
// TODO : afficher la reprise de révision
// TODO : afficher les erreurs à revoir
// TODO : gérer les états vides
// TODO : ajouter les callbacks de navigation