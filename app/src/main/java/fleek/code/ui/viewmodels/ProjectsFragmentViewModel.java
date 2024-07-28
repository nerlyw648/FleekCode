package fleek.code.ui.viewmodels;

import android.os.Parcelable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectsFragmentViewModel extends ViewModel {
    public class Data {
        public Parcelable projectsState;
        public boolean isLoaded = false;
        public Thread loadThread;
    }

    private MutableLiveData<Data> mutableLiveData;

    public MutableLiveData<Data> getData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.setValue(new Data());
            update();
        }

        return mutableLiveData;
    }

    public void update() {
        mutableLiveData.postValue(mutableLiveData.getValue());
    }

    public void update(Data data) {
        mutableLiveData.postValue(data);
    }
}