package vn.edu.stu.doanck.ultil;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBconfigUtil {
    final static String DATABASE_NAME = "database.db";
    final static String DB_PATH_SUNFFIX = "/databases/";

    public static void copyDatabaseFromAssets(Context context){
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            File dbDir = new File(context.getApplicationInfo().dataDir + DB_PATH_SUNFFIX);
            if(!dbDir.exists()){
                dbDir.mkdir();
            }
            InputStream is =null;
            OutputStream os = null;
            try {
                is=context.getAssets().open(DATABASE_NAME);
                String outputFilePath = context.getApplicationInfo().dataDir + DB_PATH_SUNFFIX + DATABASE_NAME;
                os = new FileOutputStream(outputFilePath);
                byte[] buffer = new byte[2000];
                int length;
                while ((length = is.read(buffer)) > 0){
                    os.write(buffer,0,length);
                }
                os.flush();
                Toast.makeText(
                        context,
                        "Đã chép CSDL",
                        Toast.LENGTH_LONG
                ).show();
            }
            catch (Exception e){
                Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            }
            finally {
                try{
                    os.close();
                }catch (IOException e){
                }
                try{
                    is.close();
                }catch (IOException e){
            }
        }
    }

}
}