package edu.quinnipiac.ser210.starbuzz3updated;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        ListView listDrinks=(ListView)findViewById(R.id.list_drinks);
        StarbuzzDatabaseHelper starbuzzDatabaseHelper=new StarbuzzDatabaseHelper(this);
        try
        {
            db=starbuzzDatabaseHelper.getReadableDatabase();
            cursor=db.query("DRINK",new String[]{"_id","NAME"},null,null,null,null,null);
            SimpleCursorAdapter listAdapter=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},0);
            listDrinks.setAdapter(listAdapter);
        }
        catch (SQLException e)
        {
            Toast toast=Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Intent intent=new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
                    intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int)id);
                    startActivity(intent);
                }
            }
        };
        listDrinks.setOnItemClickListener(itemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
