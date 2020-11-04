package edu.byu.cs.tweeter.Client.presenter;

public class CheckUserFollowingPresenter extends Presenter {
    private final View view;
    public interface View {
        // if needed, specify methods here to be called on view
    }
    public CheckUserFollowingPresenter(View view) {
        this.view = view;
    }

}
