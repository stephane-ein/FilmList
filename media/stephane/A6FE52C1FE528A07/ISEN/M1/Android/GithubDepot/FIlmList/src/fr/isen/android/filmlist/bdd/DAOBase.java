package fr.isen.android.filmlist.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
	  // Nous sommes � la premi�re version de la base
	  // Si je d�cide de la mettre � jour, il faudra changer cet attribut
	  protected final static int VERSION = 1;
	  // Le nom du fichier qui repr�sente ma base
	  protected final static String NOM = "film_list.db";
	    
	  protected SQLiteDatabase mDb = null;
	  protected DatabaseHandler mHandler = null;
	    
	  public DAOBase(Context pContext) {
	    this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
	  }
	    
	  public void open() {
	    // Pas besoin de fermer la derni�re base puisque getWritableDatabase s'en charge
	    mDb = mHandler.getWritableDatabase();
	  }
	    
	  public void close() {
	    mDb.close();
	  }
	    
	  public SQLiteDatabase getDb() {
	    return mDb;
	  }
	}