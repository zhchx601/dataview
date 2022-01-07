package storm;


import kafka.api.OffsetRequest;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
/**
创建一个Kfafa源数据Spout
 */
public class KafkaSourceSpout {
    public KafkaSpout createKafkSpout() {
        String brokerZkStr = "192.168.93.141:2181,192.168.93.142:2181,192.168.93.143:2181";//kfaka集群列表
        BrokerHosts hosts = new ZkHosts(brokerZkStr);   // 通过zookeeper中的/brokers即可找到kafka的地址
        String topic = "example";
        String zkRoot = "/" + topic;
        String id = "consumer-id";//定义消费者i名称
        SpoutConfig spoutConf = new SpoutConfig(hosts, topic, zkRoot, id);
        spoutConf.startOffsetTime = OffsetRequest.LatestTime(); // 设置之后，刚启动时就不会把之前的消费也进行读取，会从最新的偏移量开始读取
        return new KafkaSpout(spoutConf);
    }
}
