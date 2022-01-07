package order;

// -*- codeing: utf-8 -*-

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class ordertopology {
    public static void main(String[] args) {
        //定义拓扑结构
        TopologyBuilder builder=new TopologyBuilder();
        builder.setSpout("spout", new kfakasourcespout().createKafkSpout());
        builder.setBolt("bolt1", new bolt1()).shuffleGrouping("spout");
        builder.setBolt("bolt2", new bolt2()).shuffleGrouping("bolt1");

        // 使用builder构建topology
        StormTopology topology = builder.createTopology();
        String topologyName = kfakastormtopology.class.getSimpleName();  // 拓扑的名称
        Config config = new Config();   // Config()对象继承自HashMap，但本身封装了一些基本的配置

        //提交
        LocalCluster localCluster=new LocalCluster();
        localCluster.submitTopology(topologyName, config,topology);
    }
}
