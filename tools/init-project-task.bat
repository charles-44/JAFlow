@echo off
chcp 65001 >nul
setlocal ENABLEDELAYEDEXPANSION

REM Prerequisites:
REM GitHub CLI must be installed: gh version
REM
REM You must be authenticated: gh auth login
REM
REM The project charles-44/projects/1 must already exist (type "Projects (beta)", not "classic").
REM
REM Run this .bat file

echo === Initialisation des TODOs JAFlow ===

REM 🔧 Général
gh issue create -t "Définir le nom et la vision du projet (JAFlow 🚀)" -b "Définir un nom de projet clair et une vision synthétique"
gh issue create -t "Écrire le README global" -b "Fournir une vue d’ensemble du projet"
gh issue create -t "Ajouter une licence et des règles de contribution" -b "Choisir une licence et écrire un CONTRIBUTING.md"

REM 🐳 Docker
gh issue create -t "Créer le fichier docker-compose.yml" -b "Définir une stack locale pour le développement"
gh issue create -t "🐳 Ajouter un conteneur Keycloak" -b "Configurer un realm et un client pour l’authentification"
gh issue create -t "🐳 Ajouter un conteneur Postgres" -b "Base de données pour Spring Boot"
gh issue create -t "🐳 Écrire la documentation docker" -b "Ajouter un fichier docker/README.md"

REM 💻 Outils Shell
gh issue create -t "Créer le projet Java pour les commandes" -b "Structure de base pour les utilitaires shell"
gh issue create -t "Implémenter les commandes Docker (start/stop/init)" -b "Faciliter les actions courantes liées à Docker"
gh issue create -t "Implémenter la génération de documentation" -b "Commandes pour Swagger/OpenAPI"
gh issue create -t "Écrire la documentation des shell-tools" -b "Ajouter un fichier shell-tools/README.md"

REM ⚙️ Moteur Spring Boot
gh issue create -t "Initialiser le projet Spring Boot" -b "Créer la structure du projet backend"
gh issue create -t "Configurer la connexion PostgreSQL" -b "Accès aux données via Spring Data"
gh issue create -t "Ajouter les entités de base" -b "WorkflowDefinition, WorkflowInstance, StepDefinition, UserStepDefinition"
gh issue create -t "Créer les services de logique métier" -b "Gestion des transitions, exécution, etc."
gh issue create -t "Configurer la sécurité avec Keycloak" -b "Authentification via OAuth2"
gh issue create -t "Exposer les APIs REST sécurisées" -b "Interfaces d’accès aux données/processus"
gh issue create -t "Écrire la documentation Spring Boot" -b "Ajouter un fichier springboot-workflow/README.md"

REM 🌐 Client Angular
gh issue create -t "Créer le projet Angular 19" -b "Base pour l’interface utilisateur"
gh issue create -t "Intégrer Keycloak avec keycloak-js" -b "Connexion via Keycloak"
gh issue create -t "Créer les composants UI" -b "Interactions avec les workflows"
gh issue create -t "Afficher les workflows et instances" -b "Liste et visualisation"
gh issue create -t "Permettre les actions (start, next step)" -b "Déclenchement d’étapes"
gh issue create -t "Écrire la documentation Angular" -b "Ajouter un fichier angular-cli/README.md"

REM 🧪 Tests & CI/CD
gh issue create -t "Ajouter des tests unitaires (Java/Angular)" -b "Premiers tests pour valider le code"
gh issue create -t "Configurer l’intégration continue" -b "GitHub Actions, GitLab CI ou équivalent"
gh issue create -t "Ajouter des hooks pre-commit" -b "Linting, formatage, tests automatiques"

echo === Fini. Les issues ont été créées. Tu peux maintenant les ajouter manuellement dans ton tableau de projet GitHub.
pause
