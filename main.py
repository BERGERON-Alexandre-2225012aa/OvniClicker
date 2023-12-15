from Pokedex import *
import tkinter as tk
from tkinter import ttk
import os
from PIL import Image, ImageTk

##########################################################################################################

def affiche_liste():
    '''Fonction créant la fenêtre du menu d'accueil'''
    def affiche_pokemon(nom_pokemon):
        '''Fonction créant la fenêtre d'affichage d'un Pokémon en particulier'''
        fichiers =  [f for f in os.listdir("home_sprites")]
        fichiers.sort()
        pokemons_list = [item0 for item0 in Pokedex.keys()]
        for i in range(len(pokemons_list)):
            if pokemons_list[i] == nom_pokemon:
                sprite = Image.open("home_sprites/" + fichiers[i])
                resized_sprite = sprite.resize((100,100), Image.ANTIALIAS)
                new_sprite = ImageTk.PhotoImage(resized_sprite)
        pokemon = tk.Toplevel()
        pokemon.geometry("450x600")
        pokemon["bg"] = "#A2A2A2"
        pokemon.iconbitmap("images/logo.ico")
        pokemon.title(nom_pokemon)
        pokemon.resizable(height=False, width=False)
        nom = tk.Label(pokemon, text = nom_pokemon, font = ("Arial", 20), foreground = "white", background = "#A2A2A2")
        nom.place(x = "17", y = "10")
        num = tk.Label(pokemon, text = Pokedex[nom_pokemon].get_dexnum(), font = ("Arial", 20), background = "#A2A2A2")
        num.place(x = "325", y = "130")
        if Pokedex[nom_pokemon].get_types()[1] == None:
            type1_photo = tk.PhotoImage(file = type_image(Pokedex[nom_pokemon].get_types()[0]))
            type1 = tk.Label(pokemon, image = type1_photo)
            type1.place(x = "25", y = "55")
        else:
            type1_photo = tk.PhotoImage(file = type_image(Pokedex[nom_pokemon].get_types()[0]))
            type1 = tk.Label(pokemon, image = type1_photo)
            type1.place(x = "20", y = "55")
            type2_photo = tk.PhotoImage(file = type_image(Pokedex[nom_pokemon].get_types()[1]))
            type2 = tk.Label(pokemon, image = type2_photo)
            type2.place(x = "100", y = "55")
        tableau_stats_photo = tk.PhotoImage(file = "images/tableaustats.png")
        tableau_stats = tk.Label(pokemon, image = tableau_stats_photo)
        tableau_stats.place(x = "95", y = "230")
        HP = tk.Label(pokemon, text = str(Pokedex[nom_pokemon].get_iv()["HP"]), font = ("Arial", 20), background = "#22B14C")
        HP.place(x = "278", y = "243")
        Atk = tk.Label(pokemon, text = str(Pokedex[nom_pokemon].get_iv()["Atk"]), font = ("Arial", 20), background = "#FFF200")
        Atk.place(x = "278", y = "296")
        Def = tk.Label(pokemon, text = str(Pokedex[nom_pokemon].get_iv()["Def"]), font = ("Arial", 20), background = "#FFC90E")
        Def.place(x = "278", y = "350")
        SpA = tk.Label(pokemon, text = str(Pokedex[nom_pokemon].get_iv()["SpA"]), font = ("Arial", 20), background = "#99D9EA")
        SpA.place(x = "278", y = "402")
        SpD = tk.Label(pokemon, text = str(Pokedex[nom_pokemon].get_iv()["SpD"]), font = ("Arial", 20), background = "#7092BE")
        SpD.place(x = "278", y = "455")
        Spe = tk.Label(pokemon, text = str(Pokedex[nom_pokemon].get_iv()["Spe"]), font = ("Arial", 20), background = "#A349A4")
        Spe.place(x = "278", y = "507")
        canvas_sprite = tk.Canvas(pokemon, width = 120, height = 120, bg = "#A2A2A2")
        canvas_sprite.place(x = "160", y = "90")
        canvas_sprite.create_image(10, 10, anchor = tk.NW, image = new_sprite)
        pokemon.mainloop()
    
    def recherche_in_liste():
        '''Fonction qui recherche la liste des Pokémon correspondante aux éléments saisis et sélectionnés par l'utilisateur 
        parmi la string saisie dans la barre de recherche, le type 1 choisi et le type 2 choisi'''
        listbox.delete(0, tk.END)
        liste = []
        for i in Pokedex.keys():
            liste.append(Pokedex[i].get_name() + " " + Pokedex[i].get_dexnum())
        texte = recherche_texte.get()
        type1 = liste_choix.get()
        type2 = liste_choix2.get()
        for item in liste:
            if texte in item:
                if type1 == "Tous" and type2 == "Tous":
                    listbox.insert(tk.END, item)
                elif type1 == "Tous" and type2 == "Aucun":
                    if Pokedex[item.split(" #")[0]].get_types()[0] == None or Pokedex[item.split(" #")[0]].get_types()[1] == None:
                        listbox.insert(tk.END, item) 
                elif type1 == "Tous":
                    if Pokedex[item.split(" #")[0]].get_types()[0] == type2 or Pokedex[item.split(" #")[0]].get_types()[1] == type2:
                        listbox.insert(tk.END, item)
                elif type2 == "Tous":
                    if Pokedex[item.split(" #")[0]].get_types()[0] == type1 or Pokedex[item.split(" #")[0]].get_types()[1] == type1:
                        listbox.insert(tk.END, item)
                elif type2 == "Aucun":
                    if (Pokedex[item.split(" #")[0]].get_types()[0] == type1 or Pokedex[item.split(" #")[0]].get_types()[1] == type1) and (Pokedex[item.split(" #")[0]].get_types()[0] == None or Pokedex[item.split(" #")[0]].get_types()[1] == None):
                        listbox.insert(tk.END, item)
                elif type1 == type2 and type1 != "Tous" and type2 != "Tous":
                    if (Pokedex[item.split(" #")[0]].get_types()[0] == type1 or Pokedex[item.split(" #")[0]].get_types()[1] == type1) and (Pokedex[item.split(" #")[0]].get_types()[0] == None or Pokedex[item.split(" #")[0]].get_types()[1] == None):
                        listbox.insert(tk.END, item)
                else:
                    if (Pokedex[item.split(" #")[0]].get_types()[0] == type1 or Pokedex[item.split(" #")[0]].get_types()[1] == type1) and (Pokedex[item.split(" #")[0]].get_types()[0] == type2 or Pokedex[item.split(" #")[0]].get_types()[1] == type2):
                        listbox.insert(tk.END, item)
                        
    def affiche_from_listbox():
        '''Fonction qui fait le lien entre la listbox et la fonction affiche_pokemon'''
        if listbox.get(tk.ANCHOR) != "":
            anchor = listbox.get(tk.ANCHOR).split(" #")
            affiche_pokemon(anchor[0])
    
    liste_poke = tk.Tk() #Fenêtre d'accueil (objet Tk)
    liste_poke.geometry("450x600")
    liste_poke["bg"] = "#A2A2A2"
    liste_poke.iconbitmap("images/logo.ico")
    liste_poke.title("Pokédex by GruZbob")
    liste_poke.resizable(height = False, width = False)
    pokedex_label = tk.Label(liste_poke, text = "Pokédex", font = ("Arial", 20), fg = "#CE1414", bg = "#A2A2A2") #Entête "Pokédex" (objet Label)
    pokedex_label.pack(pady = 30)
    frame_nom = tk.Frame(liste_poke, bg = "#A2A2A2") #Frame contenant la barre de recherche (objet Frame)
    frame_nom.pack(pady = 10)
    nom_label = tk.Label(frame_nom, text = "Nom :", bg = "#A2A2A2") #Affichage de "Nom :" au dessus de la barre de recherche (objet Label)
    nom_label.pack()
    recherche_texte = tk.StringVar() #Objet contenant le texte de la recherche (objet StringVar)
    entree2 = tk.Entry(frame_nom, textvariable = recherche_texte) #Barre de recherche (objet Entry)
    entree2.pack()
    
    frame_type1 = tk.Frame(liste_poke, bg = "#A2A2A2") #Frame contenant le menu déroulant du choix du type 1 (objet Frame)
    frame_type1.pack(pady = 10)
    choix = tk.Label(frame_type1, text = "Type 1 :", bg = "#A2A2A2") #Affichage de "Type 1 :" au dessus du menu déroulant (objet Label)
    choix.pack()
    liste_des_types = ["Tous"] #Liste contenant les éléments sélectionnables dans le menu déroulant
    for thing in liste_types:
        liste_des_types.append(thing)
    liste_choix = tk.ttk.Combobox(frame_type1, values = liste_des_types) #Menu déroulant 1 (objet Combobox)
    liste_choix.current(0)
    liste_choix.pack()
    
    frame_type2 = tk.Frame(liste_poke, bg = "#A2A2A2") #Frame contenant le menu déroulant du choix du type 2 (objet Frame)
    frame_type2.pack(pady = 10)
    choix2 = tk.Label(frame_type2, text = "Type 2 :", bg = "#A2A2A2") #Affichage de "Type 2 :" au dessus du menu déroulant (objet Label)
    choix2.pack()
    liste_des_types2 = ["Tous", "Aucun"] #Liste contenant les éléments sélectionnables dans le menu déroulant
    for thing2 in liste_types:
        liste_des_types2.append(thing2)
    liste_choix2 = tk.ttk.Combobox(frame_type2, values = liste_des_types2) #Menu déroulant 2 (objet Combobox)
    liste_choix2.current(0)
    liste_choix2.pack()
    
    bouton2 = tk.Button(liste_poke, text = "Rechercher", command = recherche_in_liste) #Bouton pour lancer la recherche (objet Button)
    bouton2.pack(side = tk.TOP, pady = 10)
    
    listbox = tk.Listbox(liste_poke, width = 40) #Listbox où la liste des Pokémon est affichée (objet Listbox)
    listbox.pack(pady = 25)
    liste = [] #Liste des éléments contenus dans la listbox
    for i in Pokedex.keys():
        liste.append(Pokedex[i].get_name() + " " + Pokedex[i].get_dexnum())
    for item in liste:
        listbox.insert(tk.END, item)
        
    bouton_affiche_liste = tk.Button(liste_poke, text = "Afficher", command = affiche_from_listbox) 
    #Bouton Afficher pour ouvrir une fenêtre détaillée d'un Pokémon (objet Button)
    bouton_affiche_liste.pack()
    liste_poke.mainloop()

affiche_liste()
