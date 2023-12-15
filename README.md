# DOCUMENTATION




## Pokedex.py



### Pokemon(DexNum,Name,Types,HP,Atk,Def,SpA,SpD,Spe)

Classe objet Pokémon qui contient un numéro de Pokédex (nombre), un nom (string),
un tuple de types (string) et des statistiques (nombres)


#### __init__(DexNum,Name,Types,HP,Atk,Def,SpA,SpD,Spe)

Constructeur de la classe Pokemon : prends en paramètres le numéro de Pokédex (nombre), 
le nom (string), un tuple de types (string), et les statistiques respectives du Pokémon.

#### get_pokemon()

Affiche une liste détaillée des caractéristiques du Pokémon

#### get_name()

Renvoie le nom du Pokémon

#### get_dexnum()

Renvoie le numéro de pokédex du Pokémon

#### get_types()

Renvoie le tuple de types du Pokémon

#### get_iv()

Renvoie le dictionnaire des statistiques du Pokémon



### Pokedex

Création de chaque objet Pokémon dans un dictionnaire où les clés qui sont les noms des Pokémon sont associées à un objet Pokemon


### liste_types

Création de la liste des types (string) disponibles


### type_image()

Fonction qui renvoie l'image (string du chemin de la ressource) associée à un type (string)




## main.py



### affiche_liste()

Fonction créant la fenêtre du menu d'accueil


#### affiche_pokemon()

Fonction créant la fenêtre d'affichage d'un Pokémon en particulier


#### recherche_in_liste()

Fonction qui recherche la liste des Pokémon correspondante aux éléments saisis et sélectionnés par l'utilisateur 
parmi la string saisie dans la barre de recherche, le type 1 choisi et le type 2 choisi


#### affiche_from_listbox()

Fonction qui fait le lien entre la listbox et la fonction affiche_pokemon



#### liste_poke

Fenêtre d'accueil (objet Tk)


#### pokedex_label

Entête "Pokédex" (objet Label)


#### frame_nom

Frame contenant la barre de recherche (objet Frame)


#### nom_label

Affichage de "Nom :" au dessus de la barre de recherche (objet Label)


#### recherche_texte

Objet contenant le texte de la recherche (objet StringVar)


#### entree2

Barre de recherche (objet Entry)


#### frame_type1

Frame contenant le menu déroulant du choix du type 1 (objet Frame)


#### choix

Affichage de "Type 1 :" au dessus du menu déroulant (objet Label)


#### liste_des_types

Liste contenant les éléments sélectionnables dans le menu déroulant


#### liste_choix

#Menu déroulant 1 (objet Combobox)


#### frame_type2

Frame contenant le menu déroulant du choix du type 2 (objet Frame)


#### choix2

Affichage de "Type 2 :" au dessus du menu déroulant (objet Label)


#### liste_des_types2

Liste contenant les éléments sélectionnables dans le menu déroulant


#### liste_choix2

Menu déroulant 2 (objet Combobox)


#### bouton2

Bouton pour lancer la recherche (objet Button)


#### listbox

Listbox où la liste des Pokémon est affichée (objet Listbox)


#### liste

Liste des éléments contenus dans la listbox


#### bouton_affiche_liste

Bouton Afficher pour ouvrir une fenêtre détaillée d'un Pokémon (objet Button)