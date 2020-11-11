package edu.byu.cs.tweeter.Client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.Client.model.services.PostStatusServiceProxy;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

/**
 * The presenter for the main activity.
 */
public class MainPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter sends notifications to it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public MainPresenter(View view) {
        this.view = view;
    }

    public void updateCurrentUser(User user) {
        setCurrentUser(user);
    }
    public User findCurrentUser() {
        return getCurrentUser();
    }

    public void updateCurrentAuthToken(AuthToken authToken) {
        setCurrentAuthToken(authToken);
    }

    public AuthToken findCurrentAuthToken() {
        return getCurrentAuthToken();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        return (new PostStatusServiceProxy()).postStatus(request);
    }

}
