package com.nsglobal.queue.common.enums;

public enum CounterStatus {
	OPEN,//Disponible pour appeler un ticke

	CLOSED,//Guichet fermé, ne reçoit aucun client

	BUSY,//Traite actuellement un client

	PAUSED,//Pause temporaire de l'opérateur
	
	RESUME,//reprendre apres une pause

	OUT_OF_SERVICE//Guichet indisponible (maintenance, panne)

}
