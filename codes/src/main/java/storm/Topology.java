package storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class Topology { public static void main(String[] args) {
    //定义拓扑结构
    TopologyBuilder builder=new TopologyBuilder();
    builder.setSpout("Spout", new KafkaSourceSpout().createKafkSpout());
    builder.setBolt("Bolt1", new Bolt1()).shuffleGrouping("Spout");
    builder.setBolt("Bolt2", new Bolt2()).shuffleGrouping("Bolt1");

    // 使用builder构建topology
    StormTopology topology = builder.createTopology();
    String topologyName = KafkaStormTopology.class.getSimpleName();  // 拓扑的名称
    Config config = new Config();   // Config()对象继承自HashMap，但本身封装了一些基本的配置

    //提交
    LocalCluster localCluster=new LocalCluster();
    localCluster.submitTopology(topologyName, config,topology);
}
}



