package com.example.loansapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loansapp.database.DatabaseHelper;
import com.example.loansapp.model.Loan;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller-klasse til håndtering af Loan-objekter mod databasen.
 */
public class LoanController {

    private DatabaseHelper dbHelper;

    public LoanController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Opretter og gemmer et Loan-objekt i databasen.
     */
    public long createLoan(Loan loan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_BRAND, loan.getTabletBrand());
        values.put(DatabaseHelper.COL_CABLE, loan.getCableType());
        values.put(DatabaseHelper.COL_NAME, loan.getBorrowerName());
        values.put(DatabaseHelper.COL_CONTACT, loan.getBorrowerContact());
        values.put(DatabaseHelper.COL_TIME, loan.getLoanTime());
        values.put(DatabaseHelper.COL_RETURNED, loan.isReturned() ? 1 : 0);

        long id = db.insert(DatabaseHelper.TABLE_LOANS, null, values);
        db.close();
        return id;
    }

    /**
     * Henter alle Loans (som ikke er markeret som returned).
     */
    public List<Loan> getAllActiveLoans() {
        List<Loan> loans = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DatabaseHelper.COL_RETURNED + " = ?";
        String[] selectionArgs = {"0"};  // 0 = false (ikke returneret)

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_LOANS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                DatabaseHelper.COL_ID + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Loan loan = new Loan();
                loan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)));
                loan.setTabletBrand(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_BRAND)));
                loan.setCableType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CABLE)));
                loan.setBorrowerName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)));
                loan.setBorrowerContact(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CONTACT)));
                loan.setLoanTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TIME)));
                loan.setReturned(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RETURNED)) == 1);

                loans.add(loan);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return loans;
    }

    /**
     * Opdaterer et Loan-objekt, sætter returned = 1.
     */
    public void markLoanReturned(int loanId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_RETURNED, 1);
        String where = DatabaseHelper.COL_ID + " = ?";
        String[] whereArgs = {String.valueOf(loanId)};
        db.update(DatabaseHelper.TABLE_LOANS, values, where, whereArgs);
        db.close();
    }

    /**
     * Henter alle Loans baseret på filter for brand og kabeltype (hvis != "Alle").
     */
    public List<Loan> filterLoans(String brand, String cable) {
        List<Loan> loans = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Bygger betingelse
        // Vi vil kun vise ikke-returnerede
        StringBuilder selection = new StringBuilder(DatabaseHelper.COL_RETURNED + " = 0");
        List<String> argsList = new ArrayList<>();

        if (!brand.equals("Alle")) {
            selection.append(" AND " + DatabaseHelper.COL_BRAND + " = ?");
            argsList.add(brand);
        }
        if (!cable.equals("Alle")) {
            selection.append(" AND " + DatabaseHelper.COL_CABLE + " = ?");
            argsList.add(cable);
        }

        String[] selectionArgs = argsList.toArray(new String[0]);

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_LOANS,
                null,
                selection.toString(),
                selectionArgs,
                null,
                null,
                DatabaseHelper.COL_ID + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Loan loan = new Loan();
                loan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)));
                loan.setTabletBrand(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_BRAND)));
                loan.setCableType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CABLE)));
                loan.setBorrowerName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)));
                loan.setBorrowerContact(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CONTACT)));
                loan.setLoanTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TIME)));
                loan.setReturned(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RETURNED)) == 1);

                loans.add(loan);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return loans;
    }
}
