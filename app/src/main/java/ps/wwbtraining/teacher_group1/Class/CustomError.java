package ps.wwbtraining.teacher_group1.Class;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ps.wwbtraining.teacher_group1.R;

/**
 * Created by osama on 17/10/2017.
 */

public class CustomError {
    public interface OnReloadClickListener {
        boolean OnReloadClickListener(View view);

    }

    OnReloadClickListener listener;

    public void Show_Toast(Context context, View view, String error, final OnReloadClickListener listener) {
        this.listener = listener;
        // Layout Inflater for inflating custom view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the layout over view
        View layout = inflater.inflate(R.layout.custom_error,
                (ViewGroup) view.findViewById(R.id.toast_root));

        // Get TextView id and set error
        TextView text = (TextView) layout.findViewById(R.id.toast_error);
        text.setText(error);

        Toast toast = new Toast(context);// Get Toast Context
        toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);// Set
        // Toast
        // gravity
        // and
        // Fill
        // Horizoontal
        Button button = (Button) layout.findViewById(R.id.reload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnReloadClickListener(view);
            }
        });
        toast.setDuration(Toast.LENGTH_LONG);// Set Duration
        toast.setView(layout); // Set Custom View over toast

        toast.show();// Finally show toast
    }

}
