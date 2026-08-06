package com.nsglobal.queue.common.constant;

import com.nsglobal.queue.common.util.Utilities;

public final class ApiMessages {

    public static final String CREATED =Utilities.isLangFr()?
            "Données créées avec succès":"Date created successfully.";

    public static final String UPDATED =Utilities.isLangFr()?
            "Données modifiées avec succès":"Data updeted successfully";

    public static final String DELETED =Utilities.isLangFr()?
            "Données supprimées avec succès":"Data deleted successfully";
    public static final String DETAILED =Utilities.isLangFr()?
            "Données recupérées avec succès":"Data listed successfully";

    public static final String NOTFOUND =Utilities.isLangFr()?
            "Données introuvable":"Not data found";
    public static final String ALLREADY_EXISTS =Utilities.isLangFr()?
            "Données existent déjà.":"Data allready exists.";
    public static final String INCORRECT_CREDENTIAL =Utilities.isLangFr()?
            "Nom d'utilisateur ou mot de passe incorrecte.":"Invalid password or username.";
    public static final String ACCESS_DENIED =Utilities.isLangFr()?
            "Accès refusé.":"Access denied";
    
    public static final String AGENCY_NOT_FOUND=Utilities.isLangFr()?
    		"Agence introuvable pour creer une branche.":"Agency not found to create bank branch";
public static final String USER_NOT_FOUND=Utilities.isLangFr()?
		"L'utilisateur connecté n'est pas trouvé.":"Current user not found. Retry pease.";
//public static final String DELETE_

    private ApiMessages() {
    }
}
