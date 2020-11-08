package edu.byu.cs.tweeter.Client.net.Exception;

import java.util.List;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;

public class TweeterRequestException extends TweeterRemoteException {

    public TweeterRequestException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}