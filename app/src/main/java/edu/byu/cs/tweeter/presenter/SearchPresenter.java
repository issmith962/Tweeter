package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.GetAllUsersService;
import edu.byu.cs.tweeter.net.request.GetAllUsersRequest;
import edu.byu.cs.tweeter.net.response.GetAllUsersResponse;

public class SearchPresenter extends Presenter {
    private final View view;

    public interface View {}

    public SearchPresenter(View view) {
        this.view = view;
    }
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        return GetAllUsersService.getInstance().getAllUsers(request);
    }
}
