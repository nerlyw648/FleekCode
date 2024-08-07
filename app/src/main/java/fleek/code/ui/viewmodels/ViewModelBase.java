package fleek.code.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelBase extends ViewModel {
    protected MutableLiveData<Object> mutableLiveData;

    public MutableLiveData getData(Object data) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<Object>();
            mutableLiveData.setValue(data);
            update();
        }

        return mutableLiveData;
    }

    public void update() {
        mutableLiveData.postValue(mutableLiveData.getValue());
    }

    public void update(Object data) {
        mutableLiveData.postValue(data);
    }
}