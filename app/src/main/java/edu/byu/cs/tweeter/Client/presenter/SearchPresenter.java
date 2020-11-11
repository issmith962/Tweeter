package edu.byu.cs.tweeter.Client.presenter;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;

public class SearchPresenter extends Presenter {
    private final View view;

    public interface View {}

    public SearchPresenter(View view) {
        this.view = view;
    }
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        return new GetAllUsersResponse("Failure: Feature not allowed for now"); 
        //return (new GetAllUsersServiceProxy()).getAllUsers(request);
    }
    public User findCurrentUser() {
        return getCurrentUser();
    }
}
