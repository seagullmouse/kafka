<h1>Kafka Application: aggregating trade statistics</h1>
<h2>Overview</h2>

The Kafka application consists of 3 nodes.

1. CSV Reader Application (producer)
2. Kafka Broker
3. Aggregator Application (consumer)

The nodes communicate as below,
 
`CSV Reader Application <-> Kafka Broker <-> Aggregator Application`

<h2>Source/installation</h2>
The source for all 3 components can be found in this multi-module Maven repository.

```
git clone https://github.com/seagullmouse/kafka.git
cd kafka
mvn clean install
```

<h2>Usage</h2>

1. Start the Kafka Broker (requires docker-compose)
<br>`docker-compose -f docker-compose.yml up`

2. Start the Aggregator Application
<br>`java -jar aggregator/target/aggregator-0.0.1-SNAPSHOT.jar`

3. Start the CSV Reader Application (pass the CSV filename as the only argument)
<br>`java -jar reader/target/reader-0.0.1-SNAPSHOT.jar ~/Downloads/commodity_trade_statistics_data.csv`

<h2>Logs</h2>

* [CSV Reader Application Log](/.console-outputs/csv-reader.log)
* [Aggregator Application Log](/.console-outputs/aggregator.log)
