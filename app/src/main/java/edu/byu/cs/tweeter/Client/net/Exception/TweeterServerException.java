package edu.byu.cs.tweeter.Client.net.Exception;

import java.util.List;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;

public class TweeterServerException extends TweeterRemoteException {


    public TweeterServerException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}
