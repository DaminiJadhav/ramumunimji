package com.posbilling.posbillingapplication.model.realmmodel;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class UserProfileDetails extends RealmObject {
    @Required
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
