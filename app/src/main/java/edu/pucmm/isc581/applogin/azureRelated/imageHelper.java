package edu.pucmm.isc581.applogin.azureRelated;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;

public class imageHelper {
    private static final String storageURL = "BLOB_STORAGE_URL";
    private static final String storageContainer = "NAME_OF_BLOB_STORAGE_CONTAINER";
    private static final String storageConnectionString = "BLOB_STORAGE_CONNECTION_STRING";

    protected void storeImageInBlobStorage(String imgPath){
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference(storageContainer);

            // Create or overwrite the blob (with the name "[CURRENT_UNIX_TIME].jpeg") with contents from a local file.
            CloudBlockBlob blob = container.getBlockBlobReference(Long.toString(System.currentTimeMillis() / 1000L) +".jpg");
            File source = new File(imgPath);
            blob.upload(new FileInputStream(source), source.length());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
