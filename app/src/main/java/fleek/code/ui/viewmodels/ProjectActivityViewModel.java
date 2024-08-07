package fleek.code.ui.viewmodels;

import fleek.code.models.Project;

public class ProjectActivityViewModel extends ViewModelBase {
    public static class Data {
        public Project project;
        public boolean isLoaded = false;
        public boolean isLoading = false;
        public Thread loadThread;
    }
}
