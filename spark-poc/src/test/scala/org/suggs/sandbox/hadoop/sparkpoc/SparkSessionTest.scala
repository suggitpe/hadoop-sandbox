package org.suggs.sandbox.hadoop.sparkpoc

import org.suggs.sandbox.hadoop.sparkpoc.SparkSessionTest.{AVRO_LOCATION, BASE}
import org.apache.spark.sql.SaveMode.Overwrite
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.FlatSpec

object SparkSessionTest{
  val BASE = "spark-poc/src/test/resources/"
  val AVRO_LOCATION = "trades.avro.snappy"
}

class SparkSessionTest extends FlatSpec {

  "with a sparkPoc we" can "read some json and write to an avro file" in {
    val sparkSession = createSparkSession()
    val dataFrame = readJsonIntoDataFrame(sparkSession, BASE + "trade.json")

    dataFrame.show(false)
    dataFrame.printSchema()

    writeDateFrameTo(dataFrame, AVRO_LOCATION)
  }

  it can "read in data from an avro file and query the data" in {
    val sparkSession = createSparkSession()
    val dataFrame = readAvroIntoDataFrame(sparkSession, AVRO_LOCATION)
    dataFrame.show()
  }

  it can "read in data from an avro file and write out a json" in {
    val sparkSession = createSparkSession()
    val dataFrame = readAvroIntoDataFrame(sparkSession, AVRO_LOCATION)
    dataFrame.toJSON.show()
  }

  def createSparkSession(): SparkSession = {
    SparkSession
      .builder()
      .appName("Test")
      .master("local")
      .getOrCreate()
  }

  def readJsonIntoDataFrame(sparkSession: SparkSession, location: String): DataFrame = {
    sparkSession
      .read
      .option("mode", "DROPMALFORMED")
      .json(location)
  }

  def writeDateFrameTo(df: DataFrame, location: String) = {
    df
      .write
      .mode(Overwrite)
      .format("com.databricks.spark.avro")
      .option("compression", "snappy")
      .save(location)
  }

  def readAvroIntoDataFrame(sparkSession: SparkSession, location: String): DataFrame = {
    sparkSession.read.format("com.databricks.spark.avro").load(location)
  }


}
