# Dap-rma

Creation d'une app client-serveur dans le cadre de la formation house of code Lyon.
Ce projet utilise les API Google (GMAIL, CALENDAR) et permettra de lire les emails non lus, la liste des labels
dans GMAIL, le prochain événement dans CALENDAR.



## Getting started

Ce projet est un projet Java utilisant Spring et Maven. Vous devez donc disposez d'un IDE au choix type Intellij ou Eclipse.



### Mise en place

Une fois le projet récupéré et qu'il s'affiche sur votre IDE, voici les étapes à suivre pour installer :



#### La BDD

Dans le fichier application.properties (Pour le trouver aller dans src -> main -> java -> resources)
insérer les lignes suivantes :

```
spring.datasource.url=jdbc:mysql://localhost:[le port de votre BDD]/[le nom du schéma]?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=[votre username]
spring.datasource.password=[votre password]
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

spring.jpa.database=MYSQL
hibernate.dialect=org.hibernate.dialect.MySQLInnoDBDialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb

#Afficher les requetes
spring.jpa.show-sql=true

#Créer automatiquement la source de données
spring.jpa.generate-ddl=true

#Laisser démarrer l'application en cas d'erreurs
spring.datasource.continue-on-error=true
```
Avant de démarrer Spring, il faudra utiliser un outil de création de BDD type Mysql Workbench, PhpMyAdmin.
Une fois cet outil installé, en supposant que vous avez quelques notions (sinon je vous encourage à regarder 
rapidement leur fonctionnement), créez un nouveau schéma avec le nom qui vous conviendra ('Dap-bdd' pour rester 
cohérent sinon).
Une fois cela fait, vous disposez normalement de toutes les données manquantes pour finaliser la configuration Spring
de votre BDD.



#### Les autorisations Google

Ici rien de bien sorcier, mais quelques manipulations à faire correctement.

1. La première chose à faire est de créer un dossier 'Dap' (la majuscule sur le 'D' est importante !) dans votre user.home
2. Puis à l'intérieur de ce dossier Dap, il faut créer un dossier 'tokens'.
3. Une fois cela réalisé, rendez-vous sur Google developers console -> https://console.developers.google.com
4. Si vous n'avez pas de compte, il faut en créer un. Puis rendez-vous dans l'onglet bibliothèque et activez les APIS GMAIL et GOOGLE CALENDAR.
5. Ensuite rendez-vous dans l'onglet identifiant et cliquer sur créer des identifiants -> ID Client OAuth -> choisir application web
6. Dans la fenêtre qui apparaît donnez un nom à votre identifiant puis séléctionnez ajouter une URI. Il faut alors indiquer l'uri suivante :

  ```
  http://localhost:8080/oAuth2Callback
  ```
  
 7. Enfin cliquer sur enregistrer.
 8. Télécharger ensuite cet identifiant nouvellement créé (sur la ligne de l'identifiant tout à droite)
 9. Renommer l'identifiant télécharger ainsi : 'credentials.json' et le placer dans le dossier Dap (mais pas dans le dossier tokens)
 10. Vous êtes normalement bon pour lancer le server.
 
 
 
 #### Le lancement de l'application
 
 Vous allez enfin pouvoir faire un run de l'application. Une fois cela fait, quelques étapes sont à suivre pour l'utiliser correctement :
 
 1. Rendez vous sur -> http://localhost/user/all
 Vous devriez voir que vous n'avez aucun user encore présent.
 2. Ajouter un user -> http://localhost/user/add?name=jose
 Un user devrait être ajouter.Vérifier le en vous rendant à l'url indiquer dans l'étape 1.
 Vérifier également en BDD, normalement votre user devrait être ajouter dans la table user (pensez à rafraichir votre BDD ;) )
 3. Ajouter un compte affilié à votre user -> http://localhost/account/add/jose
 4. Vous devriez avoir une page Google vous demandant de rentrer vos identifiants Google. 
 5. Désormais vous pouvez utiliser les url suivantes :
  a. http://localhost/email/nbUnread?userKey=jose   (pour lire votre nombre d'email non lus)
  b. http://localhost/label/print?userKey=jose   (pour lire vos labels de votre boite gmail)
  c. http://localhost/event/next?userKey=jose   (pour lire votre prochain événement sur Google calendar)
  
 !! Bien entendu vous pouvez remplacer 'jose' par un autre prénom de votre choix !! 
 


