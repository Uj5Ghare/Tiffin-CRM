package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CustomerEntity::class,
        MealPlanEntity::class,
        SubscriptionEntity::class,
        DeliveryRecordEntity::class,
        PaymentRecordEntity::class,
        PauseScheduleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TiffinDatabase : RoomDatabase() {

    abstract fun tiffinDao(): TiffinDao

    companion object {
        @Volatile
        private var INSTANCE: TiffinDatabase? = null

        fun getDatabase(context: Context): TiffinDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TiffinDatabase::class.java,
                    "tiffin_crm_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
