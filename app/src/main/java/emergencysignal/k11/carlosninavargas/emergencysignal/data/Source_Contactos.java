package emergencysignal.k11.carlosninavargas.emergencysignal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Carlos Nina Vargas on 1/23/2015.
 */
public class Source_Contactos
{
    private MyLite myConn;
    private SQLiteDatabase db;
    private Cursor c;
    private static final String TABLE_NAME = MyLite.TABLE_CONTACTOS;

    public Source_Contactos(Context context)
    {
        myConn = new MyLite(context, null, 1);
        db = myConn.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public long saveRecord(DATA_Contactos myDC)
    {
        ContentValues values = new ContentValues();
        values.put("Phone",myDC.Phone);
        values.put("Message",myDC.Message);
        values.put("Frequency",myDC.Frequency);

        return db.insert(TABLE_NAME,null,values);
    }

    public boolean CheckIfEmpty()
    {
        c = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        if (c.moveToFirst())
        {
            return true;
        } else
        {
            return false;
        }

    }
}
