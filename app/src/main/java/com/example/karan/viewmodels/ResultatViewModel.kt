package com.example.karan.viewmodels

/*
 * ============================================================
 * RESULTAT VIEWMODEL — INSTRUCTIONS DE TRAVAIL
 * ============================================================
 *
 * 👤 Responsable : Mamadou Alpha Diallo
 *
 * 🎯 OBJECTIF :
 * ResultatViewModel contient la logique nécessaire à
 * ResultatScreen.
 *
 * Il récupère les informations du quiz terminé depuis le
 * Repository et prépare l'état affiché à l'utilisateur.
 *
 * ------------------------------------------------------------
 * 🔄 FLUX :
 *
 * ResultatScreen
 *      ↓
 * ResultatViewModel
 *      ↓
 * RevisionRepository
 *      ↓
 * SessionQuizDao / autres DAO nécessaires
 *      ↓
 * Room
 *
 * ------------------------------------------------------------
 * 📊 DONNÉES À GÉRER :
 *
 * Le ViewModel devra notamment pouvoir fournir :
 *
 * - le score ;
 * - le nombre total de questions ;
 * - le nombre de bonnes réponses ;
 * - le nombre de mauvaises réponses ;
 * - le pourcentage de réussite ;
 * - la matière ;
 * - l'examen ;
 * - les informations de correction ;
 * - la progression ;
 * - les erreurs à revoir ;
 * - l'état de chargement ;
 * - les éventuelles erreurs.
 *
 * ------------------------------------------------------------
 * 🏆 SCORE :
 *
 * Le ViewModel doit récupérer ou calculer les informations
 * nécessaires à l'affichage du résultat.
 *
 * Exemple :
 *
 * 8 / 10
 * 80 %
 *
 * ⚠️ Le calcul ne doit pas être effectué directement dans
 * ResultatScreen.
 *
 * ------------------------------------------------------------
 * 📝 CORRECTION :
 *
 * Le ViewModel doit préparer les données nécessaires pour
 * permettre à l'élève de revoir les questions du quiz.
 *
 * Pour chaque question, pouvoir afficher si nécessaire :
 *
 * - l'énoncé ;
 * - la réponse donnée ;
 * - la bonne réponse ;
 * - l'explication.
 *
 * ------------------------------------------------------------
 * 📈 PROGRESSION :
 *
 * Le ViewModel peut utiliser les sessions enregistrées
 * localement pour fournir les informations de progression.
 *
 * Exemple :
 *
 * - nombre de quiz réalisés ;
 * - réussite moyenne ;
 * - progression d'une matière.
 *
 * Le calcul exact devra être défini avec l'équipe afin
 * d'éviter de dupliquer la logique.
 *
 * ------------------------------------------------------------
 * 💾 HISTORIQUE :
 *
 * La session du quiz terminé doit être persistée dans Room.
 *
 * ResultatViewModel peut récupérer les informations de cette
 * session afin de les afficher.
 *
 * ------------------------------------------------------------
 * 🌊 STATEFLOW :
 *
 * Utiliser StateFlow pour exposer un état unique au Screen.
 *
 * L'état pourra contenir :
 *
 * - loading ;
 * - résultat disponible ;
 * - correction ;
 * - progression ;
 * - erreur ;
 * - état vide.
 *
 * ------------------------------------------------------------
 * 🧠 RESPONSABILITÉ :
 *
 * ResultatViewModel contains the logic necessary to prepare
 * result data.
 *
 * ------------------------------------------------------------
 * 🚫 À NE PAS FAIRE :
 *
 * ❌ Pas de code Compose.
 * ❌ Pas d'accès direct aux DAO.
 * ❌ Pas d'accès direct à Room.
 * ❌ Pas de navigation UI.
 *
 * Le ViewModel dépend de :
 *
 * RevisionRepository
 *
 * et non directement de :
 *
 * SessionQuizDao
 *
 * ------------------------------------------------------------
 * ⚠️ ÉTATS À PRÉVOIR :
 *
 * 1. Chargement
 * 2. Résultat disponible
 * 3. Correction disponible
 * 4. Aucune donnée
 * 5. Erreur
 *
 * Exemple :
 *
 * "Aucun résultat disponible pour le moment."
 *
 * ------------------------------------------------------------
 * 🧪 TESTS À PRÉVOIR :
 *
 * Vérifier :
 *
 * - récupération d'une session ;
 * - affichage du score ;
 * - calcul du pourcentage ;
 * - bonnes/mauvaises réponses ;
 * - récupération de la correction ;
 * - récupération de la progression ;
 * - gestion d'une session inexistante ;
 * - gestion des erreurs ;
 * - mise à jour du StateFlow.
 *
 * ============================================================
 */

// TODO : créer l'UI State du résultat
// TODO : créer ResultatViewModel
// TODO : injecter RevisionRepository
// TODO : récupérer la session terminée
// TODO : préparer les informations du score
// TODO : préparer le pourcentage de réussite
// TODO : préparer les données de correction
// TODO : récupérer les informations de progression
// TODO : gérer l'historique nécessaire
// TODO : gérer les états loading / success / error / empty
