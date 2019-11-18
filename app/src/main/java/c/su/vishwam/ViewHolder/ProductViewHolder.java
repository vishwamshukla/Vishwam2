package c.su.vishwam.ViewHolder;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import c.su.vishwam.Interface.ItemClickListener;
import c.su.vishwam.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView patientName, patientProblem,patientDate,patientTime,patientAge,patientSex,patientPhone,patientOthers,bed,ward, chatName, chatType;
    public ItemClickListener listener;
    public ImageView imageView;
    public Button HandoverToDoctor,TransferIpd;
    public CircleImageView chatImage;



    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);


        patientName = (TextView) itemView.findViewById(R.id.patient_name);
        patientProblem = (TextView) itemView.findViewById(R.id.patient_problem);
        patientDate = (TextView) itemView.findViewById(R.id.patient_date);
        patientTime = (TextView) itemView.findViewById(R.id.patient_time);
        patientAge = (TextView) itemView.findViewById(R.id.patient_age);
        patientSex = (TextView) itemView.findViewById(R.id.patient_sex);
        patientPhone = (TextView) itemView.findViewById(R.id.patient_phone_number);
        patientOthers = (TextView) itemView.findViewById(R.id.patient_other_details);
        TransferIpd = (Button) itemView.findViewById(R.id.transfer_to_ipd);
        HandoverToDoctor = (Button) itemView.findViewById(R.id.handover);
        bed = (TextView) itemView.findViewById(R.id.patient_bed_no);
        ward = (TextView) itemView.findViewById(R.id.patient_ward_no);
        imageView = (ImageView) itemView.findViewById(R.id.emergency_image);
        chatName = (TextView) itemView.findViewById(R.id.all_user_fullname);
        chatType = (TextView) itemView.findViewById(R.id.all_user_type);
        chatImage = (CircleImageView) itemView.findViewById(R.id.all_user_profile_image);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(),false);
    }

    public void chatImage(Context applicationContext, String image) {
        CircleImageView circleImageView = imageView.findViewById(R.id.all_user_profile_image);
        Picasso.get().load(image).into(circleImageView);
    }
}
