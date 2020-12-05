package edu.pucmm.isc581.applogin.asyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import edu.pucmm.isc581.applogin.azureRelated.ImageHelper;

import java.util.HashMap;
import java.util.List;

public class UploadImages extends AsyncTask<Object, Void, Void> {
    //Recibe un HashMap de nombre de la imagen y el Bitmap, y el applicationContext
    @Override
    protected Void doInBackground(Object... objects) {
        HashMap<String, Bitmap> namesAndImages = (HashMap<String, Bitmap>) objects[2];
        Context appContext = (Context) objects[1];
        namesAndImages.forEach(
                (name, image) -> ImageHelper.storeImageInBlobStorage(appContext, image, name)
        );
    }


}
