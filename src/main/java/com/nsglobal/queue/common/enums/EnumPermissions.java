package com.nsglobal.queue.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum EnumPermissions {
	
	COUNTER_OPEN("Autorisation pour ouvrir un guichet"),
	COUNTER_CLOSE("Autorisation pour fermer un guichet"),
	COUNTER_ASSIGN("Autorisation pour assigner un operateur a un guichet"),
	COUNTER_RELEASE("Autorisation pour liberer un guichet de son operateur"),
	TICKET_CREATE("Autorisation pour creer un ticket"),
	TICKET_CALL("Autorisation pour appeler un ticket"),
	TICKET_TRANSFER("Autorisation pour transferer un ticket vers un autre guichet"),
	TICKET_FINISH("Autorisation pour completer un ticket"),
	TICKET_CANCEL("Autorisation pour annuler un ticket"),
	VIEW_DASHBOARD("Autorisation pour avoir acces au tableu de bord"),
	//MANAGE_APPOINTMENTS("Autorisation pour  un guichet"),
	MANAGE_BRANCHS("Autorisation pour gerer une agence"),
	MANAGE_SERVICE("Autorisation pour gerer un service"),
	MANAGE_AGENCY("Autorisation pour gerer l'agence principale"),
	VIEW_REPORTS("Voir les rapports"),
	VIEW_DETAIL("Voir les details"),
	VIEW_LIST("Autorisation pour visualiser les listes"),
	MANAGE_USERS("Autorisation pour gestion des utilisateurs"),
	MANAGE_ROLES("Autorisation pour gestion  des roles et permission"),
	SEND_NOTIFICATION("Envoyer un message"),
	VIEW_DISPLAY("Affichager sur ecran: LED, KISOK...");
	
	@Setter
	@Getter
	private String description;
}
