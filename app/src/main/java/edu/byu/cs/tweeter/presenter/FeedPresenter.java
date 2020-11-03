package edu.byu.cs.tweeter.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.model.services.FeedService;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;

public class FeedPresenter extends Presenter {
    private final View view;

    public interface View {
        // if needed, specify methods here to be called on view...
    }
    public FeedPresenter(View view) { this.view = view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) {
        return FeedService.getInstance().getFeed(request);
    }
}
