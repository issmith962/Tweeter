package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.GetAllUsersService;
import edu.byu.cs.tweeter.Shared.request.GetAllUsersRequest;
import edu.byu.cs.tweeter.Shared.response.GetAllUsersResponse;

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
