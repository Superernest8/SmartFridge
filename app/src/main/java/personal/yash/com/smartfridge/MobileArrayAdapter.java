package personal.yash.com.smartfridge;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MobileArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final int[] quantities;

    public MobileArrayAdapter(Context context, String[] values, int[] quantities) {
        super(context, R.layout.list_item, values);
        this.context = context;
        this.values = values;
        this.quantities = quantities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values[position]);

        ProgressBar pBar = (ProgressBar)rowView.findViewById(R.id.progressBar);


        // Change icon based on name
        String s = values[position];

        System.out.println(s);


            pBar.setProgress(quantities[position]);



        return rowView;
    }
}