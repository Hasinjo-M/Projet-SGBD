Requete pour avoir tout les tables :
	les tables

Requete pour supprimer une table :
	supprime nomdeTable

Requete pour supprimer tout les tables existants
	supprime all
	 
Requet create table : 
	create table Personne ( id:int,Nom:String )
	create table Vente ( idObjet:int,id:int )
	create table Produit ( idObject:int,NomObject:String )

Requete insertion dans un table : 
	insert into Personne ( id,Nom,Anniverssaire ) values ( 2,Toavina,23/06/04 )
		

Requete Projection d'un table : (pas de condition)
	select . id,Nom from Personne

Requete select * : ( avec une seule condition )
	selects * from Personne
	selects * from Object where id = 3

Requete pour faire un jointure : ( sans condition )
	select * from nomTable1,nomTable2 on Personne:id = Achat:id

Requete pour faire une union de 2 tables :
	union select Union Personne et Olona

Requete pour faire une Intersection de 2 tables :
	select Intersection Personne et Olona

Requete pour faire produit cartesienne de 2 tables :
	select cartesienne ( Personne,Produit )

	
Requete pour quitter le serveur 
	exit 