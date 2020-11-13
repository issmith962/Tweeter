package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;

public abstract class Service {
    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
