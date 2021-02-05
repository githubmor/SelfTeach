package data;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ReadDatabase_Impl implements ReadDatabase {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReadDataTable> __insertionAdapterOfReadDataTable;

  private final EntityDeletionOrUpdateAdapter<ReadDataTable> __deletionAdapterOfReadDataTable;

  public ReadDatabase_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReadDataTable = new EntityInsertionAdapter<ReadDataTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `ReadDataTable` (`id`,`bookId`,`pageReadCount`,`readDate`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ReadDataTable value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getBookId());
        stmt.bindLong(3, value.getPageReadCount());
        if (value.getReadDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getReadDate());
        }
      }
    };
    this.__deletionAdapterOfReadDataTable = new EntityDeletionOrUpdateAdapter<ReadDataTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `ReadDataTable` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ReadDataTable value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public long insertRead(final ReadDataTable readDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfReadDataTable.insertAndReturnId(readDataTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteRead(final ReadDataTable readDataTable) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfReadDataTable.handle(readDataTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<ReadBookNameDataTable> getAllReadsWithBookName() {
    final String _sql = "SELECT readdatatable.*,bookdatatable.name FROM readdatatable LEFT JOIN bookdatatable ON readdatatable.bookId = bookdatatable.id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "bookId");
      final int _cursorIndexOfPageReadCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageReadCount");
      final int _cursorIndexOfReadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "readDate");
      final int _cursorIndexOfBookName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final List<ReadBookNameDataTable> _result = new ArrayList<ReadBookNameDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ReadBookNameDataTable _item;
        final String _tmpBookName;
        if (_cursor.isNull(_cursorIndexOfBookName)) {
          _tmpBookName = null;
        } else {
          _tmpBookName = _cursor.getString(_cursorIndexOfBookName);
        }
        final ReadDataTable _tmpReadDataTable;
        if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfBookId) && _cursor.isNull(_cursorIndexOfPageReadCount) && _cursor.isNull(_cursorIndexOfReadDate))) {
          final int _tmpId;
          _tmpId = _cursor.getInt(_cursorIndexOfId);
          final int _tmpBookId;
          _tmpBookId = _cursor.getInt(_cursorIndexOfBookId);
          final int _tmpPageReadCount;
          _tmpPageReadCount = _cursor.getInt(_cursorIndexOfPageReadCount);
          final String _tmpReadDate;
          if (_cursor.isNull(_cursorIndexOfReadDate)) {
            _tmpReadDate = null;
          } else {
            _tmpReadDate = _cursor.getString(_cursorIndexOfReadDate);
          }
          _tmpReadDataTable = new ReadDataTable(_tmpId,_tmpBookId,_tmpPageReadCount,_tmpReadDate);
        }  else  {
          _tmpReadDataTable = null;
        }
        _item = new ReadBookNameDataTable(_tmpReadDataTable,_tmpBookName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
