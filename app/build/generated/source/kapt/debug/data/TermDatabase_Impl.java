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
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TermDatabase_Impl implements TermDatabase {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TermDataTable> __insertionAdapterOfTermDataTable;

  private final EntityDeletionOrUpdateAdapter<TermDataTable> __deletionAdapterOfTermDataTable;

  private final EntityDeletionOrUpdateAdapter<TermDataTable> __updateAdapterOfTermDataTable;

  public TermDatabase_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTermDataTable = new EntityInsertionAdapter<TermDataTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `TermDataTable` (`id`,`name`,`startDate`,`endDate`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TermDataTable value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getStartDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getStartDate());
        }
        if (value.getEndDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEndDate());
        }
      }
    };
    this.__deletionAdapterOfTermDataTable = new EntityDeletionOrUpdateAdapter<TermDataTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `TermDataTable` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TermDataTable value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfTermDataTable = new EntityDeletionOrUpdateAdapter<TermDataTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `TermDataTable` SET `id` = ?,`name` = ?,`startDate` = ?,`endDate` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TermDataTable value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getStartDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getStartDate());
        }
        if (value.getEndDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEndDate());
        }
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public long insert(final TermDataTable term) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTermDataTable.insertAndReturnId(term);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final TermDataTable term) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfTermDataTable.handle(term);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final TermDataTable term) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfTermDataTable.handle(term);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public TermDataTable getTerm() {
    final String _sql = "SELECT * FROM termdatatable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final TermDataTable _result;
      if(_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpStartDate;
        _tmpStartDate = _cursor.getString(_cursorIndexOfStartDate);
        final String _tmpEndDate;
        _tmpEndDate = _cursor.getString(_cursorIndexOfEndDate);
        _result = new TermDataTable(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int existTerm() {
    final String _sql = "SELECT COUNT(*) FROM termdatatable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
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
