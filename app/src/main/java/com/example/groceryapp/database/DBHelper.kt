package com.example.groceryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.groceryapp.models.Product
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, KEY_DATABASE_NAME, null, KEY_DATABASE_VERSION) {
    var db: SQLiteDatabase = this.writableDatabase
    var mList: ArrayList<Product> = ArrayList()


    companion object {
        const val KEY_DATABASE_NAME = "mygrocerydb"
        const val KEY_DATABASE_VERSION = 2
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_IMAGE = "image"
        const val TABLE_NAME = "cart"
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        var createTable =
            "create table $TABLE_NAME ($COLUMN_ID integer, $COLUMN_NAME char(50), $COLUMN_PRICE float, $COLUMN_MRP float, $COLUMN_QUANTITY integer, $COLUMN_IMAGE integer)"
        sqLiteDatabase.execSQL(createTable)
        Log.d("abc", "OnCreate")
    }

    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        var dropTable = "drop table cart"
        sqliteDatabase!!.execSQL(dropTable)
        onCreate(sqliteDatabase)
        Log.d("abc", "OnUpgrade")
    }

    fun addProduct(product: Product) {

        if (!isItemInCart(product)) {
            var sqLiteDatabase = writableDatabase

            var contentValue = ContentValues()
            contentValue.put(COLUMN_ID, product._id)
            contentValue.put(COLUMN_NAME, product.productName)
            contentValue.put(COLUMN_PRICE, product.price)
            contentValue.put(COLUMN_MRP, product.mrp)
            contentValue.put(COLUMN_QUANTITY, 1)
            contentValue.put(COLUMN_IMAGE, product.image)

            sqLiteDatabase.insert(TABLE_NAME, null, contentValue)
            Log.d("abc", "addProduct()")
        } else {
            updateQuantity(product,1)
        }
    }

    fun increaseQuantity(product: Product){
        //product.quantity!! + 1
        updateQuantity(product,1)

    }

    fun reduceQuantity(product: Product){
       // product.quantity!! - 1
        updateQuantity(product, -1)
    }

    fun updateQuantity(product: Product, count: Int) {
        var currentQuantity = getCurrentQuantity(product)

        var contentValues = ContentValues()
        var whereClause = "$COLUMN_NAME = ?"
        var whereArgument = arrayOf(product.productName)
        var db = writableDatabase
        contentValues.put(COLUMN_QUANTITY, count + currentQuantity)
        db.update(TABLE_NAME, contentValues, whereClause, whereArgument)

    }

    fun getCurrentQuantity(product: Product): Int{
        var currentQuantity = 0
        var whereClause = "$COLUMN_NAME = ?"
        var whereArgument = arrayOf(product.productName)

        var db = writableDatabase
        var column = arrayOf(COLUMN_QUANTITY)
        var cursor = db.query(TABLE_NAME, column, whereClause, whereArgument, null, null, null)

        if(cursor != null && cursor.moveToFirst()) {

            currentQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
        }
        return currentQuantity
    }

    fun deleteProduct(product: String) {

        var db = writableDatabase
        var whereClause = "$COLUMN_NAME = ?"
        var whereArgument = arrayOf(product)
        db.delete(TABLE_NAME, whereClause, whereArgument)

    }

    fun isItemInCart(product: Product): Boolean {

        var query = "select * from $TABLE_NAME where $COLUMN_ID = ?"
        var whereArgument = arrayOf(product._id)
        var cursor = db.rawQuery(query, whereArgument)
        var count = cursor.count
        return count != 0
    }

    fun isCartEmpty(): Boolean {
        var query = "select * from $TABLE_NAME"
        var cursor = db.rawQuery(query, null)
        return !cursor.moveToFirst()

    }

    fun readItems(): ArrayList<Product> {
        var list: ArrayList<Product> = ArrayList()
        var db = readableDatabase
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_IMAGE,
            COLUMN_QUANTITY,
            COLUMN_MRP,
            COLUMN_PRICE
        )

        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                var price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                var mrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP))
                var cart = Product(
                    _id = id,
                    productName = name,
                    image = image,
                    quantity = quantity,
                    price = price,
                    mrp = mrp
                )
                list.add(cart)
            } while (cursor.moveToNext())
        }
        return list
    }

    fun SubTotal(): Double {
        mList  = readItems()
        var subtotal = 0.0
        for (i in mList) {
            subtotal += i.mrp?.times(getCurrentQuantity(i))!!
        }
        return subtotal
    }


    fun Discount(): Double {
        mList = readItems()
        var discount = 0.0
        for (i in mList) {
            discount += i.mrp?.minus(i.price!!)!!
        }
        return discount
    }

    fun Total(): Double {
        mList = readItems()
        var total = 0.0
        for (i in mList) {
            total = SubTotal() - Discount()
        }
        return total
    }


}