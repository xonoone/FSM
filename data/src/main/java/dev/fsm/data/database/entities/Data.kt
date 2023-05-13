package dev.fsm.data.database.entities

import androidx.room.*

@Entity(tableName = "temp_test")
data class Data(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo(name = "inner_field")  val innerField: InnerData,
    @ColumnInfo(name = "inner_array")  val innerArray: ArrayList<InnerData>
)

data class InnerData(
    @ColumnInfo(name = "data") val data: String
)

@Dao
interface TempDao {
    @Query("SELECT * FROM temp_test")
    fun get(): Data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(param: Data)
}
