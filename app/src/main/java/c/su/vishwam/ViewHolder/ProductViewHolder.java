package c.su.vishwam.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import c.su.vishwam.Interface.ItemClickListener;
import c.su.vishwam.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView patientName, patientProblem,patientTime;
    public ItemClickListener listener;



    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        patientName = (TextView) itemView.findViewById(R.id.patient_name);
        patientProblem = (TextView) itemView.findViewById(R.id.patient_problem);
        patientTime = (TextView) itemView.findViewById(R.id.patient_time);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(),false);
    }
}
