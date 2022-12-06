Requete pour avoir tout les tables :
	les tables

Requete pour supprimer une table :
	supprime nomdeTable

Requete pour supprimer tout les tables existants
	supprime all
	 
Requet create table : 
	create table Personne ( id:int,Nom:String,Anniversaire:Date )
	create table Vente ( id:int,NomProduit,idPersonne:int )

Requete insertion dans un table : 
	insert into Personne ( id,Nom,Anniverssaire ) values ( 2,Toavina,23/06/04 )
		

Requete Projection d'un table : (pas de condition)
	select . id,Nom from Personne

Requete select * : ( avec une seule condition )
	selects * from Personne
	selects * from Object where id = 3

Requete pour faire un jointure : ( sans condition )
	select * from nomTable1,nomTable2 on Personne:id = Achat:id


	
Requete pour quitter le serveur 
	exit 