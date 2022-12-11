package AlbertusTimothyGunawanJSleepKM.jsleep_android.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.MainActivity;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.R;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Payment;

public class PaymentRenterAdapter extends ArrayAdapter<Payment> {

    public PaymentRenterAdapter(@NonNull Context context, ArrayList<Payment> paymentList) {
        super(context, 0, paymentList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        Payment currPayment = getItem(position);

        TextView roomName = currentItemView.findViewById(R.id.textView1);
        String roomNameText = "Room Name: " + MainActivity.getRoom.get(currPayment.roomId).name.toString();
        roomName.setText(roomNameText);

        TextView date = currentItemView.findViewById(R.id.textView2);
        DateFormat df = new SimpleDateFormat("E, MMMM dd yyyy");
        String fromString = df.format(currPayment.from);
        String toString = df.format(currPayment.to);
        String dateText = fromString  + " - " + toString;
        date.setText(dateText);

        TextView status = currentItemView.findViewById(R.id.textView3);
        String statusText = "Status: " + currPayment.status;
        status.setText(statusText);

        return currentItemView;
    }
}
