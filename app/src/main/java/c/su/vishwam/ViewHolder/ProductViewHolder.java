package c.su.vishwam.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import c.su.vishwam.Interface.ItemClickListener;
import c.su.vishwam.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView patientName, patientProblem,patientDateTime,patientAge,patientSex,patientPhone,patientOthers;
    public ItemClickListener listener;
    public ImageView imageView;



    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);


        patientName = (TextView) itemView.findViewById(R.id.patient_name);
        patientProblem = (TextView) itemView.findViewById(R.id.patient_problem);
        patientDateTime = (TextView) itemView.findViewById(R.id.patient_date_time);
        patientAge = (TextView) itemView.findViewById(R.id.patient_age);
        patientSex = (TextView) itemView.findViewById(R.id.patient_sex);
        patientPhone = (TextView) itemView.findViewById(R.id.patient_phone_number);
        patientOthers = (TextView) itemView.findViewById(R.id.patient_other_details);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(),false);
    }
}
