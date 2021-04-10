# Introduction Le traitement par lots avec Spring Batch

## Batch processing : principe et objectif

- Implémentations de Batch processing ; Quartz, scripts, Spring Batch
- Spring Batch : présentation, fonctionnalités
- Spring Batch : Architecture
- Spring Batch Concepts

## Spring Batch et SpringBoot

- Rappels Spring Boot : Principales annotations et auto-configuration
- Dépendances Spring pour Spring Batch
- Auto-configuration Spring Batch

## Le DSL de SpringBatch

- Job, JobRepository, JobLauncher
- Step et StepExecution
- Contexte d’exécution
- Item : Reader, Writer, Processor

## Configuration et exécution des Jobs

- Alternatives pour la configuration
- Paramètres de configuration : Redémarrage, Interception, Validation des Paramètres
- Configuration du repository, du Launcher
- Exécution des jobs : en ligne de commande, via un conteneur web

## Configuration des steps

- Chunk et transactions
- TaskletStep
- Séquencement des steps, notion de flow,
- Externalisation du flow, binding des attributs de job et de steps

## Items

- Reader, Writer, Stream
- Fichiers à plats, XML, JSON, Base de données
- Item Reader/Writer personnalisés
- Item Processor, composition de processeurs

## Pour aller plus loin

- Scaling et traitement parallèle
- Répétition et Ré-essais d’exécution
- Tests unitaires
- Le projet Spring Batch Integration
- Surveillance et métriques
