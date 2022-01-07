package order;

// -*- codeing: utf-8 -*-

import kafka.api.OffsetRequest;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

/**
 * Kafka和storm的整合，用于统计实时流量对应的pv和uv
 */
class kfakastormtopology {
    //    static class MyKafkaBolt extends BaseRichBolt {
    static class MyKafkaBolt extends BaseBasicBolt {
        /**
         * kafkaSpout发送的字段名为bytes
         */
        @Override
        public void execute(Tuple input, BasicOutputCollector collector) {
            byte[] binary = input.getBinary(0); // 跨jvm传输数据，接收到的是字节数据
//            byte[] bytes = input.getBinaryByField("bytes");   // 这种方式也行
            String line = new String(binary);
            System.out.println(line);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {

        }
    }

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        /**
         * 设置spout和bolt的dag（有向无环图）
         */
        KafkaSpout kafkaSpout = createKafkaSpout();
        builder.setSpout("id_kafka_spout", kafkaSpout);
        builder.setBolt("id_kafka_bolt", new kfakastormtopology.MyKafkaBolt())
                .shuffleGrouping("id_kafka_spout"); // 通过不同的数据流转方式，来指定数据的上游组件
        // 使用builder构建topology
        StormTopology topology = builder.createTopology();
        String topologyName = storm.KafkaStormTopology.class.getSimpleName();  // 拓扑的名称
        Config config = new Config();   // Config()对象继承自HashMap，但本身封装了一些基本的配置

        // 启动topology，本地启动使用LocalCluster，集群启动使用StormSubmitter
        if (args == null || args.length < 1) {  // 没有参数时使用本地模式，有参数时使用集群模式
            LocalCluster localCluster = new LocalCluster(); // 本地开发模式，创建的对象为LocalCluster
            localCluster.submitTopology(topologyName, config, topology);
        } else {
            StormSubmitter.submitTopology(topologyName, config, topology);
        }
    }

    private static KafkaSpout createKafkaSpout() {
        String brokerZkStr = "192.168.93.141:2181,192.168.93.142:2181,192.168.93.143:2181";
        BrokerHosts hosts = new ZkHosts(brokerZkStr);   // 通过zookeeper中的/brokers即可找到kafka的地址
        String topic = "Order";//要消费的topic主题
        String zkRoot = "/" + topic;//kafka在zk中的目录（会在该节点目录下记录读取kafka消息的偏移量）
        String id = "consumer-id";//当前操作的标识id
        SpoutConfig spoutConf = new SpoutConfig(hosts, topic, zkRoot, id);
        spoutConf.startOffsetTime = OffsetRequest.LatestTime(); // 设置之后，刚启动时就不会把之前的消费也进行读取，会从最新的偏移量开始读取
        return new KafkaSpout(spoutConf);
    }
}