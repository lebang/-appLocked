package auto.com.applocked.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import auto.com.applocked.R;
import auto.com.applocked.fn.Utils;
import auto.com.applocked.model.AppInfo;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private ArrayList<AppInfo> mDatas;
    private AppsAdapter.OnItemListener mOnItemListener;
    private Context mContext;

    public AppsAdapter(Context context, ArrayList<AppInfo> datas) {
        mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(auto.com.applocked.R.layout.app_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AppInfo appInfo = mDatas.get(position);
        holder.mIcon.setImageDrawable(appInfo.appIcon);
        holder.mName.setText(appInfo.appName);
        holder.mLayout.setTag(appInfo);
        if (!appInfo.enabled){
            holder.mCheckBox.setChecked(true);
        } else {
            holder.mCheckBox.setChecked(false);
        }
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Utils.disableApp(appInfo.packageName);
                    appInfo.enabled = false;
                } else {
                    Utils.enableApp(appInfo.packageName);
                    appInfo.enabled = true;
                }
            }
        });
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemListener.onItemClick(holder.mLayout, position);
            }
        });
        holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemListener.onItemLongClick(view, position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setOnItemListener(AppsAdapter.OnItemListener listener) {
        this.mOnItemListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mLayout;
        ImageView mIcon;
        TextView mName;
        CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.view_item);
            mIcon = (ImageView) itemView.findViewById(auto.com.applocked.R.id.app_icon);
            mName = (TextView) itemView.findViewById(auto.com.applocked.R.id.app_name);
            mCheckBox = (CheckBox) itemView.findViewById(auto.com.applocked.R.id.app_ck);
        }
    }

    public interface OnItemListener{
        void onItemClick(View view, int positon);
        void onItemLongClick(View view, int positon);
    }
}
