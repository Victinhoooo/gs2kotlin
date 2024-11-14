package br.com.fiap.JoseVictor_rm95663

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TipsDatabaseHelper(appContext: Context) : SQLiteOpenHelper(appContext, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "EcoTipsDB"
        private const val DB_VERSION = 1
        private const val TIPS_TABLE = "Tips"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_DESCRIPTION = "description"
    }

    override fun onCreate(database: SQLiteDatabase) {
        val createTableStatement = """
            CREATE TABLE $TIPS_TABLE (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COL_TITLE TEXT, 
                $COL_DESCRIPTION TEXT
            )
        """
        database.execSQL(createTableStatement)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $TIPS_TABLE")
        onCreate(database)
    }

    // Função para buscar todas as dicas
    fun fetchAllTips(): List<EcoTip> {
        val dbInstance = readableDatabase
        val cursor = dbInstance.rawQuery("SELECT * FROM $TIPS_TABLE", null)
        val tipsList = mutableListOf<EcoTip>()

        with(cursor) {
            while (moveToNext()) {
                val title = getString(getColumnIndexOrThrow(COL_TITLE))
                val description = getString(getColumnIndexOrThrow(COL_DESCRIPTION))
                tipsList.add(EcoTip(title, description))
            }
            close()
        }
        return tipsList
    }

    // Função para adicionar uma nova dica
    fun addTip(tip: EcoTip): Long {
        val values = ContentValues().apply {
            put(COL_TITLE, tip.title)
            put(COL_DESCRIPTION, tip.description)
        }
        val dbInstance = writableDatabase
        return dbInstance.insert(TIPS_TABLE, null, values)
    }
}
