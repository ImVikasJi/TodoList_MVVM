package com.example.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.data.Task
import com.example.todolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Task::class], version = 1
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // db operations
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Hello Vikas",completed = true))
                dao.insert(Task("How are you",completed = true))
                dao.insert(Task("Learning?"))
                dao.insert(Task("Be consistence", important = true))
                dao.insert(Task("That is enough"))
                dao.insert(Task("Time is everything"))
            }
        }
    }

//    companion object {
//        @Volatile
//        private var instance: TaskDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also { instance = it }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                TaskDatabase::class.java,
//                "task_table_db.db"
//            ).build()
//    }
}