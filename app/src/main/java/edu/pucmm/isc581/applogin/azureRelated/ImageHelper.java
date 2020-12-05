package edu.pucmm.isc581.applogin.azureRelated;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageHelper {
    public static final String storageURL = "BLOB_STORAGE_URL";
    private static final String storageContainer = "temaepeciale";
    private static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=temaepeciale;AccountKey=htBnfcaqXj8nMFgXWuGT+9h+mLOkOKFH64DXVILCtljVDGf3T3iyF6v6YYbwp3HbhCZ35N9da9FIvUxDp71cxQ==;EndpointSuffix=core.windows.net";

    public static Boolean storeImageInBlobStorage(Context ApplicationContext, Bitmap bitmap, String filename){
        try {
            if (filename.isEmpty())
                filename = Long.toString(System.currentTimeMillis() / 1000L) + ".jpg";

            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference(storageContainer);

            // Create or overwrite the blob (with the name "[CURRENT_UNIX_TIME].jpeg") with contents from a local file.
            File f = new File(ApplicationContext.getCacheDir(),  filename);
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            CloudBlockBlob blob = container.getBlockBlobReference(filename);
            File source = new File(f.getAbsolutePath());
            blob.upload(new FileInputStream(source), source.length());
            return true;
        }
        catch (Exception e) {
            Log.wtf("QEXCEPTION_IMAGEEN:", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
