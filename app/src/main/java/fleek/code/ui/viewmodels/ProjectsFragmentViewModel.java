package fleek.code.ui.viewmodels;

import android.os.Parcelable;

public class ProjectsFragmentViewModel extends ViewModelBase {
    public static class Data {
        public Parcelable projectsState;
        public boolean isLoaded = false;
        public Thread loadThread;
    }
}