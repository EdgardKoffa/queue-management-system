package com.nsglobal.queue.common.constant;

import com.nsglobal.queue.common.util.Utilities;

public final class ApiMessages {

    
    public static final String AGENCY_NOT_FOUND=Utilities.isLangFr()?
    		"Agence introuvable pour creer une branche.":"Agency not found to create bank branch";

    //
    public static final String NOTFOUND_BRANCH =Utilities.isLangFr()?
            "Sous agence introuvable":"Bank branch not found";
    public static final String EXIST_BRANCH =Utilities.isLangFr()?
            "Sous agence existe déjà":"Bank branch already exists";
    
   
    public static final String CREATED =Utilities.isLangFr()?
            "Données créées avec succès":"Data created successfully.";
    public static final String SUCCESS =Utilities.isLangFr()?
            "Opération réussite avec succès":"Success operation.";
   
    public static final String UPDATED =Utilities.isLangFr()?
            "Données modifiées avec succès":"Data updeted successfully";
    public static final String DELETED =Utilities.isLangFr()?
            "Données supprimées avec succès":"Data deleted successfully";
    public static final String DETAILED =Utilities.isLangFr()?
            "Données recupérées avec succès":"Data listed successfully";
    public static final String NOTFOUND =Utilities.isLangFr()?
            "Données introuvable":"No data found";
    public static final String ALLREADY_EXISTS =Utilities.isLangFr()?
            "Données existent déjà.":"Data allready exists.";
    public static final String INCORRECT_CREDENTIAL =Utilities.isLangFr()?
            "Nom d'utilisateur ou mot de passe incorrecte.":"Invalid password or username.";
    public static final String ACCESS_DENIED =Utilities.isLangFr()?
            "Accès refusé.":"Access denied";
    public static final String USER_NOT_FOUND=Utilities.isLangFr()?
    		"L'utilisateur connecté n'est pas trouvé.":"Current user not found. Retry pease.";
    
    /*Counter messages*/
    public static final String NOTFOUND_COUNTER =Utilities.isLangFr()?
            "Guichet introuvable":"Counter not found";
    public static final String EXIST_COUNTER =Utilities.isLangFr()?
            "Guichet introuvable":"Counter not found";
    public static final String ALREADY_OPENED_COUNTER =Utilities.isLangFr()?
            "Le guichet %s N° %d est déjà en service":"Counter %s N° %d  already opened";
    public static final String ALREADY_CLOSED_COUNTER =Utilities.isLangFr()?
            "Le guichet %s N° %d est déjà fermé.":"Counter %s N° %d  already closed.";
    public static final String ALL_ALREADY_CLOSED_COUNTER =Utilities.isLangFr()?
            "Aucun guichet  n'est ouvert.":"No opened counter.";
   
    public static final String ALL_ALREADY_OPENED_COUNTER =Utilities.isLangFr()?
            "Aucun guichet  n'est fermé.":"No closed counter.";
    
    

    public static final String FAILURE_OPEN_COUNTER =Utilities.isLangFr()?
            "Echec d'ouverture du guichet. Cause: %s":"Counter opening failed. Cause %s";
    public static final String ALL_FAILURE_OPEN_COUNTER =Utilities.isLangFr()?
            "Echec d'ouverture de tous les guichet. Cause: %s":"All counter opening failed. Cause %s";

    public static final String FAILURE_CLOSE_COUNTER =Utilities.isLangFr()?
            "Echec de fermeture du guichet. Cause: %s":"Counter closing failed. Cause %s";
    public static final String ALL_FAILURE_CLOSE_COUNTER =Utilities.isLangFr()?
            "Echec de fermeture de tous les guichet. Cause: %s":"All counter closing failed. Cause %s";
   
    public static final String BUSY_COUNTER =Utilities.isLangFr()?
            "Le guichet %s N° %d a un traitement encours.":"Counter  %s N° %d has one cuurent service";
    public static final String OPERATOR_ALREADY_ASSIGNED=Utilities.isLangFr()?
    		"L'opérateur déjà assigné à un guichet":"Operator already assigned to a counter.";
    public static final String COUNTER_ALREADY_OPERATOR=Utilities.isLangFr()?
    		"Le guichet a déjà opérateur assigné":"Counter has already an Operator assigned.";
    public static final String COUNTER_FAIL_OPERATOR=Utilities.isLangFr()?
    		"Echec d'assignation d'un opérateur au guichet. Cause: %s":"Fail to assign operator. Cause: %s";
    public static final String OPERATOR_NOT_FOUND=Utilities.isLangFr()?
    		"Cet opérateur est introuvable.":"Operator not found";
    public static final String OPERATOR_RELEASED=Utilities.isLangFr()?
    		"L'opérateur %s est retiré du guichet %s .":"Operator %s is released from counter %s";
    public static final String OPERATOR_NOT_ASSIGNED=Utilities.isLangFr()?
    		"Aucun opérateur n'est assigné à cet guichet.":"No operator is assigned for counter";
    public static final String COUNTER_IN_USE=Utilities.isLangFr()?
    		"Le guichet est occupé par un opérateur.":"Counter is currently in use";
    
    public static final String COUNTER_FAIL_RELEASE=Utilities.isLangFr()?
    		"Echec de retraction de l'opérateur au guichet. Cause: %s":"Fail to release operator. Cause: %s";
    

    //
    //---------------------------------------------------------------------------------------
   //public static final String DELETE_

    private ApiMessages() {
    }
}
