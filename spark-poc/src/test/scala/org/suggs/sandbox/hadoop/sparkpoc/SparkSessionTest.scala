package org.suggs.sandbox.hadoop.sparkpoc

import org.apache.spark.sql.SaveMode.Overwrite
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.FlatSpec

class SparkSessionTest extends FlatSpec {

  val baseDir = "spark-poc/src/test/resources/"

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

  "with a sparkPoc we" can "read some json and write to an avro file" in {
    val sparkSession = createSparkSession()
    val dataFrame = readJsonIntoDataFrame(sparkSession, baseDir + "trade.json")

    dataFrame.show(false)
    dataFrame.printSchema()

    writeDateFrameTo(dataFrame, baseDir + "trades.avro.snappy")
  }

  it can "read in data from an avro file and query the data" in {
    val sparkSession = createSparkSession()
    val dataFrame = readAvroIntoDataFrame(sparkSession, baseDir + "trades.avro.snappy")
    dataFrame.show()
  }


}
