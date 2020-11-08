package edu.pucmm.isc581.applogin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.pucmm.isc581.applogin.R;
import edu.pucmm.isc581.applogin.dataModels.OsDataModel;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OsListAdapter extends RecyclerView.Adapter<OsListAdapter.OsViewHolder> {

    private List<OsDataModel> dataModelList;
    private Context mContext;

    @NonNull
    @Override
    public OsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.os_list_item, parent, false);
        return new OsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OsListAdapter.OsViewHolder holder, int position) {
        holder.bindData(dataModelList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public static class OsViewHolder extends RecyclerView.ViewHolder {
        public ImageView androidImage;
        public TextView androidName;
        public TextView apiLevel;

        public OsViewHolder(View itemView){
            super(itemView);
            androidImage = itemView.findViewById(R.id.androidVersionImage);
            androidName = itemView.findViewById(R.id.androidVersionName);
            apiLevel = itemView.findViewById(R.id.apiLevelNumber);
        }

        public void bindData(OsDataModel osDataModel, Context context){
            androidImage.setImageDrawable(context.getDrawable(osDataModel.getImage()));
            androidName.setText(osDataModel.getName());
            apiLevel.setText(osDataModel.getApi_level());
        }
    }
}
