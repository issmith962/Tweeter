package edu.byu.cs.tweeter.Client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.service.FeedService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.Client.model.services.FeedServiceProxy;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedPresenter extends Presenter {
    private final View view;

    public FeedService getFeedService() {
        return new FeedServiceProxy();
    }

    public interface View {
        // if needed, specify methods here to be called on view...
    }
    public FeedPresenter(View view) { this.view = view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) throws IOException, TweeterRemoteException {
        return getFeedService().getFeed(request);
    }

    public AuthToken findCurrentAuthToken() {
        return getCurrentAuthToken();
    }
}
