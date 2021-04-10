# Spring Batch

## Introduction

- batch : traitement sur un gros volume de données
- fourni un panel d'outils permettant de faciliter le développement de batch
- avantage : propose une structure dans le code très appréciable pour les travaux en équipe
- inconvénient : nécessite d'écrire plusieurs classes même pour un traitement basique
- on parle plutôt de **Job** plutôt que de *batch*
- les concepts
    - **JobLauncher** : chargé d'exécuter un job
        - permet de lancer un job, synchrone ou asynchrone
        - peut-être configué pour s'auto déclencher ou être déclenché par un événement extérieur
    - **Job** : représentation du batch, qui va définir différentes **Step** avec un ordre précis, autour de son exécution
    - **Step** : étape au sein du batch, stocké généralement dans des *Beans*
        - de type **Chunk** : en 3 étapes : lecture (*ItemReader*) / processus (*ItemProcessor*) / écriture (*ItemWriter*)
        - de type **Tasklet** : traitements sans lecture ni écriture de données, par exemple des procédures stockées en base de données
    - **JobRepository** : classe qui va enregistrer des données autour du job
        - notamment les statistiques issues du monitoring sur le JobLauncher, le Job et le/les Step(s)
        - enregistrement dans une base de données, ou dans une Map
    - **JobExplorer** / **Job et Step Listeners** : permet d'effectuer des requêtes sur les données stockées à propos des jobs
- possibilité de paralléliser les traitements


![Architecture de Spring Batch](images/spring-batch-reference-model.png "Architecture de Spring Batch")

## Exemple

- création d'un projet Spring Boot avec la dépendance **Spring Batch**
- annotation de la classe de configuration **@EnableBatchProcessing** pour activer le traitement par lots
