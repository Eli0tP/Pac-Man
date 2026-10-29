# Pac-Man en Java

Reproduction du jeu Pac-Man en Java (Swing), réalisée pour apprendre la programmation orientée objet.

## Fonctionnalités

- Labyrinthe chargé depuis un fichier texte (`src/map.txt`) : murs, pac-gommes, super pac-gommes, téléporteurs
- Déplacement au clavier sur une grille de 32 px, avec gestion des collisions contre les murs
- Quatre fantômes au comportement différent : chacun a sa propre probabilité de poursuivre Pac-Man
  (choix de la case la plus proche selon la distance euclidienne), sinon il se déplace au hasard
- Super pac-gomme : les fantômes fuient Pac-Man et peuvent être mangés (+200 points)
- Cerises bonus, score, écran de victoire et de fin de partie
- Effets sonores (début de partie, pac-gommes, fruits, fantômes, mort)

## Technologies

Java · Swing / AWT · `javax.sound.sampled` · programmation orientée objet (interface `GameObject`, héritage)

## Lancer le jeu

Prérequis : un JDK (version 8 ou plus). Depuis la racine du dépôt :

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

Le jeu doit être lancé depuis la racine du dépôt pour trouver les dossiers `resources/`, `son/` et `src/map.txt`.

## Commandes

Flèches du clavier : déplacer Pac-Man.

## Structure

```
src/
  Main.java             fenêtre du jeu
  GamePanel.java        boucle de jeu, chargement de la carte, collisions, score
  Ghost.java            déplacement et comportement des fantômes
  game_items/           interface GameObject et classe PacMan
  Points.java, Cherry.java, SuperPoint.java, Wall.java, Teleporter.java ...
  map.txt               carte du niveau
resources/              sprites
son/                    effets sonores
```
