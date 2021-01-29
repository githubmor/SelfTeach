package data;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile BookDatabase _bookDatabase;

  private volatile ReadDatabase _readDatabase;

  private volatile TermDatabase _termDatabase;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `BookDataTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `pageCount` INTEGER NOT NULL, `priority` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ReadDataTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bookId` INTEGER NOT NULL, `pageReadCount` INTEGER NOT NULL, `readDate` TEXT NOT NULL, FOREIGN KEY(`bookId`) REFERENCES `BookDataTable`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_ReadDataTable_bookId` ON `ReadDataTable` (`bookId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TermDataTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `startDate` TEXT NOT NULL, `endDate` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4f96b6760177c218d1e0e2393d08ccac')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `BookDataTable`");
        _db.execSQL("DROP TABLE IF EXISTS `ReadDataTable`");
        _db.execSQL("DROP TABLE IF EXISTS `TermDataTable`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsBookDataTable = new HashMap<String, TableInfo.Column>(4);
        _columnsBookDataTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookDataTable.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookDataTable.put("pageCount", new TableInfo.Column("pageCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookDataTable.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBookDataTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBookDataTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBookDataTable = new TableInfo("BookDataTable", _columnsBookDataTable, _foreignKeysBookDataTable, _indicesBookDataTable);
        final TableInfo _existingBookDataTable = TableInfo.read(_db, "BookDataTable");
        if (! _infoBookDataTable.equals(_existingBookDataTable)) {
          return new RoomOpenHelper.ValidationResult(false, "BookDataTable(data.BookDataTable).\n"
                  + " Expected:\n" + _infoBookDataTable + "\n"
                  + " Found:\n" + _existingBookDataTable);
        }
        final HashMap<String, TableInfo.Column> _columnsReadDataTable = new HashMap<String, TableInfo.Column>(4);
        _columnsReadDataTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadDataTable.put("bookId", new TableInfo.Column("bookId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadDataTable.put("pageReadCount", new TableInfo.Column("pageReadCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadDataTable.put("readDate", new TableInfo.Column("readDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReadDataTable = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysReadDataTable.add(new TableInfo.ForeignKey("BookDataTable", "CASCADE", "NO ACTION",Arrays.asList("bookId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesReadDataTable = new HashSet<TableInfo.Index>(1);
        _indicesReadDataTable.add(new TableInfo.Index("index_ReadDataTable_bookId", false, Arrays.asList("bookId")));
        final TableInfo _infoReadDataTable = new TableInfo("ReadDataTable", _columnsReadDataTable, _foreignKeysReadDataTable, _indicesReadDataTable);
        final TableInfo _existingReadDataTable = TableInfo.read(_db, "ReadDataTable");
        if (! _infoReadDataTable.equals(_existingReadDataTable)) {
          return new RoomOpenHelper.ValidationResult(false, "ReadDataTable(data.ReadDataTable).\n"
                  + " Expected:\n" + _infoReadDataTable + "\n"
                  + " Found:\n" + _existingReadDataTable);
        }
        final HashMap<String, TableInfo.Column> _columnsTermDataTable = new HashMap<String, TableInfo.Column>(4);
        _columnsTermDataTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTermDataTable.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTermDataTable.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTermDataTable.put("endDate", new TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTermDataTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTermDataTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTermDataTable = new TableInfo("TermDataTable", _columnsTermDataTable, _foreignKeysTermDataTable, _indicesTermDataTable);
        final TableInfo _existingTermDataTable = TableInfo.read(_db, "TermDataTable");
        if (! _infoTermDataTable.equals(_existingTermDataTable)) {
          return new RoomOpenHelper.ValidationResult(false, "TermDataTable(data.TermDataTable).\n"
                  + " Expected:\n" + _infoTermDataTable + "\n"
                  + " Found:\n" + _existingTermDataTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "4f96b6760177c218d1e0e2393d08ccac", "3449732cca59544072681aa8d5fbbb2b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "BookDataTable","ReadDataTable","TermDataTable");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `BookDataTable`");
      _db.execSQL("DELETE FROM `ReadDataTable`");
      _db.execSQL("DELETE FROM `TermDataTable`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BookDatabase.class, BookDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReadDatabase.class, ReadDatabase_Impl.getRequiredConverters());
    _typeConvertersMap.put(TermDatabase.class, TermDatabase_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public BookDatabase bookDatabase() {
    if (_bookDatabase != null) {
      return _bookDatabase;
    } else {
      synchronized(this) {
        if(_bookDatabase == null) {
          _bookDatabase = new BookDatabase_Impl(this);
        }
        return _bookDatabase;
      }
    }
  }

  @Override
  public ReadDatabase readDatabase() {
    if (_readDatabase != null) {
      return _readDatabase;
    } else {
      synchronized(this) {
        if(_readDatabase == null) {
          _readDatabase = new ReadDatabase_Impl(this);
        }
        return _readDatabase;
      }
    }
  }

  @Override
  public TermDatabase termDatabase() {
    if (_termDatabase != null) {
      return _termDatabase;
    } else {
      synchronized(this) {
        if(_termDatabase == null) {
          _termDatabase = new TermDatabase_Impl(this);
        }
        return _termDatabase;
      }
    }
  }
}
