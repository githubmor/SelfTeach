package data;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BookDatabase_Impl implements BookDatabase {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BookDataTable> __insertionAdapterOfBookDataTable;

  private final EntityDeletionOrUpdateAdapter<BookDataTable> __deletionAdapterOfBookDataTable;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllBook;

  public BookDatabase_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBookDataTable = new EntityInsertionAdapter<BookDataTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `BookDataTable` (`id`,`name`,`pageCount`,`priority`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BookDataTable value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getPageCount());
        stmt.bindLong(4, value.getPriority());
      }
    };
    this.__deletionAdapterOfBookDataTable = new EntityDeletionOrUpdateAdapter<BookDataTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `BookDataTable` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BookDataTable value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllBook = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM bookdatatable";
        return _query;
      }
    };
  }

  @Override
  public long insertBook(final BookDataTable bookDataTable) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfBookDataTable.insertAndReturnId(bookDataTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteBook(final BookDataTable bookDataTable) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfBookDataTable.handle(bookDataTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAllBook() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllBook.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllBook.release(_stmt);
    }
  }

  @Override
  public List<BookReadsDataTable> getAllBookWithListReads() {
    final String _sql = "SELECT * FROM bookdatatable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
        final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
        final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageCount");
        final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
        final LongSparseArray<ArrayList<ReadDataTable>> _collectionReadDataTableLists = new LongSparseArray<ArrayList<ReadDataTable>>();
        while (_cursor.moveToNext()) {
          final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
          ArrayList<ReadDataTable> _tmpReadDataTableListsCollection = _collectionReadDataTableLists.get(_tmpKey);
          if (_tmpReadDataTableListsCollection == null) {
            _tmpReadDataTableListsCollection = new ArrayList<ReadDataTable>();
            _collectionReadDataTableLists.put(_tmpKey, _tmpReadDataTableListsCollection);
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipReadDataTableAsdataReadDataTable(_collectionReadDataTableLists);
        final List<BookReadsDataTable> _result = new ArrayList<BookReadsDataTable>(_cursor.getCount());
        while(_cursor.moveToNext()) {
          final BookReadsDataTable _item;
          final BookDataTable _tmpBookDataTable;
          if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfPageCount) && _cursor.isNull(_cursorIndexOfPriority))) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpPageCount;
            _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            _tmpBookDataTable = new BookDataTable(_tmpId,_tmpName,_tmpPageCount,_tmpPriority);
          }  else  {
            _tmpBookDataTable = null;
          }
          ArrayList<ReadDataTable> _tmpReadDataTableListsCollection_1 = null;
          final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
          _tmpReadDataTableListsCollection_1 = _collectionReadDataTableLists.get(_tmpKey_1);
          if (_tmpReadDataTableListsCollection_1 == null) {
            _tmpReadDataTableListsCollection_1 = new ArrayList<ReadDataTable>();
          }
          _item = new BookReadsDataTable(_tmpBookDataTable,_tmpReadDataTableListsCollection_1);
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<BookSumReadDataTable> getAllBookWithSumRead() {
    final String _sql = "SELECT bookdatatable.*,SUM(readdatatable.pageReadCount) as readSum FROM bookdatatable INNER JOIN readdatatable ON bookdatatable.id = readdatatable.bookId";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageCount");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfReadSum = CursorUtil.getColumnIndexOrThrow(_cursor, "readSum");
      final List<BookSumReadDataTable> _result = new ArrayList<BookSumReadDataTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final BookSumReadDataTable _item;
        final int _tmpReadSum;
        _tmpReadSum = _cursor.getInt(_cursorIndexOfReadSum);
        final BookDataTable _tmpBookDataTable;
        if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfPageCount) && _cursor.isNull(_cursorIndexOfPriority))) {
          final int _tmpId;
          _tmpId = _cursor.getInt(_cursorIndexOfId);
          final String _tmpName;
          if (_cursor.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _cursor.getString(_cursorIndexOfName);
          }
          final int _tmpPageCount;
          _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
          final int _tmpPriority;
          _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
          _tmpBookDataTable = new BookDataTable(_tmpId,_tmpName,_tmpPageCount,_tmpPriority);
        }  else  {
          _tmpBookDataTable = null;
        }
        _item = new BookSumReadDataTable(_tmpBookDataTable,_tmpReadSum);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int existBook() {
    final String _sql = "SELECT COUNT(*) FROM bookdatatable";
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

  private void __fetchRelationshipReadDataTableAsdataReadDataTable(
      final LongSparseArray<ArrayList<ReadDataTable>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<ReadDataTable>> _tmpInnerMap = new LongSparseArray<ArrayList<ReadDataTable>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipReadDataTableAsdataReadDataTable(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<ReadDataTable>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipReadDataTableAsdataReadDataTable(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`bookId`,`pageReadCount`,`readDate` FROM `ReadDataTable` WHERE `bookId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "bookId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "bookId");
      final int _cursorIndexOfPageReadCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageReadCount");
      final int _cursorIndexOfReadDate = CursorUtil.getColumnIndexOrThrow(_cursor, "readDate");
      while(_cursor.moveToNext()) {
        final long _tmpKey = _cursor.getLong(_itemKeyIndex);
        ArrayList<ReadDataTable> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final ReadDataTable _item_1;
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
          _item_1 = new ReadDataTable(_tmpId,_tmpBookId,_tmpPageReadCount,_tmpReadDate);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
