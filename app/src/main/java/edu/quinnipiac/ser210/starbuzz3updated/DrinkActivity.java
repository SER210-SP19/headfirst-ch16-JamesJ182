package edu.quinnipiac.ser210.starbuzz3updated;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {


    public static final String EXTRA_DRINKID="drinkId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkId=(Integer)getIntent().getExtras().get(EXTRA_DRINKID);
        TextView name=(TextView)findViewById(R.id.name);
        TextView description=(TextView)findViewById(R.id.description);
        ImageView photo=(ImageView)findViewById(R.id.photo);
        SQLiteOpenHelper starbuzzDatabaseHelper=new StarbuzzDatabaseHelper(this);
        try
        {
            SQLiteDatabase db=starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor=db.query("DRINK",new String[]{"NAME","DESCRIPTION","IMAGE_RESOURCE_ID"},"_id = ?",
                    new String[]{Integer.toString(drinkId)},
                    null,null,null);
            if(cursor.moveToFirst())
            {
                String nameText=cursor.getString(0);
                String descriptionText=cursor.getString(1);
                int photoId=cursor.getInt(2);
                name.setText(nameText);
                description.setText(descriptionText);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }
            cursor.close();
            db.close();
        }
        catch (SQLException e)
        {
            Toast toast=Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }



    }
}
