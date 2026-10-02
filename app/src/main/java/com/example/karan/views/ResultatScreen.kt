package com.example.karan.views

/*
 * ============================================================
 * RESULTAT SCREEN — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Amadou Bah
 *
 * 🎯 OBJECTIF :
 * ResultatScreen affiche le résultat d'un quiz terminé.
 *
 * Il permet à l'élève de comprendre ses performances et
 * de consulter les informations importantes de sa session.
 *
 * ------------------------------------------------------------
 * 📱 RÔLE DANS LE MVP :
 *
 * Cet écran regroupe :
 *
 * - le score ;
 * - le nombre de bonnes réponses ;
 * - le nombre de mauvaises réponses ;
 * - la correction ;
 * - les explications ;
 * - la progression ;
 * - l'accès à une nouvelle révision.
 *
 * Cela évite de créer plusieurs écrans supplémentaires
 * uniquement pour afficher ces informations.
 *
 * ------------------------------------------------------------
 * 🔄 PARCOURS :
 *
 * QuizScreen
 *      ↓
 * ResultatScreen
 *      ↓
 * AccueilScreen / MatieresScreen
 *
 * ------------------------------------------------------------
 * 🏆 RÉSULTAT :
 *
 * Afficher clairement :
 *
 * - score obtenu ;
 * - nombre de questions ;
 * - bonnes réponses ;
 * - mauvaises réponses ;
 * - éventuellement pourcentage de réussite.
 *
 * Exemple :
 *
 * "Score : 8 / 10"
 *
 * "8 bonnes réponses"
 *
 * "2 mauvaises réponses"
 *
 * ------------------------------------------------------------
 * 📊 PROGRESSION :
 *
 * Le résultat de la session doit pouvoir contribuer à la
 * progression générale de l'élève.
 *
 * Cette progression sera calculée par le ViewModel à partir
 * des données disponibles dans le Repository.
 *
 * ❌ Le Screen ne calcule pas lui-même la progression.
 *
 * ------------------------------------------------------------
 * 📝 CORRECTION :
 *
 * L'élève doit pouvoir revoir les questions auxquelles il
 * a répondu.
 *
 * Pour chaque question, afficher si nécessaire :
 *
 * - l'énoncé ;
 * - la réponse donnée ;
 * - la bonne réponse ;
 * - l'explication.
 *
 * Les questions incorrectes doivent pouvoir être identifiées
 * comme "erreurs à revoir".
 *
 * ------------------------------------------------------------
 * 💾 HISTORIQUE :
 *
 * Une session terminée doit être enregistrée localement.
 *
 * ResultatScreen peut afficher les informations de la session
 * actuelle et éventuellement les dernières performances.
 *
 * L'enregistrement lui-même est effectué par le ViewModel
 * via le Repository.
 *
 * ------------------------------------------------------------
 * 🌐 OFFLINE-FIRST :
 *
 * Le résultat doit fonctionner sans connexion Internet.
 *
 * La session doit être enregistrée dans Room afin de conserver
 * l'historique localement.
 *
 * ------------------------------------------------------------
 * 🔘 ACTIONS :
 *
 * Prévoir des actions permettant :
 *
 * - recommencer un quiz ;
 * - choisir une autre matière ;
 * - retourner à l'accueil ;
 * - éventuellement revoir les erreurs.
 *
 * La navigation réelle sera gérée par AppNavigation.
 *
 * ------------------------------------------------------------
 * 🧠 RESPONSABILITÉ :
 *
 * ResultatScreen affiche uniquement l'état fourni par
 * ResultatViewModel.
 *
 * ❌ Pas de Room.
 * ❌ Pas de DAO.
 * ❌ Pas de calcul du score.
 * ❌ Pas d'enregistrement direct dans Room.
 * ❌ Pas de logique métier complexe.
 *
 * ------------------------------------------------------------
 * ⚠️ ÉTATS À PRÉVOIR :
 *
 * 1. Chargement du résultat
 * 2. Résultat disponible
 * 3. Aucune donnée
 * 4. Erreur de chargement
 *
 * Exemple d'état vide :
 *
 * "Aucun résultat disponible pour le moment."
 *
 * ------------------------------------------------------------
 * 🎨 ACCESSIBILITÉ :
 *
 * Respecter :
 *
 * - interface en français ;
 * - taille minimale de texte : 14sp ;
 * - contraste suffisant ;
 * - informations importantes facilement visibles ;
 * - boutons suffisamment grands ;
 * - hiérarchie visuelle claire.
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - affichage du score ;
 * - bonnes réponses ;
 * - mauvaises réponses ;
 * - correction ;
 * - explications ;
 * - progression ;
 * - enregistrement de la session ;
 * - état vide ;
 * - retour à l'accueil ;
 * - lancement d'une nouvelle révision.
 *
 * ============================================================
 */

// TODO : créer le composable ResultatScreen
// TODO : recevoir l'état fourni par ResultatViewModel
// TODO : afficher le score
// TODO : afficher les bonnes/mauvaises réponses
// TODO : afficher la correction
// TODO : afficher les explications
// TODO : afficher la progression
// TODO : gérer l'état vide
// TODO : ajouter l'action "Recommencer"
// TODO : ajouter l'action "Retour à l'accueil"
// TODO : ajouter l'action "Choisir une autre matière"