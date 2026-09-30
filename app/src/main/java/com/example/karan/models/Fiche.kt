package com.example.karan.models

/*
 * ============================================================
 * PROJET : RÉVISION BAC / BEPC
 * FICHIER : Fiche.kt
 * ============================================================
 *
 * RESPONSABLE :
 * Mohamed Maciré Soumah — Responsable données
 *
 * ============================================================
 * OBJECTIF DU FICHIER
 * ============================================================
 *
 * Représenter une fiche de révision disponible hors connexion.
 *
 * Une fiche permet à l'élève de réviser un cours ou un chapitre
 * avant ou après avoir réalisé un quiz.
 *
 * ============================================================
 * UTILISATION
 * ============================================================
 *
 * Les fiches seront accessibles depuis les matières.
 *
 * Exemple :
 *
 * Mathématiques
 * ├── Algèbre
 * ├── Géométrie
 * └── Fonctions
 *
 * Français
 * ├── Grammaire
 * ├── Conjugaison
 * └── Littérature
 *
 * ============================================================
 * DONNÉES À PRÉVOIR
 * ============================================================
 *
 * Une fiche doit au minimum contenir :
 *
 * - un identifiant unique ;
 * - l'identifiant de la matière ;
 * - le titre ;
 * - le contenu ;
 * - le chapitre ou thème concerné.
 *
 * On pourra également prévoir :
 *
 * - l'examen concerné (BAC ou BEPC) ;
 * - un ordre d'affichage ;
 * - une date de création ou de mise à jour.
 *
 * ============================================================
 * STRUCTURE ENVISAGÉE
 * ============================================================
 *
 * Exemple conceptuel :
 *
 * Fiche
 * ├── id
 * ├── matiereId
 * ├── examen
 * ├── titre
 * ├── chapitre
 * ├── contenu
 * └── ordre
 *
 * Les noms et types définitifs seront choisis
 * lors de l'implémentation.
 *
 * ============================================================
 * MODE HORS CONNEXION
 * ============================================================
 *
 * Les fiches doivent être enregistrées localement.
 *
 * L'élève doit pouvoir :
 *
 * - ouvrir une fiche sans Internet ;
 * - lire son contenu sans Internet ;
 * - fermer puis rouvrir l'application ;
 * - retrouver les fiches.
 *
 * ============================================================
 * ROOM
 * ============================================================
 *
 * Cette classe sera également utilisée comme entité Room.
 *
 * TODO :
 * [ ] Ajouter l'annotation @Entity
 * [ ] Définir la table "fiches"
 * [ ] Définir la clé primaire
 * [ ] Définir les propriétés nécessaires
 * [ ] Vérifier les types compatibles avec Room
 *
 * ============================================================
 * ORGANISATION DES FICHES
 * ============================================================
 *
 * TODO :
 * [ ] Permettre de retrouver les fiches d'une matière.
 * [ ] Permettre de filtrer selon l'examen.
 * [ ] Permettre de récupérer les fiches dans un ordre logique.
 *
 * ============================================================
 * RÈGLES
 * ============================================================
 *
 * - Pas de logique métier dans ce fichier.
 * - Pas de logique d'interface utilisateur.
 * - Le contenu doit être disponible hors connexion.
 * - Une fiche doit être associée à une matière.
 * - Une fiche doit avoir un titre identifiable.
 *
 * ============================================================
 * TESTS À PRÉVOIR
 * ============================================================
 *
 * [ ] Créer une fiche de test.
 * [ ] Enregistrer la fiche dans Room.
 * [ ] Récupérer la fiche depuis Room.
 * [ ] Vérifier son association avec une matière.
 * [ ] Vérifier que son contenu est correctement conservé.
 * [ ] Vérifier que plusieurs fiches peuvent être enregistrées
 *     pour une même matière.
 *
 * ============================================================
 * APRÈS VALIDATION
 * ============================================================
 *
 * Le modèle sera utilisé par :
 *
 * Fiche.kt
 *      ↓
 * FicheDao.kt
 *      ↓
 * RevisionRepository
 *      ↓
 * MatieresViewModel
 *      ↓
 * MatieresScreen
 *
 * ============================================================
 */

// TODO : créer le modèle Fiche
