# 📚 Révision BAC / BEPC

Application Android de révision destinée aux élèves préparant le **BEPC** ou le **BAC**.

L'objectif du projet est de permettre aux élèves de réviser leurs matières à travers des quiz et des fiches de révision, avec un fonctionnement **hors connexion**.

---

## 🎯 Objectifs

L'application permet à l'utilisateur de :

- Choisir entre le **BEPC** et le **BAC**
- Consulter les matières disponibles
- Choisir une matière
- Choisir le nombre de questions
- Répondre à des quiz
- Voir la correction et l'explication après chaque réponse
- Consulter son score
- Suivre sa progression
- Consulter l'historique de ses sessions
- Revoir ses erreurs
- Consulter des fiches de révision
- Utiliser l'application sans connexion Internet

---

## ⭐ Fonctionnalités principales

### 🏠 Accueil

L'écran d'accueil permet de :

- choisir l'examen : BEPC ou BAC ;
- voir la progression ;
- reprendre une révision ;
- accéder aux erreurs à revoir ;
- accéder aux matières.

### 📚 Matières

L'utilisateur peut :

- consulter les matières ;
- sélectionner une matière ;
- consulter les fiches de révision ;
- choisir le nombre de questions ;
- commencer un quiz.

### 📝 Quiz

Le quiz comprend :

- une question à la fois ;
- 4 propositions de réponse ;
- un indicateur de progression ;
- un chronomètre ;
- une correction immédiate ;
- une explication de la réponse ;
- la gestion des erreurs.

### 🏆 Résultat

À la fin du quiz, l'utilisateur peut consulter :

- son score ;
- le nombre de bonnes réponses ;
- le nombre de mauvaises réponses ;
- son pourcentage de réussite ;
- la correction des questions ;
- les explications ;
- sa progression.

---

## 🏗️ Architecture

Le projet utilise l'architecture **MVVM**.

```text
Interface Compose
       ↓
   ViewModel
       ↓
   Repository
       ↓
      DAO
       ↓
  Room Database
````

### Structure du projet

```text
com.example.revisionbacbepc
│
├── models
│   ├── Matiere.kt
│   ├── Question.kt
│   ├── SessionQuiz.kt
│   └── Fiche.kt
│
├── views
│   ├── AccueilScreen.kt
│   ├── MatieresScreen.kt
│   ├── QuizScreen.kt
│   └── ResultatScreen.kt
│
├── viewmodels
│   ├── AccueilViewModel.kt
│   ├── MatieresViewModel.kt
│   ├── QuizViewModel.kt
│   └── ResultatViewModel.kt
│
├── data
│   ├── local
│   │   ├── dao
│   │   │   ├── MatiereDao.kt
│   │   │   ├── QuestionDao.kt
│   │   │   ├── SessionQuizDao.kt
│   │   │   └── FicheDao.kt
│   │   └── AppDatabase.kt
│   │
│   └── repository
│       ├── RevisionRepository.kt
│       └── RevisionRepositoryImpl.kt
│
├── navigation
│   └── AppNavigation.kt
│
└── di
    └── AppContainer.kt
```

---

## 💾 Base de données

L'application utilise **Room** pour stocker les données localement.

Les principales données sont :

* `Matiere`
* `Question`
* `SessionQuiz`
* `Fiche`

### Offline-first

Le fonctionnement principal de l'application ne dépend pas d'Internet.

```text
Application
     ↓
 ViewModel
     ↓
 Repository
     ↓
    DAO
     ↓
   Room
```

Les données doivent rester disponibles après la fermeture et la réouverture de l'application.

---

## 🧩 Technologies

* Kotlin
* Jetpack Compose
* Android
* Room
* ViewModel
* StateFlow
* Navigation Compose
* Git
* GitHub

---

## 👥 Équipe

| Membre                    | Responsabilité                     |
| ------------------------- | ---------------------------------- |
| **N'famory Traore**       | Chef de projet & intégration       |
| **Amadou Bah**            | Interface & expérience utilisateur |
| **Mamadou Alpha Diallo**  | Logique & qualité                  |
| **Mohamed Maciré Soumah** | Données & Room                     |

### Répartition

#### N'famory Traore

* Architecture générale
* Navigation
* Intégration
* Git / GitHub
* Gestion des branches
* Résolution des conflits
* Génération de l'APK finale

#### Amadou Bah

* Interfaces Compose
* Design
* Accessibilité
* États vides
* Expérience utilisateur

#### Mamadou Alpha Diallo

* ViewModels
* StateFlow
* Logique du quiz
* Timer
* Calcul du score
* Validation
* Tests

#### Mohamed Maciré Soumah

* Models / Entities
* DAO
* Room
* Base de données
* Migrations
* Données initiales

---

## 📱 Parcours utilisateur

```text
Accueil
   ↓
Choisir BEPC / BAC
   ↓
Matières
   ↓
Choisir une matière
   ↓
Choisir le nombre de questions
   ↓
Quiz
   ↓
Correction + explication
   ↓
Résultat
   ↓
Progression / erreurs à revoir
```

---

## 📏 Contraintes du MVP

Le projet doit respecter les contraintes suivantes :

* Maximum **3 à 4 écrans principaux**
* Interface en français
* Taille de texte minimale de **14sp**
* Bon contraste
* Fonctionnement hors connexion
* Persistance avec Room
* Architecture MVVM
* Repository derrière une interface
* Utilisation de StateFlow ou LiveData
* Gestion des états vides
* Gestion des erreurs
* Validation claire des entrées
* Aucune donnée sensible dans Git

---

## 🔒 Sécurité

Les données utilisées pendant le développement doivent être fictives.

Ne jamais ajouter dans Git :

* mots de passe réels ;
* clés API ;
* tokens ;
* données personnelles sensibles ;
* informations secrètes.

---

## 🚀 Installation

### Prérequis

* Android Studio
* JDK compatible avec le projet
* Android SDK
* Git

### Cloner le projet

```bash
git clone <URL_DU_REPOSITORY>
```

Puis ouvrir le projet avec Android Studio.

### Lancer l'application

1. Ouvrir le projet avec Android Studio.
2. Synchroniser Gradle.
3. Sélectionner un émulateur ou un appareil Android.
4. Lancer l'application.

---

## 🌿 Git et branches

Chaque membre travaille sur sa propre branche.

Exemple :

```text
main
│
├── feature/interface
├── feature/viewmodels
├── feature/database
└── feature/navigation
```

Avant de fusionner une branche :

1. Vérifier que le projet compile.
2. Tester la fonctionnalité.
3. Récupérer les dernières modifications de `main`.
4. Résoudre les éventuels conflits.
5. Créer une Pull Request.
6. Vérifier le code.
7. Fusionner dans `main`.

---

## 🧪 Tests

Les fonctionnalités importantes doivent être testées :

* chargement des matières ;
* récupération des questions ;
* validation des réponses ;
* calcul du score ;
* fonctionnement du timer ;
* sauvegarde d'une session ;
* récupération de l'historique ;
* calcul de la progression ;
* fonctionnement hors connexion ;
* gestion des données vides ;
* validation des choix utilisateur.

---

## 📌 État du projet

🚧 **Projet en cours de développement.**

### Progression

* [ ] Créer les Models / Entities
* [ ] Créer les DAO
* [ ] Configurer Room
* [ ] Créer le Repository
* [ ] Configurer les dépendances
* [ ] Créer les ViewModels
* [ ] Créer les écrans Compose
* [ ] Configurer la navigation
* [ ] Ajouter les données de test
* [ ] Tester le fonctionnement hors connexion
* [ ] Effectuer les tests finaux
* [ ] Corriger les bugs
* [ ] Générer l'APK finale

---

## 📄 Contexte du projet

Projet réalisé dans le cadre d'une formation en développement Android.

L'objectif est de mettre en pratique :

* Kotlin ;
* Jetpack Compose ;
* MVVM ;
* Room ;
* Repository Pattern ;
* StateFlow ;
* Navigation ;
* Git/GitHub ;
* développement d'une application **offline-first**.

