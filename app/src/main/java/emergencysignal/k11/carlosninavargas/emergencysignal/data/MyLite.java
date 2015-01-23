package emergencysignal.k11.carlosninavargas.emergencysignal.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
/**
 * Created by Carlos Nina Vargas on 1/23/2015.
 */
public class MyLite extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "ESignal.s3db";
    private static File BASE_PATH = Environment.getDataDirectory();
    public static final String DATA_DIRNAME = "K11Data";
    public static final String DATABASE_PATH = Environment.getDataDirectory().getAbsolutePath() + File.separator + DATA_DIRNAME + File.separator + DATABASE_NAME;

    public static final int DATABASE_VERSION = 1;

    // Table List
    public static final String TABLE_CONTACTOS = "Contactos";
    public static final String TABLE_CONFIG = "Config";

    // Creation SQL
    private static final String DATABASE_CREATE_CONTACTOS = "CREATE TABLE [Contactos] ( [ID] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,  [Nombre] VARCHAR(200)  NULL,   [Phone] VARCHAR(16)  NOT NULL, [Message] VARCHAR(200)  NOT NULL, [Frequency] INTEGER DEFAULT '0' NOT NULL )";
    private static final String DATABASE_CREATE_CONFIG = "CREATE TABLE [Config] ( [Pass] VARCHAR(100)  NOT NULL, [KeysCode] INTEGER  NOT NULL )";

    // Constructor here
    public MyLite(Context contexto, SQLiteDatabase.CursorFactory factory, int version) {
        //super(contexto, nombre, factory, version);
        super(new ContextWrapper(contexto) {
            @Override public SQLiteDatabase openOrCreateDatabase(String name,
                                                                 int mode, SQLiteDatabase.CursorFactory factory) {

                // allow database directory to be specified
                File dir = new File(BASE_PATH.getAbsolutePath() + File.separator + DATA_DIRNAME);
                if(!dir.exists()) {
                    dir.mkdirs();
                }
                return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
                        SQLiteDatabase.CREATE_IF_NECESSARY);
            }
        }, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Executing all SQL Queries for creation
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE_CONTACTOS);
        db.execSQL(DATABASE_CREATE_CONFIG);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva)
    {
        // Destroying everything
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIG);

        // Calling to re-create everything
        onCreate(db);
    }

}
